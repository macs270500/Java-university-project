package com.company;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class AdminMain extends JFrame{

	private JLabel mainLbl;
	private JLabel optionLbl;
	private JButton btnItem;
	private JButton btnAdd;
	private JButton btnView;
	private JButton btnBack;
	private JButton btnExit;
	private JPanel PanelMain;
	private JPanel PanelSub;


	public AdminMain()
	{
		super("Admin Main");
		setLayout(new FlowLayout());

		this.getContentPane().setBackground(Color.pink);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(500, 450);
		this.setLocationRelativeTo(null);
		this.setVisible(true);

		// Adding label to JFrame
		mainLbl = new JLabel("ISHOP");
		mainLbl.setFont(new Font("Menlo", Font.BOLD,30));
		mainLbl.setForeground(Color.WHITE);

		optionLbl = new JLabel("Choose one of the following to continue:");
		optionLbl.setFont(new Font("serif",Font.PLAIN,14));
		optionLbl.setPreferredSize(new Dimension(460,50));
		optionLbl.setForeground(Color.WHITE);

		add(mainLbl);
		add(optionLbl);


		// Adding buttons in the main panel
		btnItem = new JButton("ITEM RETURN");
		btnItem.setBackground(Color.WHITE);
		try {
			Icon icon = new ImageIcon("img\\returnItem.png");
			btnItem = new JButton("ITEM RETURN", icon);
			btnItem.setToolTipText("Registers items returned to the store");
			int width = 32; int height = 32;
//			Image img = new ImageIcon(this.getClass().getResource("back.png")).getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);
//			Icon r = new ImageIcon(img);
//			btnItem.setIcon(r);
		}
		catch(Exception e)
		{
			JOptionPane.showInputDialog(null, "No Image Has Been Found!!",JOptionPane.ERROR_MESSAGE);
		}

		btnAdd = new JButton("UPDATE & View STOCK");
		btnAdd.setBackground(Color.WHITE);

		try {
			Icon icon = new ImageIcon("img\\update.png");
			btnAdd = new JButton("UPDATE & View STOCK", icon);
			btnAdd.setToolTipText("Registers items that the company bought");

		}
		catch(Exception e)
		{
			JOptionPane.showInputDialog(null, "No Image Has Been Found!!",JOptionPane.ERROR_MESSAGE);
		}

		Icon icon = new ImageIcon("img\\back.png");
		btnBack = new JButton("BACK", icon);
		btnBack.setBackground(Color.WHITE);

		Icon iconExit = new ImageIcon("img\\exit.png");
		btnExit = new JButton("EXIT", iconExit);

		btnExit.setBackground(Color.WHITE);

		PanelMain = new JPanel();
		PanelMain.setLayout(new GridLayout(5,1,0,0));
		PanelMain.setPreferredSize(new Dimension(460,300));
		PanelMain.setBackground(Color.pink);
		PanelMain.add(btnItem);
		PanelMain.add(btnAdd);
		PanelMain.add(btnBack);
		PanelMain.add(btnExit);
		add(PanelMain);


		//Register event handlers
		ButtonHandler handler = new ButtonHandler();
		btnItem.addActionListener(handler);
		btnAdd.addActionListener(handler);
		btnBack.addActionListener(handler);
		btnExit.addActionListener(handler);
	}

	//Event handling
	private class ButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if(event.getSource()==btnItem)
			{
				try {
					GoodsReturn gR = new GoodsReturn();
				} catch (SQLException throwables) {
					throwables.printStackTrace( );
				} catch (ClassNotFoundException e) {
					e.printStackTrace( );
				}
				dispose();
//				ReturnItem form= new ReturnItem();
//				form.getContentPane().setBackground(Color.DARK_GRAY);
//	 	        form.setSize(500, 380);
//	 	        form.setLocationRelativeTo(null);
//	 	        form.setVisible(true);

			}
			if(event.getSource()==btnAdd)
			{
				dispose();
				UpdateStock US = new UpdateStock();
			}
			if(event.getSource()==btnBack)
			{
				POSInterface.frameVisibility(true);
				dispose();
			}
			if(event.getSource()==btnExit)
			{
				if (JOptionPane.showConfirmDialog( btnExit,"Confirm if you Want to Exit","Warning",
			            JOptionPane.YES_NO_OPTION ,JOptionPane.WARNING_MESSAGE)==JOptionPane.YES_OPTION)
			            System.exit(0);
			}
		}
	}

}
