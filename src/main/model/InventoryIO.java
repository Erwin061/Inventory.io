package model;

import java.util.ArrayList;
import java.util.Iterator;

public class InventoryIO {
    String description; // stores a short description
    String color = "N/A"; //stores color, if applicable.
    private String name; // name of an object
    private boolean isProduct; // a check to verify if a given object is a product or not.
    private ArrayList<InventoryIO> subInventory = new ArrayList<>();//holds the sub-inventory inside an inventory object
    //need to implement more options for products

    // Modifies: this.
    // Effects: Constructor for Inventory Object that takes 2 arguments for product and name.
    // Requires: nameOfItems != null.
    public InventoryIO(String nameOfItems, boolean isProduct) {
        if (isProduct) {
            this.name = "*" + nameOfItems;
        } else {
            this.name = nameOfItems;
        }
        this.isProduct = isProduct;
        this.description = "";
    }

    // Modifies: this.
    // Effects: Constructor for Inventory Object that takes 3 arguments for product, name, and description.
    // Requires: nameOfItems != null && description != null.
    public InventoryIO(String nameOfItems, String description, boolean isProduct) {
        if (isProduct) {
            this.name = "*" + nameOfItems;
        } else {
            this.name = nameOfItems;
        }
        this.isProduct = isProduct;
        this.description = description;
    }

    // Modifies: this.
    // Effects: Constructor for Inventory Object that takes 4 arguments for product, name, color, and description.
    // Requires: nameOfItems != null && description != null && color != null.
    public InventoryIO(String nameOfItems, String description, boolean isProduct, String color) {
        if (isProduct) {
            this.name = "*" + nameOfItems;
        } else {
            this.name = nameOfItems;
        }
        this.isProduct = isProduct;
        this.description = description;
        this.color = color;
        Event clk = new Event("null");
        EventLog.getInstance().logEvent(new Event(""));
        EventLog.getInstance().logEvent(new Event(clk.getDate() + "\nNew '" + name + "' (X) added to Inventory (Y)"));
        EventLog.getInstance().logEvent(new Event("Description set: " + description));
        EventLog.getInstance().logEvent(new Event("Product type set: " + isProduct));
        EventLog.getInstance().logEvent(new Event("Color set: " + color));

    }

    // Modifies: this.
    // Effects: Generic constructor for Inventory Object that takes 0 arguments.
    public InventoryIO() {
        this.name = "InventoryIO";
        this.description = "Root of Inventory";
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
        Event mainEvent = new Event("New name for '" + this.name + "' set to: "  + name);
        EventLog.getInstance().logEvent(new Event("\n" + mainEvent.getDate() + "\n" + mainEvent.getDescription()));
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
    public void setDesc(String desc) {
        Event mainEvent = new Event("New description for '" + this.name + "' set to: "  + desc);
        EventLog.getInstance().logEvent(new Event("\n" + mainEvent.getDate() + "\n" + mainEvent.getDescription()));
        this.description = desc;
    }

    // Modifies: this.
    // Effects: sets the product bool for the current InventoryIO product; mainly to be used by Reader during file load.
    public void setProduct(boolean isProduct) {
        Event mainEvent = new Event("New product type for '" + this.name + "' set to: "  + isProduct);
        EventLog.getInstance().logEvent(new Event("\n" + mainEvent.getDate() + "\n" + mainEvent.getDescription()));
        this.isProduct = isProduct;
    }

    // Effects: returns the color of a given InventoryIO object (if applicable, else returns "N/A")
    public String getColor() {
        return this.color;
    }

    // Modifies: this.
    // Effects: sets the color of the object.
    public void setColor(String color) {
        Event mainEvent = new Event("New color for '" + this.name + "' set to: "  + color);
        EventLog.getInstance().logEvent(new Event("\n" + mainEvent.getDate() + "\n" + mainEvent.getDescription()));
        this.color = color;
    }

    // Modifies: this
    // Effects: Overrides the toString method so the JTree node can access the name of my Inventory Object. Since all
    // my Inventory Objects are apparent and actual types of "InventoryIO," we don't have to override toString for
    // any other class. (As we have no parent class to this.)
    public String toString() {
        return this.name;
    }

    // Effects: Takes a call from the UI package and starts to print all Events stored within the Event Collection.
    // Modifies: this
    public static void printEvent() {
        Iterator<Event> test = EventLog.getInstance().iterator();
        while (test.hasNext()) {
            System.out.println(test.next().getDescription());
        }
    }
}
