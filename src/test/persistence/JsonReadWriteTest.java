package persistence;

import model.InventoryIO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReadWriteTest {

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
            testFailure1 = true;
        }
        assertTrue(testFailure1);

        try {
            JsonWrite write = new JsonWrite("./data/CanWrite.txt"); // gives invalid file dir; illegal.
            write.jsonSave(testing);
        } catch (FileNotFoundException e) {
            testFailure2 = true;
        }
        assertFalse(testFailure2);
    }
}

