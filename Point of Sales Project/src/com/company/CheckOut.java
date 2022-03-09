package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

//Checkout form
//Client receives a receipt printed on screen and on paper.
public class CheckOut extends JFrame {
    JFrame frame = new JFrame("Checkout");

    JPanel panel = new JPanel(new BorderLayout(  ));

    String[] columnNames = {"ID", "Product Name", "Quantity", "Total Price"};
    DefaultTableModel model = new DefaultTableModel(null, columnNames);
    JTable table;
    JScrollPane scrollPane;

    JLabel label = new JLabel( "Checkout" );

    JButton btnBack = new JButton( "back" );

    CheckOut() throws SQLException, ClassNotFoundException, FileNotFoundException {

        Connection con = dbConnection.connection( );
        Statement statement = con.createStatement( );

        int receiptNum = 0;

        File receiptNumber = new File("Receipt Number.txt");
        Scanner sc = new Scanner(receiptNumber);
        while (sc.hasNext( )) {
            receiptNum = sc.nextInt( );
        }
        String sql = "SELECT * FROM `soldproducts` WHERE `receiptID` =" + receiptNum;

        ResultSet rs = statement.executeQuery(sql);

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

        btnBack.addActionListener(e->{
            frame.dispose();
        });

        table = new JTable(model);

        scrollPane = new JScrollPane(table);

        panel.add(label,BorderLayout.NORTH);
        panel.add(scrollPane,BorderLayout.CENTER);
        panel.add(btnBack, BorderLayout.SOUTH);

        frame.add(panel);

        frame.setMinimumSize(new Dimension(500, 500));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}