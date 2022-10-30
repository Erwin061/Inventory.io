package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonRead;
import persistence.JsonWrite;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {
    ArrayList<InventoryIO> testing;
    ArrayList<InventoryIO> defaultTesting;

    @BeforeEach
    void runFirst() {
        //User Specified input
        testing = new ArrayList<>();
        testing.add(new InventoryIO("CPU", "contains all CPUs", false));
        testing.get(0).getSubInventory().add(new InventoryIO("INTEL", "CPU", false));
        testing.get(0).getSubInventory().add(new InventoryIO("AMD", "CPU", false));
        testing.get(0).getSubInventory().get(0).getSubInventory().add(new InventoryIO("i7", false));
        //Default Object
        defaultTesting = new ArrayList<>();
        defaultTesting.add(new InventoryIO());
    }

    @Test
    void testMainInventory() {
        testing.add(new InventoryIO("Headphones", "contains all headphones", false));
        assertEquals(2, testing.size());
    }

    @Test
    void testNaming() {
        testing.add(new InventoryIO("Phones", "contains all phones", false));
        assertEquals("CPU", testing.get(0).getName());
        assertEquals("Phones", testing.get(1).getName());
        assertEquals("INTEL", testing.get(0).getSubInventory().get(0).getName());
        assertEquals("AMD", testing.get(0).getSubInventory().get(1).getName());
    }

    @Test
    void testDeleting() {
        testing.add(new InventoryIO("Phones", "contains all phones", false));
        testing.remove(testing.size() - 1);
        testing.remove(testing.get(0).getSubInventory().remove(testing.get(0).getSubInventory().size() - 1));
        assertEquals(1, testing.size()); //removes a main inventory object
        assertEquals(1, testing.get(0).getSubInventory().size()); //removes 1 of the 2 sub inventories
    }

    @Test
    void testDescription() {
        testing.add(new InventoryIO("Phones", "contains all phones", false));
        assertEquals("contains all CPUs", testing.get(0).getDescription());
        assertEquals("contains all phones", testing.get(1).getDescription());
    }

    @Test
    void testMultipleAdditions() {
        // adds to main inventory
        testing.add(new InventoryIO("Laptops", "contains all laptops", false));
        // adds to sub-inventory of "Laptops"
        testing.get(1).getSubInventory().add(new InventoryIO("Asus", false));
        // checks if index is now equal to 2 as we added one.
        assertEquals(2, testing.size());
        // checks if the inventory has the sub-inventory we added to it.
        assertEquals(1, testing.get(1).getSubInventory().size());
    }

    @Test
    void testProduct() {
        // holds the inventory object i7.
        InventoryIO productCPU = testing.get(0).getSubInventory().get(0).getSubInventory().get(0);
        // adds to sub-inventory of 9700K which is a product
        productCPU.getSubInventory().add(new InventoryIO("9700K", true));
        // checks if object is a product or not.
        assertTrue(productCPU.getSubInventory().get(0).getProduct());
        // adds to sub-inventory of 9700K which is a product
        productCPU.getSubInventory().add(new InventoryIO("7700K", "Kaby Lake CPU", true));
        // checks if object is a product or not.
        assertTrue(productCPU.getSubInventory().get(0).getProduct());
    }


    @Test
    void testSettingName() {
        testing.get(0).setName("is it a CPU!?"); //changes the name of CPU inventory.
        assertEquals("is it a CPU!?", testing.get(0).getName());

    }

    @Test
    void testSettingProduct() {
        testing.get(0).setProduct(true); //changes the nature of CPU inventory to a product.
        assertTrue(testing.get(0).getProduct());
        testing.get(0).setProduct(!true);
        assertFalse(testing.get(0).getProduct());

    }

    @Test
    void testSettingDesc() {
        assertEquals("contains all CPUs", testing.get(0).getDescription()); //checks if current holds true
        testing.get(0).setDesc("it is a CPU!?"); //changes the desc of CPU inventory.
        assertEquals("it is a CPU!?", testing.get(0).getDescription()); //checks changed desc
    }

    @Test
    void testSettingSubInv() {
        InventoryIO testing = new InventoryIO(); //creates a default Inventory Object.
        ArrayList<InventoryIO> subOfTesting = new ArrayList<>();
        subOfTesting.add(new InventoryIO("test me", "testing", false));
        assertFalse(testing.getSubInventory().equals(subOfTesting));
        testing.setSubInventory(subOfTesting);
        assertTrue(testing.getSubInventory().equals(subOfTesting));
    }

    @Test
    void testWriting() {
        ArrayList<InventoryIO> testingFromMain = new ArrayList<>();
        InventoryIO testFromDefault = new InventoryIO(); //creates a default Inventory Object.
        ArrayList<InventoryIO> subOfTesting = new ArrayList<>();
        subOfTesting.add(testFromDefault);

        try {
            JsonWrite write = new JsonWrite();
            write.jsonSave(testing);
        } catch (FileNotFoundException e) {
            System.out.println("File was not found! Returning to main menu.");
        }

        try {
            JsonRead read = new JsonRead();
            testingFromMain = read.getInventoryFromJson();
        } catch (FileNotFoundException e) {
            System.out.println("File was not found! Returning to main menu.");
        }
        assertEquals(testing.get(0).getName(), testingFromMain.get(0).getName());

        try {
            JsonWrite write = new JsonWrite();
            write.jsonSave(subOfTesting);
        } catch (FileNotFoundException e) {
            System.out.println("File was not found! Returning to main menu.");
        }
        try {
            JsonRead read = new JsonRead();
            subOfTesting = read.getInventoryFromJson();
        } catch (FileNotFoundException e) {
            System.out.println("File was not found! Returning to main menu.");
        }
        assertEquals(defaultTesting.get(0).getName(), subOfTesting.get(0).getName());
    }


    @Test
    void testFailedWrite() {
        boolean testFailure1 = false;
        boolean testFailure2 = false;
        try {
            JsonWrite write = new JsonWrite("./data/\nW:her:eFileGo!txt"); // gives invalid file dir; illegal.
            write.jsonSave(testing);
        } catch (FileNotFoundException e) {
            testFailure1 =true;
        }
        assertTrue(testFailure1);

        try {
            JsonWrite write = new JsonWrite("./data/CanWrite.txt"); // gives invalid file dir; illegal.
            write.jsonSave(testing);
        } catch (FileNotFoundException e) {
            testFailure1 =true;
        }
        assertFalse(testFailure2);
    }
}