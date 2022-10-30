package persistence;

import model.InventoryIO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

/*
This class writes a given object in JSON form
 */

public class JsonWrite {
    JSONWriter write;
    PrintWriter test;

    //start
    public JsonWrite() throws FileNotFoundException {
        test = new PrintWriter(new File("./data/saveFile.txt"));
        write = new JSONWriter(test);
    }

    //start
    public JsonWrite(String destin) throws FileNotFoundException {
        test = new PrintWriter(new File(destin));
        write = new JSONWriter(test);
    }

    //Modifies: this
    //Effects: writes a save file
    public void jsonSave(ArrayList<InventoryIO> initialInventory) throws FileNotFoundException {

        JSONObject tracker = new JSONObject().putOnce("Inventory",initialInventory);
        tracker.write(test,3,1);
        test.close();

    }
}
