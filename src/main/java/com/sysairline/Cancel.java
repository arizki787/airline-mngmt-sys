package com.sysairline;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;

public class Cancel extends JFrame implements ActionListener{
    
    JTextField tfpnr;
    JLabel tfname, cancellationno, lblfcode, lbldateoftravel;
    JButton fetchButton, flight;
    private String loadedPnr;
    
    public Cancel() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        
        Random random = new Random();
        
        JLabel heading = new JLabel("CANCELLATION");
        heading.setBounds(180, 20, 250, 35);
        heading.setFont(new Font("Tahoma", Font.PLAIN, 32));
        add(heading);
        
        ImageIcon i1 = new ImageIcon(Cancel.class.getResource("/com/sysairline/icons/cancel.jpg"));
        Image i2 = i1.getImage().getScaledInstance(250, 250, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(470, 120, 250, 250);
        add(image);
        
        JLabel lblaadhar = new JLabel("PNR Number");
        lblaadhar.setBounds(60, 80, 150, 25);
        lblaadhar.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblaadhar);
        
        tfpnr = new JTextField();
        tfpnr.setBounds(220, 80, 150, 25);
        add(tfpnr);
        
        fetchButton = new JButton("Show Details");
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
        
        JLabel lblnationality = new JLabel("Cancellation No");
        lblnationality.setBounds(60, 180, 150, 25);
        lblnationality.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblnationality);
        
        cancellationno = new JLabel("" + random.nextInt(1000000));
        cancellationno.setBounds(220, 180, 150, 25);
        add(cancellationno);
        
        JLabel lbladdress = new JLabel("Flight Code");
        lbladdress.setBounds(60, 230, 150, 25);
        lbladdress.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lbladdress);
        
        lblfcode = new JLabel();
        lblfcode.setBounds(220, 230, 150, 25);
        add(lblfcode);
        
        JLabel lblgender = new JLabel("Date");
        lblgender.setBounds(60, 280, 150, 25);
        lblgender.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblgender);
        
        lbldateoftravel = new JLabel();
        lbldateoftravel.setBounds(220, 280, 150, 25);
        add(lbldateoftravel);
        
        flight = new JButton("Cancel");
        flight.setBackground(Color.BLACK);
        flight.setForeground(Color.WHITE);
        flight.setBounds(220, 330, 120, 25);
        flight.addActionListener(this);
        add(flight);
        
        setSize(800, 450);
        setLocation(350, 150);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == fetchButton) {
            String pnr = tfpnr.getText().trim();
            loadedPnr = null;
            tfname.setText("");
            lblfcode.setText("");
            lbldateoftravel.setText("");
            
            String query = "SELECT * FROM reservation WHERE PNR = ?";
            try (Connection conn = Conn.getConnection();
                 PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setString(1, pnr);
                try (ResultSet rs = ps.executeQuery()) {
                
                if (rs.next()) {
                    tfname.setText(rs.getString("name")); 
                    lblfcode.setText(rs.getString("flightcode")); 
                    lbldateoftravel.setText(rs.getString("ddate"));
                    loadedPnr = pnr;
                } else {
                    JOptionPane.showMessageDialog(this, "Please enter correct PNR");
                }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Unable to load reservation: " + e.getMessage(),
                        "Database error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (ae.getSource() == flight) {
            String pnr = tfpnr.getText().trim();
            if (loadedPnr == null || !loadedPnr.equals(pnr)) {
                JOptionPane.showMessageDialog(this, "Show a valid reservation before cancelling.",
                        "Missing reservation", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String name = tfname.getText();
            String cancelno = cancellationno.getText();
            String fcode = lblfcode.getText();
            String date = lbldateoftravel.getText();
            
            try (Connection conn = Conn.getConnection()) {
                conn.setAutoCommit(false);
                try {
                    String query = "INSERT INTO cancel "
                            + "(pnr, name, cancellationno, flightcode, ddate) VALUES (?, ?, ?, ?, ?)";
                    try (PreparedStatement ps = conn.prepareStatement(query)) {
                        ps.setString(1, pnr);
                        ps.setString(2, name);
                        ps.setString(3, cancelno);
                        ps.setString(4, fcode);
                        ps.setString(5, date);
                        ps.executeUpdate();
                    }

                    String deleteQuery = "DELETE FROM reservation WHERE PNR = ?";
                    try (PreparedStatement deletePs = conn.prepareStatement(deleteQuery)) {
                        deletePs.setString(1, pnr);
                        if (deletePs.executeUpdate() != 1) {
                            conn.rollback();
                            JOptionPane.showMessageDialog(this, "Reservation was not found; cancellation was not saved.");
                            return;
                        }
                    }
                    conn.commit();
                } catch (SQLException e) {
                    conn.rollback();
                    throw e;
                }
                JOptionPane.showMessageDialog(null, "Ticket Cancelled");
                setVisible(false);
            
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Unable to cancel ticket: " + e.getMessage(),
                        "Database error", JOptionPane.ERROR_MESSAGE);
            }
        } 
    }

    public static void main(String[] args) {
        new Cancel();
    }
}
