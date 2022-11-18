package persistence;

import model.InventoryIO;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public abstract class ReadWrite extends JFrame {

    //Effects: Reads a saved file from the default location provided.
    //Modifies: this.
    public ArrayList<InventoryIO> readFromJson() throws FileNotFoundException {
        ArrayList<InventoryIO> initialInventory = new ArrayList<>();
        JsonRead read = new JsonRead();
        initialInventory = read.getInventoryFromJson();
        return initialInventory;
    }

    //Effects: saves the state of the application in a default file.
    //Modifies: this.
    public void writeToJson(ArrayList<InventoryIO> initialInventory) throws FileNotFoundException {
        JsonWrite write = new JsonWrite();
        write.jsonSave(initialInventory);

    }

}

