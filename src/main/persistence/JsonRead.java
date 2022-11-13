package persistence;

import model.InventoryIO;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/*
This class reads the JSON saved during the operation of the program. JsonReader uses the saved Json file to
recreate the state of the application saved by the user.
 */
public class JsonRead {
    PrintWriter write;
    Scanner readJson;
    ArrayList<InventoryIO> returnInventory = new ArrayList<>();


    public JsonRead() throws FileNotFoundException {
        write = new PrintWriter(new File("./data/saveFile.json"));
        readJson = new Scanner(new File("./data/saveFile.txt"));
        readFromJson(readJson);
    }

    // Modifies: this.
    // Requires: A valid destination, else throws FileNotFoundException!
    // Effects: Creates a valid JSONObject that gets passed to convertFromJson method.
    private void readFromJson(Scanner destin) throws FileNotFoundException {
        String tempStorage = "";
        JSONObject invocation;
        while (destin.hasNextLine()) {
            tempStorage += destin.nextLine();
        }
        invocation = new JSONObject(tempStorage);
        this.returnInventory = convertFromJson(invocation);
    }

    // Requires: A valid JSONObject to be passed from readFromJson method
    // Modifies: finalProduct
    // Effects: Reads the passed JSONObject and recreates the InventoryIO object.
    public ArrayList<InventoryIO> convertFromJson(JSONObject read) throws FileNotFoundException {
        ArrayList<InventoryIO> makeInventory = new ArrayList<>();
        JSONArray test = read.getJSONArray("Inventory");

        for (Object ob : test) {
            JSONObject finalObject = (JSONObject)ob;
            InventoryIO finalProduct = new InventoryIO();
            String color = (String) finalObject.get("color");
            finalProduct.setColor(color);
            String name = (String) finalObject.get("name");
            finalProduct.setName(name);
            String desc = (String) finalObject.get("description");
            finalProduct.setDesc(desc);
            boolean isProduct = finalObject.getBoolean("product");
            finalProduct.setProduct(isProduct);
            if (!finalObject.getJSONArray("subInventory").isEmpty()) {
                ArrayList<InventoryIO> subInv = new ArrayList<>();
                subInv = convertSubInventory(finalObject.getJSONArray("subInventory"));
                finalProduct.setSubInventory(subInv);
                makeInventory.add(finalProduct);
            } else {
                makeInventory.add(new InventoryIO(name, desc, isProduct));
            }
        }
        return makeInventory;
    }

    // Requires: A valid JSONArray to be passed from convertFromJson method
    // Modifies: finalProductArray
    // Effects: Recreates the sub-inventory from main; recalls itself recursively if more sub-inventories are found.
    private ArrayList<InventoryIO> convertSubInventory(JSONArray read) {
        ArrayList<InventoryIO> makeSubIn = new ArrayList<>();
        for (Object ab : read) {
            JSONObject finalObjectArray = (JSONObject) ab;
            InventoryIO finalProduct = new InventoryIO();
            String name = (String) finalObjectArray.get("name");
            finalProduct.setName(name);
            String desc = (String) finalObjectArray.get("description");
            finalProduct.setDesc(desc);
            boolean isProduct = finalObjectArray.getBoolean("product");
            finalProduct.setProduct(isProduct);
            if (!finalObjectArray.getJSONArray("subInventory").isEmpty()) {
                ArrayList<InventoryIO> subInv = new ArrayList<>();
                subInv = convertSubInventory(finalObjectArray.getJSONArray("subInventory"));
                finalProduct.setSubInventory(subInv);
                makeSubIn.add(finalProduct);
            } else {
                makeSubIn.add(new InventoryIO(name, desc, isProduct));
            }
        }
        return makeSubIn;
    }


    //Effects: Returns returnInventory object.
    public ArrayList<InventoryIO> getInventoryFromJson() {
        return returnInventory;
    }

}
