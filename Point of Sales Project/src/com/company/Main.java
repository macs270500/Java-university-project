package com.company;

import javax.swing.*;
import java.sql.SQLException;

//Launches the system
public class Main {

    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName( ));
        POSInterface posInterface = new POSInterface( );
    }

}
