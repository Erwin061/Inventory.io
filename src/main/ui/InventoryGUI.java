package ui;

import model.InventoryIO;
import persistence.ReadWrite;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import java.awt.event.*;
import java.util.ArrayList;
//This class (Y) uses the "initialInventory" ArrayList which holds InventoryIO objects (X). We can add
//multiple InventoryIO objects (X) into our class InventoryGUI (Y) by putting them in this ArrayList.

public class InventoryGUI extends ReadWrite {
    private ArrayList<InventoryIO> initialInventory;
    private JTree mainTree;
    private JTree replacementTree;
    private JButton saveInventoryButton;
    private JButton loadInventoryButton;
    private JPanel mainInventory;
    private JList optionList;
    private DefaultListModel modelOfInv = new DefaultListModel<>();
    private JPopupMenu popUpMenu;
    JMenu subPopupMenu;
    JMenuItem description;
    JMenuItem name;
    JMenuItem color;
    JMenuItem product;
    JMenuItem add;
    JMenuItem remove;

    public InventoryGUI() {
        optionList.setModel(modelOfInv);
        saveFile();
        nodeForList();
        rightMouseClick();
        loadFile();
        changeDescFromList();
    }

    // MODIFIES: this
    // EFFECTS: adds a MouseListener to our mainTree JTree; if an Object of type InventoryIO or String is
    // right-clicked on, it will create a pop-up menu.
    void rightMouseClick() {
        mainTree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if (SwingUtilities.isRightMouseButton(e)) {
                    DefaultMutableTreeNode selectedNode =
                            (DefaultMutableTreeNode) mainTree.getLastSelectedPathComponent();
                    if (selectedNode != null && selectedNode.getUserObject() instanceof InventoryIO) {
                        setPopUpMenu(e);
                    } else if (selectedNode != null && selectedNode.getUserObject() instanceof String) {
                        setPopUpMenuMainInventory(e);
                    }
                }
            }
        });
    }

    //EFFECTS: loads a previously saved state of the Inventory via a button click.
    //MODIFIES: this
    void loadFile() {
        loadInventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initialInventory = readFromJson();
                refreshJTreeGUI();
            }
        });
    }

    //EFFECTS: saves the given state of the Inventory via a button click.
    //MODIFIES: this
    void saveFile() {
        saveInventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Saved!");
                writeToJson(initialInventory);
            }

        });
    }

    //EFFECTS: Passes the current node selected in JTree for the refreshList() method to use as an argument.
    //MODIFIES: this
    public void nodeForList() {
        mainTree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode selectedNode =
                        (DefaultMutableTreeNode) mainTree.getLastSelectedPathComponent();

                if (selectedNode != null && selectedNode.getUserObject() instanceof InventoryIO) {
                    InventoryIO nodeInfo = (InventoryIO) selectedNode.getUserObject();
                    refreshList(nodeInfo);
                }
            }
        });
    }

    //EFFECTS: Adds the descriptors to a list, from which user can see what the object's given descriptors are.
    //REQUIRES: non-null arg.
    public void refreshList(InventoryIO obj) {
        modelOfInv.removeAllElements();
        modelOfInv.addElement("NAME? : " + obj.getName());
        modelOfInv.addElement("DESCRIPTION? : " + obj.getDescription());
        modelOfInv.addElement("PRODUCT? : " + obj.getProduct());
        modelOfInv.addElement("COLOR? : " + obj.getColor());
    }


    public void changeDescFromList() {
        optionList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2) {
                    if (optionList.getSelectedIndex() == 0) {
                        String name = JOptionPane.showInputDialog("Input a name");
                        changeName(name);
                    } else if (optionList.getSelectedIndex() == 1) {
                        String desc = JOptionPane.showInputDialog("Input a description");
                        changeDescription(desc);
                    } else if (optionList.getSelectedIndex() == 2) {
                        int prod = JOptionPane.showConfirmDialog(null, "Is this a product?", "", 1, 3);
                        changeProduct(prod);
                    } else if (optionList.getSelectedIndex() == 3) {
                        String color = JOptionPane.showInputDialog("Input a color");
                        changeColor(color);
                    }
                }
            }
        });
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("InventoryGUI");
        frame.setContentPane(new InventoryGUI().mainInventory);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    //REQUIRES: non-null sub-inventory from the passed object.
    //EFFECTS: Creates a tree from the passed inventory object. Adds it to the parents, and
    //checks if the inventory has a sub-inventory (child). If it does, sends it to a recursive method.
    //MODIFIES: this.
    private void createUIComponents() {
        initialInventory = new ArrayList<>();
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("InventoryGUI");
        mainTree = new JTree(root);
        for (InventoryIO in : initialInventory) {
            DefaultMutableTreeNode i = new DefaultMutableTreeNode(in);
            root.add(i);

            if (in.getSubInventory().size() > 0) {
                recursiveSubInventory(i, in);
            }
        }
        mainTree = new JTree(root);
        add(mainTree);

    }

    //REQUIRES: non-null sub-inventory from the passed object.
    //EFFECTS: Simply refreshes the JTree when changes are made.
    //MODIFIES: this.
    private void refreshJTreeGUI() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("InventoryGUI");
        replacementTree = new JTree(root);
        for (InventoryIO in : initialInventory) {
            DefaultMutableTreeNode i = new DefaultMutableTreeNode(in);
            root.add(i);

            if (in.getSubInventory().size() > 0) {
                recursiveSubInventory(i, in);
            }
        }
        replacementTree = new JTree(root);
        add(replacementTree);
        mainTree.setModel(replacementTree.getModel());
        for (int x = 0; x < mainTree.getRowCount();++x) {
            mainTree.expandRow(x);
        }
    }

    //REQUIRES: non-null sub-inventory from the passed object.
    //EFFECTS: Does a recursive call if a sub-inventory is found from the parents. Adds it to the parents, and
    //rechecks if the sub-inventory also has another sub-inventory (child).
    //MODIFIES: this.
    private void recursiveSubInventory(DefaultMutableTreeNode i, InventoryIO y) {

        for (InventoryIO in : y.getSubInventory()) {
            DefaultMutableTreeNode c = new DefaultMutableTreeNode(in);
            i.add(c);
            if (in.getSubInventory().size() > 0) {
                recursiveSubInventory(c, in);
            }
        }
    }

    //EFFECTS: Sets up the popUpMenu for use.
    //MODIFIES: this.
    private void startPopUpMenu() {
        popUpMenu = new JPopupMenu("Main");
        subPopupMenu = new JMenu("Change Descriptors");
        description = new JMenuItem("Change Description");
        name = new JMenuItem("Change Name");
        color = new JMenuItem("Change Color");
        product = new JMenuItem("Change If Product");
        add = new JMenuItem("Add Sub-Inventory Item");
        remove = new JMenuItem("Delete This Inventory Item");
        subPopupMenu.add(description);
        subPopupMenu.add(name);
        subPopupMenu.add(color);
        subPopupMenu.add(product);
        popUpMenu.add(subPopupMenu);
        popUpMenu.add(add);
        popUpMenu.add(remove);
    }

    //EFFECTS: makes a pop menu with more options.
    //MODIFIES: this.
    private void setPopUpMenu(MouseEvent e) {
        startPopUpMenu();
        popUpMenu.show(mainInventory, e.getX(), e.getY());
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addToCurrentTreeNode();
            }
        });
        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeCurrentTreeNode();
            }
        });
        description.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String desc = JOptionPane.showInputDialog("Input a description");
                changeDescription(desc);
            }
        });

    }

    private void additionalListeners() {
        name.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog("Input a name");
                changeName(name);
            }
        });
        color.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String color = JOptionPane.showInputDialog("Input a color");
                changeColor(color);
            }
        });
        product.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    //EFFECTS: Makes a special pop-up menu only for the main inventory object.
    //MODIFIES: this.
    private void setPopUpMenuMainInventory(MouseEvent e) {

        popUpMenu = new JPopupMenu("Main");
        JMenuItem add = new JMenuItem("Add New Main Inventory Item");
        popUpMenu.add(add);
        popUpMenu.show(mainInventory, e.getX(), e.getY());
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addToMainTreeNode();
            }
        });
    }

    //EFFECTS: Adds a new object to the Sub Inventory of the one currently selected.
    //MODIFIES: this.
    private void addToCurrentTreeNode() {
        DefaultMutableTreeNode selectedNode =
                (DefaultMutableTreeNode) mainTree.getLastSelectedPathComponent();

        if (selectedNode != null && selectedNode.getUserObject() instanceof InventoryIO) {
            InventoryIO nodeInfo = (InventoryIO) selectedNode.getUserObject();
            nodeInfo.getSubInventory().add(new InventoryIO());
        }
        refreshJTreeGUI();
    }

    //EFFECTS: Removes an object from the Sub Inventory of the one currently selected.
    //if one is selected for removal, it keeps searching all sub-inventories until that selected object is found.
    //MODIFIES: this.
    private void removeCurrentTreeNode() {
        DefaultMutableTreeNode selectedNode =
                (DefaultMutableTreeNode) mainTree.getLastSelectedPathComponent();
        if (selectedNode != null && selectedNode.getUserObject() instanceof InventoryIO) {
            InventoryIO nodeInfo = (InventoryIO) selectedNode.getUserObject();
            if (initialInventory.contains(nodeInfo)) {
                initialInventory.remove(nodeInfo);
            } else {
                recursiveRemover(nodeInfo, initialInventory);
            }
        }
        refreshJTreeGUI();
    }

    //EFFECTS: helper method for removeCurrentTreeNode. It recursively checks if an ArrayList of InventoryIO objects
    //matches the one selected for removal.
    //MODIFIES: this.
    private void recursiveRemover(InventoryIO x, ArrayList<InventoryIO> y) {
        for (InventoryIO main : y) {
            if (main.getSubInventory().contains(x)) {
                main.getSubInventory().remove(x);
            } else if (main.getSubInventory().size() > 0) {
                recursiveRemover(x, main.getSubInventory());
            }
        }
    }

    //EFFECTS: Simply adds a new InventoryIO object to the current root node.
    //MODIFIES: this.
    private void addToMainTreeNode() {
        initialInventory.add(new InventoryIO("yes", "yes", true));
        refreshJTreeGUI();
    }

    //EFFECTS: Changes the description of the selected Inventory Object from JTree.
    //MODIFIES: this
    //REQUIRES: a non-empty string to be passed for the argument "s."
    private void changeDescription(String s) {
        DefaultMutableTreeNode selectedNode =
                (DefaultMutableTreeNode) mainTree.getLastSelectedPathComponent();
        if (selectedNode != null && selectedNode.getUserObject() instanceof InventoryIO && s != null) {
            InventoryIO nodeInfo = (InventoryIO) selectedNode.getUserObject();
            while (s.isEmpty()) {
                JOptionPane.showMessageDialog(null, "The description cannot be empty!");
                s = JOptionPane.showInputDialog("Input a description");
            }
            nodeInfo.setDesc(s);
            refreshJTreeGUI();
            refreshList(nodeInfo);
            JOptionPane.showMessageDialog(null, "Successfully changed the description to " + s + "!");
        }
    }

    //EFFECTS: Changes the name of the selected Inventory Object from JTree.
    //MODIFIES: this
    //REQUIRES: a non-empty string to be passed for the argument "s."
    private void changeName(String s) {
        DefaultMutableTreeNode selectedNode =
                (DefaultMutableTreeNode) mainTree.getLastSelectedPathComponent();
        if (selectedNode != null && selectedNode.getUserObject() instanceof InventoryIO && s != null) {
            InventoryIO nodeInfo = (InventoryIO) selectedNode.getUserObject();
            while (s.isEmpty()) {
                JOptionPane.showMessageDialog(null, "The name cannot be empty!");
                s = JOptionPane.showInputDialog("Input a name");
            }
            nodeInfo.setName(s);
            refreshJTreeGUI();
            refreshList(nodeInfo);
            JOptionPane.showMessageDialog(null, "Successfully changed the name to " + s + " !");
        }
    }

    //EFFECTS: Changes the isProduct boolean of the selected Inventory Object from JTree.
    //MODIFIES: this
    //REQUIRES: s!=2
    private void changeProduct(int s) {
        DefaultMutableTreeNode selectedNode =
                (DefaultMutableTreeNode) mainTree.getLastSelectedPathComponent();
        if (selectedNode != null && selectedNode.getUserObject() instanceof InventoryIO && s != 2) {
            InventoryIO nodeInfo = (InventoryIO) selectedNode.getUserObject();
            if (s == 0) {
                nodeInfo.setProduct(true);
                refreshJTreeGUI();
                refreshList(nodeInfo);
                JOptionPane.showMessageDialog(null, "Successfully changed to product!");
            } else {
                nodeInfo.setProduct(false);
                refreshJTreeGUI();
                refreshList(nodeInfo);
                JOptionPane.showMessageDialog(null, "Successfully changed to not a product!");
            }
        }
    }

    //EFFECTS: Changes the color of the selected Inventory Object from JTree.
    //MODIFIES: this
    //REQUIRES: a non-empty string to be passed for the argument "s."
    private void changeColor(String s) {
        DefaultMutableTreeNode selectedNode =
                (DefaultMutableTreeNode) mainTree.getLastSelectedPathComponent();
        if (selectedNode != null && selectedNode.getUserObject() instanceof InventoryIO && s != null) {
            InventoryIO nodeInfo = (InventoryIO) selectedNode.getUserObject();
            while (s.isEmpty()) {
                JOptionPane.showMessageDialog(null, "The color cannot be empty! ('N/A' if not applicable)");
                s = JOptionPane.showInputDialog("Input a color");
            }
            nodeInfo.setColor(s);
            refreshJTreeGUI();
            refreshList(nodeInfo);
            JOptionPane.showMessageDialog(null, "Successfully changed the color to " + s + " !");
        }
    }
}


