package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Formatter;
import java.util.Scanner;

//Clients scan or chooses their products
//then proceeds to check out

public class POSInterface extends JFrame {
    // JFrame
    static JFrame frame;

    // JButton
    static JButton btnAdd, btnEdit, btnDelete, btnClear, b1, b2, b3, b4, b5, b6, b7, b8, b9, b0, bBack, bClear, btnCheckout, btnAdmin;
    JScrollPane scrollPane;
    DefaultTableModel model;
    JTable table;

    //Naming of labels
    JLabel lblID = new JLabel("ID");
    JLabel lblName = new JLabel("Name");
    JLabel lblInStock = new JLabel("Is in stock?");
    JLabel lblPrice = new JLabel("Price per Unit(Rs)");
    JLabel lblQty = new JLabel("Quantity");

    //TextField and combobox creation
    JComboBox cmbxID = new JComboBox( );
    JTextField txtName = new JTextField( );
    JTextField txtInStock = new JTextField( );
    JTextField txtPrice = new JTextField( );
    JTextField txtQty = new JTextField( );

    JLabel totalCost = new JLabel("TOTAL:");
    JTextField txtTotal = new JTextField( );

    // create panels to add components
    JPanel panelWest = new JPanel( );
    JPanel panelEast = new JPanel( );
    JPanel innerWestPanel = new JPanel( );
    JPanel innerNorthEastPanel = new JPanel( );
    JPanel innerSouthEastPanel = new JPanel( );
    JPanel innerCenterEastPanel = new JPanel(new GridLayout(6, 3));

    //Saving the results of the query in the result set rs
    ResultSet rs = dbConnection.dbExecuteQuery("select * from product");

    ResultSetMetaData rsmd = rs.getMetaData( );

    //Defining the column names
    String[] columnNames = {"ID", "Product Name", "Quantity", "Total Price"};

    /*Creating the arrays to hold the data and defining the
    size of the array depending on the number of rows*/
    String[] prodID = new String[dbConnection.dbNoRows( )];
    String[] prodName = new String[dbConnection.dbNoRows( )];
    int[] prodQty = new int[dbConnection.dbNoRows( )];
    double[] prodPrice = new double[dbConnection.dbNoRows( )];

    public POSInterface() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {


        // main class
        // create a new frame to store text field and button
        frame = new JFrame("Point of Sales");
        frame.setMinimumSize(new Dimension(750, 600));
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout( ));

        //Alignment of labels
        lblID.setHorizontalAlignment(SwingConstants.LEFT);
        lblName.setHorizontalAlignment(SwingConstants.LEFT);
        lblInStock.setHorizontalAlignment(SwingConstants.LEFT);
        lblPrice.setHorizontalAlignment(SwingConstants.LEFT);
        lblQty.setHorizontalAlignment(SwingConstants.LEFT);

        // Button Creation with labelling
        btnAdd = new JButton("Add");
        btnEdit = new JButton("Edit");
        btnDelete = new JButton("Delete");
        btnClear = new JButton("Clear");
        b0 = new JButton("0");
        b1 = new JButton("1");
        b2 = new JButton("2");
        b3 = new JButton("3");
        b4 = new JButton("4");
        b5 = new JButton("5");
        b6 = new JButton("6");
        b7 = new JButton("7");
        b8 = new JButton("8");
        b9 = new JButton("9");
        bBack = new JButton("");
        bClear = new JButton("C");
        bBack.setIcon(new ImageIcon("BackSpaceResized.png"));
        btnCheckout = new JButton("Checkout");
        btnAdmin = new JButton("Admin");

        //setting the preferred size of the textfields and the combo box
        Dimension txtDimension = new Dimension(115, 20);
        txtTotal.setPreferredSize(txtDimension);
        cmbxID.setPreferredSize(txtDimension);
        txtName.setPreferredSize(txtDimension);
        txtInStock.setPreferredSize(txtDimension);
        txtPrice.setPreferredSize(txtDimension);
        txtQty.setPreferredSize(txtDimension);

        txtTotal.setText("Rs");

        //Disabling the editable function of the textfields
        txtTotal.setEditable(false);
        txtName.setEditable(false);
        txtInStock.setEditable(false);
        txtPrice.setEditable(false);

