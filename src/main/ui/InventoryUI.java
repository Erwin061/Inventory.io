package ui;

import model.InventoryIO;
import java.util.ArrayList;
import java.util.Scanner;

// InventoryUI builds an intractable UI visible via the console; it helps the user to see what they are doing.
// In addition to forming the UI, class InventoryUI also holds multiple objects from the InventoryIO class in ArrayLists
// In-sense, the user is able to an X to a Y; many InventoryIO objects inside class InventoryUI.
// At this moment, this class makes use of 7 methods to fulfill its goal.
public class InventoryUI {
    Scanner input = new Scanner(System.in);
    ArrayList<InventoryIO> initialInventory = new ArrayList<>();
    ArrayList<ArrayList<InventoryIO>> keepTrack = new ArrayList<>();

    public InventoryUI() {
        startUI();
    }

    //Effects: Asks the user to provide an input to start the UI or quit.
    private void startUI() {
        int fail;
        System.out.println("Hello! Please select one of the following options:");
        System.out.println("\n1 - Display interactive GUI map!");
        System.out.println("\n2 - Quit");

        do {
            String answer = input.next();
            fail = 0;
            if (answer.equals("1")) {
                checkInventorySize();
            } else if (answer.equals("2")) {
                System.exit(1);
            } else {
                fail = 1;
                System.out.println("Invalid choice! Please try again.");
            }
        } while (fail == 1);

        startUI();

    }

    //Modifies: this
    //Effects: Creates an Inventory object that is stored within the ArrayList initialInventory and/or keepTrack.
    //Requires: ArrayList initialInventory and/or keepTrack must be != null AND size must be > 0.
    private InventoryIO createInventory() {
        String isProduct;
        String name;
        String description;

        do {
            System.out.println("Are you adding a product or an inventory? (0 = inventory, 1 = product)");
            isProduct = input.next();
        } while (!isProduct.equals("1") && !isProduct.equals("0"));

        if (isProduct.equals("1")) {
            System.out.println("Okay, what is the name of the product?");
        } else {
            System.out.println("Okay, what is the name of the inventory?");
        }
        name = input.next();
        System.out.println("Enter a short description.");
        description = input.next();
        System.out.println("All done!");
        return new InventoryIO(name,description,isProduct.equals("1"));
    }

    //Requires: initialInventory MUST != null.
    //Modifies: this.
    //Effects: Creates a new inventory if ones does not exist during start-up. Adds the new inventory to the ArrayList
    //         initialInventory. ArrayList keepTrack adds the reference of initialInventory after all is done.
    private void checkInventorySize() {
        if (initialInventory.size() < 1) {
            String answer;
            do {
                System.out.println("Uh-Oh, no inventories exist! Would you like to create one?");
                answer = input.next().toLowerCase();
            } while (!answer.equals("y") && !answer.equals("n"));
            if (answer.equals("y")) {
                initialInventory.add(createInventory());
                keepTrack.add(initialInventory);
            } else {
                System.out.println("Okay, returning to main menu!");
                startUI();
            }
        } else {
            keepTrack.add(initialInventory);
            showInventory();
        }
    }

    // Requires: keepTrack != null AND size of keepTrack > 0
    // Effects: Uses indexPointer from showInventory() to add an arrow at the correct index selected by the user.
    //          Makes use of the compare variable to check if pointer index matches shown.
    private void makeUI(int indexOfParent) {
        ArrayList<InventoryIO> temp =  keepTrack.get(keepTrack.size() - 1);
        for (int compare = 0; compare <= keepTrack.get(keepTrack.size() - 1).size() - 1; ++compare) {
            if ((indexOfParent) == compare) {
                System.out.println("\n--> " + temp.get(compare).getName() + " | " + temp.get(compare).getDescription());
            } else {
                System.out.println("\n    " + temp.get(compare).getName() + " | " + temp.get(compare).getDescription());
            }
        }
    }

    // Requires: initialInventory != null AND size of initialInventory > 0
    // Modifies: this
    // Effects: Showcases an interactive inventory map without an actual GUI present.
    //          Passes the indexPointer to show an arrow within the GUI and also provides the index of a shown object.
    private void showInventory() {
        String move;
        int indexPointer = 0;

        do {
            System.out.println("\n[w] up, [s] down, [d] sub-inventory, [a] back to main, [+] add, [-] delete");
            makeUI(indexPointer);
            move = input.next();
            if (move.equals("w") && indexPointer > 0) {
                --indexPointer;
            } else if (move.equals("s") && indexPointer < keepTrack.get(keepTrack.size() - 1).size() - 1) {
                ++indexPointer;
            } else if (move.equals("d")) {
                checkSubInventory(indexPointer, keepTrack);
                indexPointer = 0;
            } else if (move.equals("a") && keepTrack.size() > 1) {
                keepTrack.remove(keepTrack.size() - 1);
                indexPointer = 0;
            } else if (move.equals("+")) {
                keepTrack.get(keepTrack.size() - 1).add(createInventory());
            } else if (move.equals("-")) {
                deleteInventory(indexPointer);
                checkInventorySize();
            }
        } while (!move.equals("q"));
    }

    //Requires: InventorIO.name != null
    //Effects: Getter method for the name of a given object.
    public String getName(int index) {
        return keepTrack.get(keepTrack.size() - 1).get(index).getName();
    }

    //Requires: keepTrack.size() MUST be > 1.
    //Modifies: this
    //Effects: Deletes the object from the given index during invocation.
    public void deleteInventory(int index) {
        System.out.println("Delete '" + getName(index) + "'?");
        if (input.next().equals("y")) {
            System.out.println("Successfully deleted '" + getName(index) + "'!");
            keepTrack.get(keepTrack.size() - 1).remove(index);
        } else {
            System.out.println("NOT deleting '" + getName(index) + "'!");
        }
    }

    //Requires: inventory.size() MUST be > 0 AND != null.
    //Modifies: Changes the reference values of the ArrayList passed during invocation; mainly this.
    //Effects: Creates a new sub-inventory if requested by the user. Adds the new inventory to the ArrayList called
    //         inventory, which itself is a passed reference memory address of another ArrayList.
    private void checkSubInventory(int index, ArrayList<ArrayList<InventoryIO>> inventory) {
        String answer;
        if (inventory.get(inventory.size() - 1).get(index).getSubInventory().size() > 0) {
            inventory.add(inventory.get(inventory.size() - 1).get(index).getSubInventory());
        } else {
            do {
                System.out.println("No sub-inventories found! Want to create one?");
                answer = input.next().toLowerCase();
            } while (!answer.equals("y") && !answer.equals("n"));
            if (answer.equals("y")) {
                inventory.get(inventory.size() - 1).get(index).getSubInventory().add(createInventory());
            } else {
                System.out.println("Okay, returning to inventory selection!");
            }
        }
    }
}
