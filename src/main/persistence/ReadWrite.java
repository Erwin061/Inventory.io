package persistence;

import model.InventoryIO;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public interface ReadWrite {

    //Effects: Reads a saved file from the default location provided.
    //Modifies: this.
    public ArrayList<InventoryIO> readFromJson() throws FileNotFoundException;

    //Effects: saves the state of the application in a default file.
    //Modifies: this.
    public void writeToJson(ArrayList<InventoryIO> initialInventory) throws FileNotFoundException;


}

