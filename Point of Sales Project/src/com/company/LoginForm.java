package com.company;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

//Login and password input
//back button

public class LoginForm extends JFrame {

    POSInterface posInterface;

    static JFrame frame = new JFrame("Login Form");

    JPanel panelCenter = new JPanel(new FlowLayout( ));
    JPanel panelNorth = new JPanel( );
    JPanel panelSouth = new JPanel(new FlowLayout( ));


    JLabel userNameLabel = new JLabel("Username:");
    JLabel passwordLabel = new JLabel("Password:");

    JLabel loginHeader = new JLabel("Login");

    JButton btnLogin = new JButton("Login");
    JButton btnBack = new JButton("Back");


    JTextField txtLgn = new JTextField( );
    JPasswordField txtPassword = new JPasswordField( );

    LoginForm() throws SQLException {
        txtLgn.setPreferredSize(new Dimension(150, 15));
        txtPassword.setPreferredSize(new Dimension(150, 15));

        loginHeader.setFont(new Font("Helvetica", Font.BOLD, 30));

        frame.setVisible(true);
        frame.setMinimumSize(new Dimension(250, 175));
        frame.setLayout(new BorderLayout( ));
        frame.setLocationRelativeTo(null);

        panelSouth.add(btnLogin);
        panelSouth.add(btnBack);

        panelCenter.add(userNameLabel);
        panelCenter.add(txtLgn);
        panelCenter.add(passwordLabel);
        panelCenter.add(txtPassword);

        panelNorth.add(loginHeader);

        frame.add(panelNorth, BorderLayout.NORTH);
        frame.add(panelCenter, BorderLayout.CENTER);
        frame.add(panelSouth, BorderLayout.SOUTH);

        btnLogin.addActionListener(e -> {
            try {
                ResultSet rs = dbConnection.dbExecuteQuery("SELECT * FROM admin WHERE AdminID = \"" + txtLgn.getText( ) + "\"");
                while (rs.next( )) {
                    String Password = new String(txtPassword.getPassword( ));
                    if (Password.equals(rs.getString(2))) {
                        JOptionPane.showMessageDialog(null, "Login Successful");
                        //Menu form goes here
                        AdminMain sf = new AdminMain();
                        txtLgn.setText("");
                        txtPassword.setText("");
                        frame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Incorrect Password", "Error", JOptionPane.WARNING_MESSAGE);
                        txtPassword.setText("");
                    }
                }
                dbConnection.dbClose( );
            } catch (SQLException throwables) {
                throwables.printStackTrace( );
            }
        });

        btnBack.addActionListener(e -> {
            POSInterface.frameVisibility(true);
            frame.dispose( );
        });
    }
}
