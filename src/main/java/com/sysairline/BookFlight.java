package com.sysairline;

import java.awt.Choice; 
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;
import java.text.SimpleDateFormat;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

public class BookFlight extends JFrame implements ActionListener {

    
    JTextField tfaadhar;
    JLabel tfname, tfnationality, tfaddress, labelgender, labelfname, labelfcode;
    JButton bookflight, fetchButton, flight;
    Choice source, destination;
    JDateChooser dcdate;
    private String loadedAadhar;
    private String loadedSource;
    private String loadedDestination;
    
    public BookFlight() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        
        JLabel heading = new JLabel("Book Flight");
        heading.setBounds(420, 20, 500, 35);
        heading.setFont(new Font("Tahoma", Font.PLAIN, 32));
        heading.setForeground(Color.BLUE);
        add(heading);
        
        JLabel lblaadhar = new JLabel("Aadhar");
        lblaadhar.setBounds(60, 80, 150, 25);
        lblaadhar.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblaadhar);
        
        tfaadhar = new JTextField();
        tfaadhar.setBounds(220, 80, 150, 25);
        add(tfaadhar);
        
        fetchButton = new JButton("Fetch User");
        fetchButton.setBackground(Color.BLACK);
        fetchButton.setForeground(Color.WHITE);
        fetchButton.setBounds(380, 80, 120, 25);
        fetchButton.addActionListener(this);
        add(fetchButton);
        
        JLabel lblname = new JLabel("Name");
        lblname.setBounds(60, 130, 150, 25);
        lblname.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblname);
        
        tfname = new JLabel();
        tfname.setBounds(220, 130, 150, 25);
        add(tfname);
        
        JLabel lblnationality = new JLabel("Nationality");
        lblnationality.setBounds(60, 180, 150, 25);
        lblnationality.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblnationality);
        
        tfnationality = new JLabel();
        tfnationality.setBounds(220, 180, 150, 25);
        add(tfnationality);
        
        JLabel lbladdress = new JLabel("Address");
        lbladdress.setBounds(60, 230, 150, 25);
        lbladdress.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lbladdress);
        
        tfaddress = new JLabel();
        tfaddress.setBounds(220, 230, 150, 25);
        add(tfaddress);
        
        JLabel lblgender = new JLabel("Gender");
        lblgender.setBounds(60, 280, 150, 25);
        lblgender.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblgender);
        
        labelgender = new JLabel("Gender");
        labelgender.setBounds(220, 280, 150, 25);
        add(labelgender);
        
        JLabel lblsource = new JLabel("Source");
        lblsource.setBounds(60, 330, 150, 25);
        lblsource.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblsource);
        
        source = new Choice();
        source.setBounds(220, 330, 150, 25);       
        add(source);
        
        JLabel lbldest = new JLabel("Destination");
        lbldest.setBounds(60, 380, 150, 25);
        lbldest.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lbldest);
        
        destination = new Choice();
        destination.setBounds(220, 380, 150, 25);       
        add(destination);
        String sql = "select * from flight";
        try (Connection c = Conn.getConnection();
             PreparedStatement stmt = c.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery();) {
            
            while(rs.next()) {
                source.add(rs.getString("source"));
                destination.add(rs.getString("destination"));
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Unable to load flights: " + e.getMessage(),
                    "Database error", JOptionPane.ERROR_MESSAGE);
        }
        
        flight = new JButton("Fetch Flights");
        flight.setBackground(Color.BLACK);
        flight.setForeground(Color.WHITE);
        flight.setBounds(380, 380, 120, 25);
        flight.addActionListener(this);
        add(flight);
        
        JLabel lblfname = new JLabel("Flight Name");
        lblfname.setBounds(60, 430, 150, 25);
        lblfname.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblfname);
        
        labelfname = new JLabel();
        labelfname.setBounds(220, 430, 150, 25);
        add(labelfname);
        
        JLabel lblfcode = new JLabel("Flight Code");
        lblfcode.setBounds(60, 480, 150, 25);
        lblfcode.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblfcode);
        
        labelfcode = new JLabel();
        labelfcode.setBounds(220, 480, 150, 25);
        add(labelfcode);
        
        JLabel lbldate = new JLabel("Date of Travel");
        lbldate.setBounds(60, 530, 150, 25);
        lbldate.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lbldate);
        
        dcdate = new JDateChooser();
        dcdate.setBounds(220, 530, 150, 25);
        add(dcdate);
        
        ImageIcon i1 = new ImageIcon(BookFlight.class.getResource("/com/sysairline/icons/details.jpg"));
        Image i2 = i1.getImage().getScaledInstance(450, 320, Image.SCALE_DEFAULT);
        ImageIcon image = new ImageIcon(i2);
        JLabel lblimage = new JLabel(image);
        lblimage.setBounds(550, 80, 500, 410);
        add(lblimage);
        
        bookflight = new JButton("Book Flight");
        bookflight.setBackground(Color.BLACK);
        bookflight.setForeground(Color.WHITE);
        bookflight.setBounds(220, 580, 150, 25);
        bookflight.addActionListener(this);
        add(bookflight);
        
        setSize(1100, 700);
        setLocation(200, 50);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == fetchButton) {
            String aadhar = tfaadhar.getText().trim();
            loadedAadhar = null;
            tfname.setText("");
            tfnationality.setText("");
            tfaddress.setText("");
            labelgender.setText("");
            
            try {
                String sql = "SELECT * FROM passenger WHERE aadhar = ?";
                try (Connection c = Conn.getConnection();
                     PreparedStatement stmt = c.prepareStatement(sql)) {
                    stmt.setString(1, aadhar);
                    try (ResultSet rs = stmt.executeQuery()) {
                
                        if (rs.next()) {
                            tfname.setText(rs.getString("name"));
                            tfnationality.setText(rs.getString("nationality"));
                            tfaddress.setText(rs.getString("address"));
                            labelgender.setText(rs.getString("gender"));
                            loadedAadhar = aadhar;
                        } else {
                            JOptionPane.showMessageDialog(this, "Please enter correct ID number");
                        }
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Unable to load passenger details: " + e.getMessage(),
                        "Database error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (ae.getSource() == flight) {
            String src = source.getSelectedItem();
            String dest = destination.getSelectedItem();
            loadedSource = null;
            loadedDestination = null;
            labelfname.setText("");
            labelfcode.setText("");

            try {
                String query = "SELECT * FROM flight WHERE source = ? AND destination = ?";
                try (Connection conn = Conn.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setString(1, src);
                    stmt.setString(2, dest);
                    try (ResultSet rs = stmt.executeQuery()) {
                
                        if (rs.next()) {
                            labelfname.setText(rs.getString("f_name"));
                            labelfcode.setText(rs.getString("f_code"));
                            loadedSource = src;
                            loadedDestination = dest;
                        } else {
                            JOptionPane.showMessageDialog(this, "No Flights Found");
                        }
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Unable to search flights: " + e.getMessage(),
                        "Database error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            String aadhar = tfaadhar.getText().trim();
            String src = source.getSelectedItem();
            String des = destination.getSelectedItem();
            if (loadedAadhar == null || !loadedAadhar.equals(aadhar)) {
                JOptionPane.showMessageDialog(this, "Fetch a valid passenger before booking.",
                        "Missing passenger", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (loadedSource == null || !loadedSource.equals(src) || !loadedDestination.equals(des)) {
                JOptionPane.showMessageDialog(this, "Fetch a flight for the selected route before booking.",
                        "Missing flight", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (dcdate.getDate() == null) {
                JOptionPane.showMessageDialog(this, "Select a travel date before booking.",
                        "Missing date", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Random random = new Random();
            
            String name = tfname.getText(); 
            String nationality = tfnationality.getText();
            String flightname = labelfname.getText(); 
            String flightcode = labelfcode.getText();
            String ddate = new SimpleDateFormat("yyyy-MM-dd").format(dcdate.getDate());
            
            try {
                String sql = "INSERT INTO reservation "
                        + "(PNR, ticket, aadhar, name, nationality, flightname, flightcode, source, destination, ddate) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                try (Connection c = Conn.getConnection();
                     PreparedStatement stmt = c.prepareStatement(sql)) {
                    stmt.setString(1, "PNR-" + random.nextInt(1000000));
                    stmt.setString(2, "TIC-" + random.nextInt(10000));
                    stmt.setString(3, aadhar);
                    stmt.setString(4, name);
                    stmt.setString(5, nationality);
                    stmt.setString(6, flightname);
                    stmt.setString(7, flightcode);
                    stmt.setString(8, src);
                    stmt.setString(9, des);
                    stmt.setString(10, ddate);
                    stmt.executeUpdate();
                }
                
                JOptionPane.showMessageDialog(null, "Ticket Booked Successfully");

                setVisible(false);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Unable to book flight: " + e.getMessage(),
                        "Database error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        new BookFlight();
    }

}
