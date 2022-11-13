package ui;

import model.InventoryIO;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.*;
import java.util.ArrayList;

public class InventoryGUI extends InventoryUI {
    private JTree tree1;
    private JTree tree2;
    private JButton saveInventoryButton;
    private JButton loadInventoryButton;
    private JPanel mainInventory;
    private JList list1;
    private DefaultListModel modelOfInv = new DefaultListModel<>();
    private JPopupMenu popUpMenu;
    private ArrayList<InventoryIO> test = initialInventory;

    public InventoryGUI() {
        list1.setModel(modelOfInv);
        /////////////////////
        saveInventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Saved!");
                writeToJson();
            }

        });

        tree1.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode selectedNode =
                        (DefaultMutableTreeNode)tree1.getLastSelectedPathComponent();

                if (selectedNode != null && selectedNode.getUserObject() instanceof InventoryIO) {
                    InventoryIO nodeInfo = (InventoryIO) selectedNode.getUserObject();
                    refreshList(nodeInfo);
                }
            }
        });

        tree1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if (SwingUtilities.isRightMouseButton(e)) {
                    DefaultMutableTreeNode selectedNode =
                            (DefaultMutableTreeNode)tree1.getLastSelectedPathComponent();
                    if (selectedNode != null && selectedNode.getUserObject() instanceof InventoryIO) {
                        InventoryIO nodeInfo = (InventoryIO) selectedNode.getUserObject();
                        setPopUpMenu(e);
                    } else if (selectedNode != null && selectedNode.getUserObject() instanceof String) {
                        setPopUpMenuMainInventory(e);
                    }
                }
            }
        });
        loadInventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                readFromJson();
                refreshJTreeGUI();
                tree1.setModel(tree2.getModel());
                return;
            }
        });
    }

    //EFFECTS: Adds the descriptors to a list, from which user can see what the object holds.
    //REQUIRES: non-null arg.
    public void refreshList(InventoryIO obj) {
        modelOfInv.removeAllElements();
        modelOfInv.addElement("NAME? : " + obj.getName());
        modelOfInv.addElement("DESCRIPTION? : " + obj.getDescription());
        modelOfInv.addElement("PRODUCT? : " + obj.getProduct());
        modelOfInv.addElement("COLOR? : " + obj.getColor());
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
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Inventory");
        tree1 = new JTree(root);
        for (InventoryIO in : initialInventory) {
            DefaultMutableTreeNode i = new DefaultMutableTreeNode(in);
            root.add(i);

            if (in.getSubInventory().size() > 0) {
                recursiveSubInventory(i,in);
            }
        }
        tree1 = new JTree(root);
        add(tree1);


    }

    //REQUIRES: non-null sub-inventory from the passed object.
    //EFFECTS: Simply refreshes the JTree when changes are made.
    //MODIFIES: this.
    private void refreshJTreeGUI() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Inventory");
        tree2 = new JTree(root);
        for (InventoryIO in : initialInventory) {
            DefaultMutableTreeNode i = new DefaultMutableTreeNode(in);
            root.add(i);

            if (in.getSubInventory().size() > 0) {
                recursiveSubInventory(i,in);
            }
        }
        tree2 = new JTree(root);
        add(tree2);


    }
    //REQUIRES: non-null sub-inventory from the passed object.
    //EFFECTS: Does a recursive call of a sub-inventory is found from the parents. Adds it to the parents, and
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

    private void setPopUpMenu(MouseEvent e) {

        popUpMenu = new JPopupMenu("Main");
        JMenu subPopupMenu = new JMenu("Change Descriptors");
        JMenuItem description = new JMenuItem("Change Description");
        JMenuItem name = new JMenuItem("Change Name");
        JMenuItem color = new JMenuItem("Change Color");
        JMenuItem product = new JMenuItem("Change If Product");
        JMenuItem add = new JMenuItem("Add Sub-Inventory Item");
        JMenuItem remove = new JMenuItem("Delete This Inventory Item");
        subPopupMenu.add(description);
        subPopupMenu.add(name);
        subPopupMenu.add(color);
        subPopupMenu.add(product);
        popUpMenu.add(subPopupMenu);
        popUpMenu.add(add);
        popUpMenu.add(remove);
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
    }

    private void setPopUpMenuMainInventory(MouseEvent e) {

        popUpMenu = new JPopupMenu("Main");
        JMenuItem add = new JMenuItem("Add New Main Inventory Item");
        popUpMenu.add(add);
        popUpMenu.show(mainInventory,e.getX(),e.getY());
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addToMainTreeNode();
            }
        });
    }

    private void addToCurrentTreeNode() {

        DefaultMutableTreeNode selectedNode =
                        (DefaultMutableTreeNode)tree1.getLastSelectedPathComponent();

        if (selectedNode != null && selectedNode.getUserObject() instanceof InventoryIO) {
            InventoryIO nodeInfo = (InventoryIO) selectedNode.getUserObject();
            nodeInfo.getSubInventory().add(new InventoryIO());
        }
        refreshJTreeGUI();
        tree1.setModel(tree2.getModel());

    }


    private void removeCurrentTreeNode() {
        DefaultMutableTreeNode selectedNode =
                (DefaultMutableTreeNode)tree1.getLastSelectedPathComponent();
        if (selectedNode != null && selectedNode.getUserObject() instanceof InventoryIO) {
            InventoryIO nodeInfo = (InventoryIO) selectedNode.getUserObject();
            if (initialInventory.contains(nodeInfo)) {
                initialInventory.remove(nodeInfo);
            } else {
                recursiveRemover(nodeInfo,initialInventory);
            }
        }
        refreshJTreeGUI();
        tree1.setModel(tree2.getModel());
    }

    private void recursiveRemover(InventoryIO x, ArrayList<InventoryIO> y) {
        for (InventoryIO main : y) {
            if (main.getSubInventory().contains(x)) {
                main.getSubInventory().remove(x);
            } else if (main.getSubInventory().size() > 0) {
                recursiveRemover(x,main.getSubInventory());
            }
        }
    }

    private void addToMainTreeNode() {
        initialInventory.add(new InventoryIO("yes","yes",true));
        refreshJTreeGUI();
        tree1.setModel(tree2.getModel());
    }
}


