package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class UpdateStock extends JFrame {

    private JPanel panel;
    private JLabel LblMain;
    private JLabel LblDisplay;
    private JTable Table;
    private JLabel LblSelect;
    private JLabel LblAdd;
    private JLabel LblID;
    private JLabel LblName;
    private JLabel LblQty;
    private JLabel LblPrice;
    private JTextField jtfID;
    private JTextField jtfName;
    private JTextField jtfQty;
    private JTextField jtfPrice;
    private JButton btnDelete;
    private JButton btnAdd;
    private JButton btnBack;
    private JButton btnExit;
    private JPanel Group;
    private JPanel Group1;
    private JLabel blank2;
    private JButton btnClear;
    private JButton btnUpdate;

    private JLabel lblDisplay;
    private JTextField jtfDisplay;
    private JButton btnDisplay;
    private JPanel pnlDisplay;

    JScrollPane sp;


    static ResultSet result = null;


    public UpdateStock() {
        super("Update System");
        setLayout(new FlowLayout( ));

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane( ).setBackground(Color.pink);
        this.setSize(900, 750);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        //header and instruction label
        LblMain = new JLabel("Ishop");
        LblMain.setFont(new Font("Menlo", Font.BOLD, 30));
        LblMain.setForeground(Color.WHITE);
        add(LblMain);

        LblDisplay = new JLabel("All Items Are Displayed Below!");
        LblDisplay.setPreferredSize(new Dimension(460, 15));
        LblDisplay.setFont(new Font("serif", Font.PLAIN, 14));
        LblDisplay.setForeground(Color.WHITE);
        add(LblDisplay);

        // inserting table
        Table = new JTable( );
        loadData();
        Table.setEnabled(false);
        JScrollPane sp = new JScrollPane(Table);
        sp.setPreferredSize(new Dimension(460, 150));
//		add(sp);

        btnDelete = new JButton("DELETE");
        btnUpdate = new JButton("UPDATE");
        btnClear = new JButton("CLEAR");

        btnDelete.setBackground(Color.WHITE);
        btnUpdate.setBackground(Color.WHITE);
        btnClear.setBackground(Color.WHITE);

        btnAdd = new JButton("ADD");
        btnBack = new JButton("BACK");
        btnExit = new JButton("EXIT");

        btnAdd.setBackground(Color.WHITE);
        btnBack.setBackground(Color.WHITE);
        btnExit.setBackground(Color.WHITE);

        //Adding buttons in Panel Group
        Group = new JPanel( );
        Group.setLayout(new GridLayout(6, 1));
        Group.setBackground(Color.pink);
        Group.add(btnDelete);
        Group.add(btnUpdate);
        Group.add(btnClear);
        Group.add(btnAdd);
        Group.add(btnBack);
        Group.add(btnExit);
//		Group.setPreferredSize(new Dimension(460,60));


        panel = new JPanel( );
        panel.setLayout(new GridLayout(1, 2, 0, 0));
        panel.setPreferredSize(new Dimension(760, 300));
        panel.setBackground(Color.pink);
        panel.add(sp);
        panel.add(Group);
        add(panel);


        // inserting table

//		Table = new JTable();	
//		
//		Table.setEnabled(false);
//		JScrollPane sp = new JScrollPane(Table);
//		sp.setPreferredSize(new Dimension(460,150));
//		add(sp);


//		
//		LblSelect = new JLabel("Enter Product ID and Choose Option Below");
//		LblSelect.setFont(new Font("serif",Font.PLAIN,13));
//		LblSelect.setPreferredSize(new Dimension(460,15));
//		LblSelect.setForeground(Color.WHITE);
//		add(LblSelect);

        //Panel display + controls
        lblDisplay = new JLabel("Enter ID From Table");
        lblDisplay.setFont(new Font("serif", Font.PLAIN, 16));
        lblDisplay.setForeground(Color.WHITE);
        jtfDisplay = new JTextField( );


        btnDisplay = new JButton("Display");
        btnDisplay.setBackground(Color.WHITE);

        pnlDisplay = new JPanel( );
        pnlDisplay.setLayout(new GridLayout(1, 3));
        pnlDisplay.setBackground(Color.pink);
        pnlDisplay.setPreferredSize(new Dimension(460, 25));
        pnlDisplay.add(lblDisplay);
        pnlDisplay.add(jtfDisplay);
        pnlDisplay.add(btnDisplay);
        getContentPane( ).add(pnlDisplay, BorderLayout.CENTER);


//		//Adding product section
//		LblAdd = new JLabel("ADD/UPDATE SECTION");
//		LblAdd.setFont(new Font("serif",Font.PLAIN,20));
//		LblAdd.setForeground(Color.WHITE);
//		add(LblAdd);

        //blank label to create space in JFrame
        blank2 = new JLabel("");
        blank2.setPreferredSize(new Dimension(460, 5));
        add(blank2);


        LblID = new JLabel("Product ID:");
        LblID.setFont(new Font("serif", Font.PLAIN, 14));
        LblID.setForeground(Color.WHITE);


        jtfID = new JTextField(11);


        LblName = new JLabel("Product Name:");
        LblName.setFont(new Font("serif", Font.PLAIN, 14));
        LblName.setForeground(Color.WHITE);


        jtfName = new JTextField(11);


        LblQty = new JLabel("Quantity:");
        LblQty.setFont(new Font("serif", Font.PLAIN, 14));
        LblQty.setForeground(Color.WHITE);


        jtfQty = new JTextField(11);


        LblPrice = new JLabel("Unit Price:");
        LblPrice.setFont(new Font("serif", Font.PLAIN, 14));
        LblPrice.setForeground(Color.WHITE);


        jtfPrice = new JTextField(11);

        //JPanel for grouping labels and text fields
        Group1 = new JPanel( );
        Group1.setLayout(new GridLayout(4, 2));
        Group1.setBackground(Color.pink);
        Group1.setPreferredSize(new Dimension(460, 100));
        Group1.add(LblID);
        Group1.add(jtfID);
        Group1.add(LblName);
        Group1.add(jtfName);
        Group1.add(LblQty);
        Group1.add(jtfQty);
        Group1.add(LblPrice);
        Group1.add(jtfPrice);
        add(Group1);


        //adding buttons

//		btnDelete = new JButton("DELETE");
//		btnUpdate = new JButton("UPDATE");
//		btnClear = new JButton("CLEAR");
//		
//		btnDelete.setBackground(Color.WHITE);
//		btnUpdate.setBackground(Color.WHITE);
//		btnClear.setBackground(Color.WHITE);
//		
//		btnAdd = new JButton("ADD");
//		btnBack = new JButton("BACK");
//		btnExit = new JButton("EXIT");
//		
//		btnAdd.setBackground(Color.WHITE);
//		btnBack.setBackground(Color.WHITE);
//		btnExit.setBackground(Color.WHITE);
//		
//		//Adding buttons in Panel Group
//		Group = new JPanel();
//		Group.setLayout(new GridLayout(6,1));
//		Group.setBackground(Color.pink);
//		Group.add(btnDelete);
//		Group.add(btnUpdate);
//		Group.add(btnClear);
//		Group.add(btnAdd);
//		Group.add(btnBack);
//		Group.add(btnExit);
//		Group.setPreferredSize(new Dimension(460,60));
//		add(Group);

        //Register Event handler
        ButtonHandler handler = new ButtonHandler( );
        btnDisplay.addActionListener(handler);
        btnDelete.addActionListener(handler);
        btnUpdate.addActionListener(handler);
        btnClear.addActionListener(handler);
        btnAdd.addActionListener(handler);
        btnBack.addActionListener(handler);
        btnExit.addActionListener(handler);


    }

    //Event handling

    private Object getValueAt(int selectedRowIndex, int i) {
        // TODO Auto-generated method stub
        return null;
    }

    private class ButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            //button display
            if (event.getSource( ) == btnDisplay) {

                try {
                    //connection to select from database
                    Connection con = dbConnection.connection();

                    Statement stmt = con.createStatement( );
                    String sql1 = "select * from Product";
                    ResultSet rs1 = stmt.executeQuery(sql1);

                    String CID = jtfDisplay.getText( );
                    Boolean target = false;

                    //fill in text fields
                    while (rs1.next( )) {

                        String ID = rs1.getString("ProductID");
                        String Name = rs1.getString("ProductName");
                        String Qty = rs1.getString("Quantity");
                        String Price = rs1.getString("UnitPrice");
                        if (CID.equals(ID)) {
                            jtfID.setText(ID);
                            jtfName.setText(Name);
                            jtfQty.setText(Qty);
                            jtfPrice.setText(Price);

                            target = true;
                        }
                    }
                    if (target == false) {
                        JOptionPane.showMessageDialog(null, "Invalid product ID ", null, JOptionPane.ERROR_MESSAGE);
                    }
                    jtfDisplay.setText(null);
                } catch (SQLException exe) {
                    JOptionPane.showMessageDialog(null, "Cannot Load Data,  SQL Exception" + exe);
                    exe.printStackTrace( );
                } catch (ClassNotFoundException nul) {
                    JOptionPane.showMessageDialog(null, "SQL Connection Error");
                }
            }


            //button delete
            if (event.getSource( ) == btnDelete) {
                if (JOptionPane.showConfirmDialog(btnExit, "Confirm if you want to delete selected item", "Warning",
                        JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
                    try {
                        //connection to database product
                        Connection con = dbConnection.connection();

                        String ID = jtfID.getText( );
                        String Name = jtfName.getText( );

                        Statement statement2 = con.createStatement( );
                        statement2.execute("DELETE FROM Product where ProductID = '" + ID + "' AND ProductName = '" + Name + "' ");
                        JOptionPane.showMessageDialog(null, "Product Deleted!");
                        con.close( );
                        jtfID.setText(null);
                        jtfName.setText(null);
                        jtfQty.setText(null);
                        jtfPrice.setText(null);

                        //refreshTable();
                        remove(Table);
                        loadData( );

                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(null, e1);
                    }
                }


            }
            //button update
            if (event.getSource( ) == btnUpdate) {
                if (JOptionPane.showConfirmDialog(btnExit, "Confirm if you want to update selected item", "Warning",
                        JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
                    try {

                        Connection con = dbConnection.connection();

                        String sql = "Update product Set ProductName = ?, UnitPrice = ?, Quantity = ? where ProductID = ?";
                        PreparedStatement ps = con.prepareStatement(sql);
                        ps.setString(1, jtfName.getText( ));
                        ps.setString(2, jtfPrice.getText( ));
                        ps.setString(3, jtfQty.getText( ));
                        ps.setString(4, jtfID.getText( ));

                        ps.executeUpdate( );
                        JOptionPane.showMessageDialog(null, "Product Updated!");
                        con.close( );
                        jtfID.setText(null);
                        jtfName.setText(null);
                        jtfQty.setText(null);
                        jtfPrice.setText(null);
                        //refreshTable();
                        remove(Table);
                        loadData( );

                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(null, e1);
                    }
                }
            }
            if (event.getSource( ) == btnClear) {
                jtfID.setText(null);
                jtfName.setText(null);
                jtfQty.setText(null);
                jtfPrice.setText(null);
            }
            //button add
            if (event.getSource( ) == btnAdd) {
                if (JOptionPane.showConfirmDialog(btnExit, "Confirm if you want to add new item", "Warning",
                        JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
                    try {
                        Connection con = dbConnection.connection();

                        Statement stmt = con.createStatement( );
                        String sql1 = "select * from `product`";
                        ResultSet rs1 = stmt.executeQuery(sql1);

                        String CID = jtfID.getText( );
                        Boolean target = false;

                        while (rs1.next( )) {

                            String ID = rs1.getString("ProductID");

                            if (CID.equals(ID)) {
                                target = true;
                                JOptionPane.showMessageDialog(null, "Invalid Product ID ", null, JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        if (target == false) {
                            try {
                                Connection con2 = dbConnection.connection();

                                String sql = "insert into product values (?, ?, ?, ?)";
                                PreparedStatement ps = con2.prepareStatement(sql);
                                ps.setString(1, jtfID.getText( ));
                                ps.setString(2, jtfName.getText( ));
                                ps.setInt(3, Integer.parseInt(jtfQty.getText( )));
                                ps.setDouble(4, Double.parseDouble(jtfPrice.getText( )));


                                ps.executeUpdate( );
                                JOptionPane.showMessageDialog(null, "Product Added!");
                                con.close( );
                                jtfID.setText(null);
                                jtfName.setText(null);
                                jtfQty.setText(null);
                                jtfPrice.setText(null);
                                //refreshTable();
                                remove(Table);
                                loadData( );

                            } catch (Exception e1) {
                                JOptionPane.showMessageDialog(null, e1);
                            }

                        }

                    } catch (SQLException exe) {
                        JOptionPane.showMessageDialog(null, "Cannot Load Data,  SQL Exception" + exe);
                        exe.printStackTrace( );
                    } catch (ClassNotFoundException nul) {

                    } catch (NumberFormatException num) {

                    }
                }
            }
            //button back
            if (event.getSource( ) == btnBack) {
                dispose( );
                AdminMain sf = new AdminMain( );
            }
            if (event.getSource( ) == btnExit) {
                if (JOptionPane.showConfirmDialog(btnExit, "Confirm if you Want to Exit", "Warning",
                        JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }


            }

        }
    }

    //function to load table
    public void loadData() {
        //open connection
        try {
            Connection con = dbConnection.connection();

            Statement stmt = con.createStatement( );
            String sql = "SELECT * FROM `product`";
            ResultSet rs = stmt.executeQuery(sql);

            String[] columnNames = {"ID", "Product Name", "Quantity", "Total Price"};
            DefaultTableModel model = new DefaultTableModel(null,columnNames);

            while (rs.next( )) {
                model.addRow(new Object[]{
                        rs.getString(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getDouble(4)
                });

            }
            Table.setModel(model);
            Table.setAutoResizeMode(1);
            Table.getColumnModel( ).getColumn(0).setPreferredWidth(65);
            Table.getColumnModel( ).getColumn(1).setPreferredWidth(200);
            Table.getColumnModel( ).getColumn(2).setPreferredWidth(60);
            Table.getColumnModel( ).getColumn(3).setPreferredWidth(65);


        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Could not load table");
            ex.printStackTrace();
        }
    }
}