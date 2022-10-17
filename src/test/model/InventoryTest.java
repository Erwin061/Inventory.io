package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {
    ArrayList<InventoryIO> testing;

    @BeforeEach
    void runFirst() {
        testing = new ArrayList<>();
        testing.add(new InventoryIO("CPU","contains all CPUs", false));
        testing.get(0).getSubInventory().add(new InventoryIO("INTEL", "CPU", false));
        testing.get(0).getSubInventory().add(new InventoryIO("AMD", "CPU", false));
        testing.get(0).getSubInventory().get(0).getSubInventory().add(new InventoryIO("i7", false));
    }

    @Test
    void testMainInventory() {
        testing.add(new InventoryIO("Headphones","contains all headphones", false));
        assertEquals(2,testing.size());
    }

    @Test
    void testNaming() {
        testing.add(new InventoryIO("Phones","contains all phones", false));
        assertEquals("CPU",testing.get(0).getName());
        assertEquals("Phones",testing.get(1).getName());
        assertEquals("INTEL",testing.get(0).getSubInventory().get(0).getName());
        assertEquals("AMD",testing.get(0).getSubInventory().get(1).getName());
    }

    @Test
    void testDeleting() {
        testing.add(new InventoryIO("Phones","contains all phones", false));
        testing.remove(testing.size()-1);
        testing.remove(testing.get(0).getSubInventory().remove(testing.get(0).getSubInventory().size()-1));
        assertEquals(1,testing.size()); //removes a main inventory object
        assertEquals(1,testing.get(0).getSubInventory().size()); //removes 1 of the 2 sub inventories
    }

    @Test
    void testDescription() {
        testing.add(new InventoryIO("Phones","contains all phones", false));
        assertEquals("contains all CPUs",testing.get(0).getDescription());
        assertEquals("contains all phones",testing.get(1).getDescription());
    }

    @Test
    void testMultipleAdditions() {
        // adds to main inventory
        testing.add(new InventoryIO("Laptops","contains all laptops", false));
        // adds to sub-inventory of "Laptops"
        testing.get(1).getSubInventory().add(new InventoryIO("Asus",false));
        // checks if index is now equal to 2 as we added one.
        assertEquals(2,testing.size());
        // checks if the inventory has the sub-inventory we added to it.
        assertEquals(1,testing.get(1).getSubInventory().size());
    }

    @Test
    void testProduct() {
        // holds the inventory object i7.
        InventoryIO productCPU = testing.get(0).getSubInventory().get(0).getSubInventory().get(0);
        // adds to sub-inventory of 9700K which is a product
        productCPU.getSubInventory().add(new InventoryIO("9700K",true));
        // checks if object is a product or not.
        assertTrue(productCPU.getSubInventory().get(0).getProduct());
        // adds to sub-inventory of 9700K which is a product
        productCPU.getSubInventory().add(new InventoryIO("7700K","Kaby Lake CPU",true));
        // checks if object is a product or not.
        assertTrue(productCPU.getSubInventory().get(0).getProduct());
    }

}