        totalCost.setFont(new Font("Helvetica", Font.BOLD, 15));

        //defining the size of the east panel
        panelEast.setPreferredSize(new Dimension(325, 300));

        //Creating the container to contain the table

        //Setting the layout of each panel
        panelEast.setLayout(new BorderLayout( ));
        panelWest.setLayout(new BorderLayout( ));
        innerNorthEastPanel.setLayout(new BoxLayout(innerNorthEastPanel, BoxLayout.Y_AXIS));
        innerSouthEastPanel.setLayout(new FlowLayout( ));
        innerWestPanel.setLayout(new FlowLayout( ));

        //populates the array with data that will be used to display the
        //products available
        int i = 0;
        while (rs.next( )) {
            prodID[i] = rs.getString(1);
            prodName[i] = rs.getString(2);
            prodQty[i] = rs.getInt(3);
            prodPrice[i] = rs.getDouble(4);
            i++;
        }

        model = new DefaultTableModel(null, columnNames);
        table = new JTable(model);

        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer( );

        //Formatting the cells to align the displayed data to the right
        cellRenderer.setHorizontalAlignment(JLabel.RIGHT);


        //Creating the scroll pane
        //to accommodate the table
        scrollPane = new JScrollPane(table);

        //Creating Combo box and populating with
        //product ids
        cmbxID = new JComboBox(prodID);


        //Adding components to the
        //panel on the left
        innerWestPanel.add(totalCost);
        innerWestPanel.add(txtTotal);


        //Adding components to the
        //panel on the right
        JComboBox finalCmbxID1 = cmbxID;

        //Adding an item to the table
        btnAdd.addActionListener(e -> {
            try {
                String productID = finalCmbxID1.getSelectedItem( ).toString( );
                String productName = txtName.getText( );
                int productQuantity = Integer.parseInt(txtQty.getText( ));
                double productPrice = productQuantity * Double.parseDouble(txtPrice.getText( ));
                Object[] row = {productID, productName, productQuantity, productPrice};
                model.addRow(row);
                try {
                    totalCostUpdate( );
                } catch (SQLException throwables) {
                    throwables.printStackTrace( );
                }
            } catch (NumberFormatException NFE) {
                JOptionPane.showMessageDialog(null, "Scan barcode or select the product ID", "Product not found", JOptionPane.ERROR_MESSAGE);
            }
        });

        //Editing an item in the table
        btnEdit.addActionListener(e -> {
            int index = table.getSelectedRow( );
            if (index >= 0) {
                String cmbxData = finalCmbxID1.getSelectedItem( ).toString( );
                int productQuantity = Integer.parseInt(txtQty.getText( ));
                double productPrice = productQuantity * Double.parseDouble(txtPrice.getText( ));
                model.setValueAt(cmbxData, index, 0);
                model.setValueAt(txtName.getText( ), index, 1);
                model.setValueAt(txtQty.getText( ), index, 2);
                model.setValueAt(productPrice, index, 3);
            } else {
                JOptionPane.showMessageDialog(null, "No item found to edit", "Error", JOptionPane.ERROR_MESSAGE);
            }
            try {
                totalCostUpdate( );
            } catch (SQLException throwables) {
                throwables.printStackTrace( );
            }
        });

        //Deleting an item in the table
        btnDelete.addActionListener(e -> {
            try {
                model.removeRow(table.getSelectedRow( ));
                try {
                    totalCostUpdate( );
                } catch (SQLException throwables) {
                    throwables.printStackTrace( );
                }
            } catch (ArrayIndexOutOfBoundsException AIOOBE) {
                JOptionPane.showMessageDialog(null, "Select an item to delete", "Item not selected", JOptionPane.WARNING_MESSAGE);
            }
        });

        //Clears the table
        btnClear.addActionListener(e -> {
            for (int J = 0; J < model.getRowCount( ); ) {
                model.removeRow(0);
                revalidate( );
            }
            txtTotal.setText("Rs 0");
        });

        //Clears the textField Quantity
        bClear.addActionListener(e -> {
            txtQty.setText("");
        });

