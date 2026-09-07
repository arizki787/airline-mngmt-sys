package com.sysairline;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.*;
import java.sql.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class Home extends JFrame implements ActionListener{
	
	JMenuItem flightDetails, customerDetails, journeyDetails, bookFlights, ticketCancellation, boardingPass;
	public Home() {
		
		setLayout(null);
		
		Image original = new ImageIcon(Home.class.getResource("icons/front.jpg")).getImage();
		JLabel img = new JLabel();
		add(img);
		
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				int width = getContentPane().getWidth();
				int height = getContentPane().getHeight();
				img.setBounds(0, 0, width, height);
				if (width > 0 && height > 0) {
					Image scaled = original.getScaledInstance(width, height, Image.SCALE_SMOOTH);
					img.setIcon(new ImageIcon(scaled));
				}
			}
		});
		
		JLabel heading = new JLabel("AIR INDIA AnJaY");
		heading.setBounds(500, 40, 1000, 40);
		heading.setForeground(Color.BLUE);
		heading.setFont(new Font("Tahoma", Font.PLAIN, 36));
		img.add(heading);
		
		
		JMenuBar menubar = new JMenuBar();
		setJMenuBar(menubar);
		
		JMenu details = new JMenu("Details");
		menubar.add(details);
		
		flightDetails = new JMenuItem("Flight Details");
		details.add(flightDetails);
		flightDetails.addActionListener(this);
		
		customerDetails = new JMenuItem("Add Customer Details");
		details.add(customerDetails);
		customerDetails.addActionListener(this);   // <- add this
		
		bookFlights = new JMenuItem("Book Flights");
		details.add(bookFlights);
		bookFlights.addActionListener(this);
		
		journeyDetails = new JMenuItem("Journey Details");
		details.add(journeyDetails);
		journeyDetails.addActionListener(this);
		
		ticketCancellation = new JMenuItem("Cancel Ticket");
		details.add(ticketCancellation);
		ticketCancellation.addActionListener(this);

		JMenu tickets = new JMenu("tickets");
		menubar.add(tickets);

		boardingPass = new JMenuItem("Boarding Pass");
		tickets.add(boardingPass);
		boardingPass.addActionListener(this);
		
		
		
		
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setVisible(true);

	}
	
    public void actionPerformed(ActionEvent ae) {
        String text = ae.getActionCommand();
        if (text.equals("Add Customer Details")) {
            new AddCustomer();
        } else if (text.equals("Flight Details")) {
            new FlightInfo();
        } else if (text.equals("Book Flight")) {
            new BookFlight();
        } else if (text.equals("Journey Details")) {
            new JourneyDetails();
        } else if (text.equals("Cancel Ticket")) {
            new Cancel();
        }
    }
	
	public static void main(String[] args) {
		new Home();
		
	}
}
