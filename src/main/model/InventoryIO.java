package model;

import java.util.ArrayList;

public class InventoryIO {
    private boolean isProduct; // a check to verify if a given object is a product or not.
    private ArrayList<InventoryIO> subInventory = new ArrayList<>();//holds the sub-inventory inside an inventory object
    private String name; // name of an object
    private String description; // stores a short description
    //need to implement more options for products

    public InventoryIO(String nameOfItems,boolean isProduct) {
        if (isProduct) {
            this.name = "*" + nameOfItems;
        } else {
            this.name = nameOfItems;
        }
        this.isProduct = isProduct;
        this.description = "";
    }

    public InventoryIO(String nameOfItems,String description,boolean isProduct) {
        if (isProduct) {
            this.name = "*" + nameOfItems;
        } else {
            this.name = nameOfItems;
        }
        this.isProduct = isProduct;
        this.description = description;
    }

    // Effects: returns the name of a given InventoryIO object
    public String getName() {
        return name;
    }

    // Effects: returns the subInventory of a given InventoryIO object
    public ArrayList<InventoryIO> getSubInventory() {
        return subInventory;
    }

    // Effects: returns the description of a given InventoryIO object
    public String getDescription() {
        return description;
    }

    // Effects: returns the nature of a given InventoryIO object (if a product or not)
    public boolean getProduct() {
        return isProduct;
    }
}