        //Deletes a single character in the textfield Quantity
        bBack.addActionListener(e -> {
            try {
                String Quantity = txtQty.getText( );
                StringBuilder sb = new StringBuilder(Quantity);
                txtQty.setText(sb.deleteCharAt(Quantity.length( ) - 1).toString( ));
            } catch (StringIndexOutOfBoundsException SIOOBE) {

            }
        });

        //Function to populate combo box
        JComboBox finalCmbxID = cmbxID;

        //Auto displays the information concerning the product
        cmbxID.addItemListener(e -> {
            int index = finalCmbxID.getSelectedIndex( );
            txtName.setText(prodName[index]);
            txtPrice.setText(String.valueOf(prodPrice[index]));
            if (prodQty[index] > 0) {
                txtInStock.setText("Yes");
            } else {
                txtInStock.setText("No");
            }
            txtQty.setText("1");
        });

        b1.addActionListener(e -> {
            txtQty.setText(txtQty.getText( ) + "1");
        });
        b2.addActionListener(e -> {
            txtQty.setText(txtQty.getText( ) + "2");
        });
        b3.addActionListener(e -> {
            txtQty.setText(txtQty.getText( ) + "3");
        });
        b4.addActionListener(e -> {
            txtQty.setText(txtQty.getText( ) + "4");
        });
        b5.addActionListener(e -> {
            txtQty.setText(txtQty.getText( ) + "5");
        });
        b6.addActionListener(e -> {
            txtQty.setText(txtQty.getText( ) + "6");
        });
        b7.addActionListener(e -> {
            txtQty.setText(txtQty.getText( ) + "7");
        });
        b8.addActionListener(e -> {
            txtQty.setText(txtQty.getText( ) + "8");
        });
        b9.addActionListener(e -> {
            txtQty.setText(txtQty.getText( ) + "9");
        });
        b0.addActionListener(e -> {
            txtQty.setText(txtQty.getText( ) + "0");
        });

        //Opens a login form to restrict access to the stock control system
        btnAdmin.addActionListener(e -> {
            try {
                LoginForm form = new LoginForm( );
                frameVisibility(false);
            } catch (SQLException throwables) {
                throwables.printStackTrace( );
            }
        });

        btnCheckout.addActionListener(e -> {
            if(model.getRowCount() == 0){
                JOptionPane.showMessageDialog(null,"No item in cart");
            }
            else {
                try {
                    String[] productID = new String[table.getRowCount( )];
                    String[] productName = new String[table.getRowCount( )];
                    int[] productQuantity = new int[table.getRowCount( )];
                    double[] productPrice = new double[table.getRowCount( )];
                    int receiptNum = 0;

                    File receiptNumber = new File("Receipt Number.txt");
                    Scanner sc = new Scanner(receiptNumber);
                    while (sc.hasNext( )) {
                        receiptNum = sc.nextInt( );
                    }

                    Formatter outfile = new Formatter("Receipts\\Receipt" + receiptNum + ".txt");
                    outfile.format("Receipt number: %d\n", receiptNum);
                    outfile.format("%-70s | %5s |%7s\n", "Product Name", "Quantity", "Price per unit");

                    for (int k = 0; k < table.getRowCount( ); k++) {

                        productID[k] = table.getModel( ).getValueAt(k, 0).toString( );
                        productName[k] = table.getModel( ).getValueAt(k, 1).toString( );
                        productQuantity[k] = Integer.parseInt(table.getModel( ).getValueAt(k, 2).toString( ));
                        productPrice[k] = (Double) table.getModel( ).getValueAt(k, 3);

                        String sqlStatement = "INSERT INTO `soldproducts`(`ProductID`, `ProductName`, `Quantity`, `Price`, `receiptID`) VALUES ('" + productID[k] + "','" + productName[k] + "','" + productQuantity[k] + "','" + productPrice[k] + "','" + receiptNum + "')";

                        dbConnection.databaseExecuteQuery(sqlStatement);

                        sqlStatement = "SELECT `ProductID`, `ProductName`, `Quantity`, `UnitPrice` FROM `product` WHERE ProductID = \"" + productID[k] + "\"";
                        String prodName;
                        int prodQuantity;
                        double unitPrice = 0;

                        ResultSet resultSet = dbConnection.dbExecuteQuery(sqlStatement);

                        while (resultSet.next( )) {
                            prodName = resultSet.getString(2);
                            prodQuantity = resultSet.getInt(3);
                            unitPrice = resultSet.getDouble(4);
                            if (prodQuantity >= productQuantity[k]) {
                                prodQuantity -= productQuantity[k];
                            } else {
                                JOptionPane.showMessageDialog(null, productName[k] + "is out of stock", "Out of stock", JOptionPane.WARNING_MESSAGE);
                                break;
                            }
                            sqlStatement = "UPDATE `product` SET `Quantity` = \"" + prodQuantity + "\" WHERE ProductID =\"" + productID[k] + "\"";
                            dbConnection.databaseExecuteQuery(sqlStatement);
                            outfile.format("%-70s | %8s |%7s\n", prodName, productQuantity[k], unitPrice);
                        }

                    }
                    outfile.format("%75s Total: %s", "", txtTotal.getText( ));
                    outfile.close( );
                    dbConnection.dbClose( );


                    CheckOut chk = new CheckOut( );

                    receiptNum += 1;

                    FileWriter fileWriter = new FileWriter(receiptNumber);
                    fileWriter.write(String.valueOf(receiptNum));
                    fileWriter.close( );

                    reloadRs( );

                    model.setRowCount(0);
                } catch (SQLException | IOException | SecurityException | ClassNotFoundException throwables) {
                    throwables.printStackTrace( );
                }
            }
        });


