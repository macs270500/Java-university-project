package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class GoodsReturn extends JFrame {

    JFrame frame = new JFrame("Goods Return");

    JPanel panel, panelNorthCenter, panelCenter, panelWest, panelSouthCenter, panelCenterC;

    static JButton b1, b2, b3, b4, b5, b6, b7, b8, b9, b0, bBack, bClear;


    String[] columnNames = {"ID", "Product Name", "Quantity", "Total Price"};
    DefaultTableModel model = new DefaultTableModel(null, columnNames);
    JTable table;
    JScrollPane scrollPane;

    JLabel receiptLbl = new JLabel("Receipt Number:");

    JTextField receiptTxt = new JTextField( );

    JButton btnRetrieve = new JButton("Retrieve Receipt");
    JButton btnReturn = new JButton("Return Selected Goods");
    JButton btnback = new JButton("Back");


    GoodsReturn() throws SQLException, ClassNotFoundException {
        table = new JTable(model);
        scrollPane = new JScrollPane(table);

        frame.setLayout(new BorderLayout( ));
        frame.setMinimumSize(new Dimension(800, 500));
        frame.setLocationRelativeTo(null);

        receiptTxt.setPreferredSize(new Dimension(100, 25));

        panel = new JPanel(new BorderLayout( ));
        panelCenter = new JPanel(new BorderLayout( ));
        panelNorthCenter = new JPanel(new FlowLayout( ));
        panelSouthCenter = new JPanel(new FlowLayout( ));
        panelWest = new JPanel(new FlowLayout( ));
        panelCenterC = new JPanel(new GridLayout(4, 3));

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

        Connection con = dbConnection.connection( );
        Statement statement = con.createStatement( );

        btnRetrieve.addActionListener(e -> {

            model.setRowCount(0);

            String receiptID = receiptTxt.getText( );

            String sqlRetrieve = "SELECT * FROM `soldproducts` WHERE `receiptID`=" + receiptID;
            try {
                ResultSet rs = statement.executeQuery(sqlRetrieve);
                String productID;
                String productName;
                int productQuantity;
                double productPrice;

                while (rs.next( )) {
                    productID = rs.getString(1);
                    productName = rs.getString(2);
                    productQuantity = rs.getInt(3);
                    productPrice = rs.getDouble(4);

                    model.addRow(new Object[]{productID, productName, productQuantity, productPrice});
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace( );
            }
        });

        b1.addActionListener(e -> {
            receiptTxt.setText(receiptTxt.getText( ) + "1");
        });
        b2.addActionListener(e -> {
            receiptTxt.setText(receiptTxt.getText( ) + "2");
        });
        b3.addActionListener(e -> {
            receiptTxt.setText(receiptTxt.getText( ) + "3");
        });
        b4.addActionListener(e -> {
            receiptTxt.setText(receiptTxt.getText( ) + "4");
        });
        b5.addActionListener(e -> {
            receiptTxt.setText(receiptTxt.getText( ) + "5");
        });
        b6.addActionListener(e -> {
            receiptTxt.setText(receiptTxt.getText( ) + "6");
        });
        b7.addActionListener(e -> {
            receiptTxt.setText(receiptTxt.getText( ) + "7");
        });
        b8.addActionListener(e -> {
            receiptTxt.setText(receiptTxt.getText( ) + "8");
        });
        b9.addActionListener(e -> {
            receiptTxt.setText(receiptTxt.getText( ) + "9");
        });
        b0.addActionListener(e -> {
            receiptTxt.setText(receiptTxt.getText( ) + "0");
        });
        bClear.addActionListener(e -> {
            receiptTxt.setText("");
        });

        //Deletes a single character in the textfield Quantity
        bBack.addActionListener(e -> {
            try {
                String Quantity = receiptTxt.getText( );
                StringBuilder sb = new StringBuilder(Quantity);
                receiptTxt.setText(sb.deleteCharAt(Quantity.length( ) - 1).toString( ));
            } catch (StringIndexOutOfBoundsException SIOOBE) {

            }
        });

        btnReturn.addActionListener(e -> {

            int selectedRow = table.getSelectedRow( );

            String productID = table.getModel( ).getValueAt(selectedRow, 0).toString( );

            int productQuantity = Integer.parseInt(table.getModel( ).getValueAt(selectedRow, 2).toString( ));

            String retrieveQuantity = "SELECT * FROM `product` WHERE `ProductID` = \"" + productID + "\"";

            //Variable used to update the quantity by taking the amount that is available
            // and adding back the amount that was sold then update the stock amount.
            int updatedQuantity = 0;

            try {
                ResultSet rs = statement.executeQuery(retrieveQuantity);
                while (rs.next( )) {
                    updatedQuantity = rs.getInt(3);
                }

                updatedQuantity += productQuantity;

                String sqlReturn = "UPDATE `product` SET `Quantity`=" + updatedQuantity + " WHERE `ProductID` =\"" + productID + "\"";

                PreparedStatement preparedStatement = con.prepareStatement(sqlReturn);
                preparedStatement.executeUpdate( );

                String sqlDelete = "DELETE FROM `soldproducts` WHERE `ProductID` = \""+productID+"\" AND receiptID =\""+receiptTxt.getText()+"\"";
                preparedStatement = con.prepareStatement(sqlDelete);
                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(null,"Goods successfully returned and recorded","Goods return",JOptionPane.INFORMATION_MESSAGE);
                btnRetrieve.doClick();
            } catch (SQLException throwables) {
                throwables.printStackTrace( );
            }
        });

        btnback.addActionListener(e->{
            AdminMain adminMain = new AdminMain();
            frame.dispose();
        });

        panelNorthCenter.add(receiptLbl);
        panelNorthCenter.add(receiptTxt);
        panelNorthCenter.add(btnRetrieve);

        panelSouthCenter.add(btnReturn);
        panelSouthCenter.add(btnback);

        panelCenterC.add(b1);
        panelCenterC.add(b2);
        panelCenterC.add(b3);
        panelCenterC.add(b4);
        panelCenterC.add(b5);
        panelCenterC.add(b6);
        panelCenterC.add(b7);
        panelCenterC.add(b8);
        panelCenterC.add(b9);
        panelCenterC.add(b0);
        panelCenterC.add(bClear);
        panelCenterC.add(bBack);

        panelCenter.add(panelNorthCenter, BorderLayout.NORTH);
        panelCenter.add(panelSouthCenter, BorderLayout.SOUTH);
        panelCenter.add(panelCenterC, BorderLayout.CENTER);

        panelWest.add(scrollPane);

        panel.add(panelWest, BorderLayout.WEST);
        panel.add(panelCenter, BorderLayout.CENTER);

        frame.add(panel);

        frame.setVisible(true);
    }
}
