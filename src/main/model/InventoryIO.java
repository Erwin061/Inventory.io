package model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class InventoryIO {
    String description; // stores a short description
    String color = "N/A"; //stores color, if applicable.
    private String name; // name of an object
    private boolean isProduct; // a check to verify if a given object is a product or not.
    private ArrayList<InventoryIO> subInventory = new ArrayList<>();//holds the sub-inventory inside an inventory object
    //need to implement more options for products

    public InventoryIO(String nameOfItems, boolean isProduct) {
        if (isProduct) {
            this.name = "*" + nameOfItems;
        } else {
            this.name = nameOfItems;
        }
        this.isProduct = isProduct;
        this.description = "";
    }

    public InventoryIO(String nameOfItems, String description, boolean isProduct) {
        if (isProduct) {
            this.name = "*" + nameOfItems;
        } else {
            this.name = nameOfItems;
        }
        this.isProduct = isProduct;
        this.description = description;
    }

    public InventoryIO() {
        this.name = "change me pls";
        this.description = "change me pls";
        this.isProduct = false;
    }

    // Effects: returns the name of a given InventoryIO object
    public String getName() {
        return name;
    }

    // Effects: returns the subInventory of a given InventoryIO object
    public ArrayList<InventoryIO> getSubInventory() {
        return subInventory;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Modifies: this.
    // Effects: sets the subInventory for the current InventoryIO product; mainly to be used by Reader during file load
    public void setSubInventory(ArrayList<InventoryIO> subInventory) {
        this.subInventory = subInventory;
    }

    // Effects: returns the description of a given InventoryIO object
    public String getDescription() {
        return description;
    }

    // Effects: returns the nature of a given InventoryIO object (if a product or not)
    public boolean getProduct() {
        return isProduct;
    }

    // Modifies: this.
    // Requires: description input not to be null, else name become null rather than nothing.
    // Effects: sets the description for the current InventoryIO product; mainly to be used by Reader during file load.
    public void setDesc(String description) {
        this.description = description;
    }

    // Modifies: this.
    // Effects: sets the product bool for the current InventoryIO product; mainly to be used by Reader during file load.
    public void setProduct(boolean isProduct) {
        this.isProduct = isProduct;
    }

    // Effects: returns the color of a given InventoryIO object (if applicable, else returns "N/A")
    public String getColor() {
        return this.color;
    }

    // Modifies: this.
    // Effects: sets the color of the object.
    public void setColor(String color) {
        this.color = color;
    }

    public String toString() {
        return this.name;
    }
}