        //Inner North East Panel Components
        innerNorthEastPanel.add(lblID);
        innerNorthEastPanel.add(cmbxID);
        innerNorthEastPanel.add(lblName);
        innerNorthEastPanel.add(txtName);
        innerNorthEastPanel.add(lblInStock);
        innerNorthEastPanel.add(txtInStock);
        innerNorthEastPanel.add(lblPrice);
        innerNorthEastPanel.add(txtPrice);
        innerNorthEastPanel.add(lblQty);
        innerNorthEastPanel.add(txtQty);


        //Inner Center East Panel Components
        innerCenterEastPanel.add(b1);
        innerCenterEastPanel.add(b2);
        innerCenterEastPanel.add(b3);
        innerCenterEastPanel.add(b4);
        innerCenterEastPanel.add(b5);
        innerCenterEastPanel.add(b6);
        innerCenterEastPanel.add(b7);
        innerCenterEastPanel.add(b8);
        innerCenterEastPanel.add(b9);
        innerCenterEastPanel.add(b0);
        innerCenterEastPanel.add(bClear);
        innerCenterEastPanel.add(bBack);
        innerCenterEastPanel.add(btnAdd);
        innerCenterEastPanel.add(btnEdit);
        innerCenterEastPanel.add(btnDelete);
        innerCenterEastPanel.add(btnClear);
        innerCenterEastPanel.add(btnCheckout);
        innerCenterEastPanel.add(btnAdmin);

        //Panel West Components
        panelWest.add(scrollPane, BorderLayout.CENTER);
        panelWest.add(innerWestPanel, BorderLayout.SOUTH);
        panelEast.add(innerNorthEastPanel, BorderLayout.NORTH);
        panelEast.add(innerCenterEastPanel, BorderLayout.CENTER);

        // add panel to frame
        frame.add(panelWest, BorderLayout.WEST);
        frame.add(panelEast, BorderLayout.CENTER);

        //frame.pack() automatically set the size of the frame to accommodate
        // all the components
        frame.pack( );
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }


    //Updates the textfield total after an action has been performed
    public void totalCostUpdate() throws SQLException {
        double totalCostEstimate = 0;
        for (int i = 0; i < table.getModel( ).getRowCount( ); i++) {
            totalCostEstimate += Double.parseDouble(model.getValueAt(i, 3).toString( ));
        }
        txtTotal.setText("Rs " + totalCostEstimate);
    }

    public static void frameVisibility(Boolean bool) {
        frame.setVisible(bool);
    }

    public void reloadRs() throws SQLException {
        rs = dbConnection.dbExecuteQuery("SELECT * FROM product");
        int i = 0;
        while (rs.next( )) {
            prodID[i] = rs.getString(1);
            prodName[i] = rs.getString(2);
            prodQty[i] = rs.getInt(3);
            prodPrice[i] = rs.getDouble(4);
            i++;
        }
    }

}