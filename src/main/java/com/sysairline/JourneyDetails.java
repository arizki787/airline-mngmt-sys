package com.sysairline;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;

public class JourneyDetails extends JFrame implements ActionListener{
    JTable table;
    JTextField pnr;
    JButton show;
    
    public JourneyDetails() {
        
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        
        JLabel lblpnr = new JLabel("PNR Details");
        lblpnr.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblpnr.setBounds(50, 50, 100, 25);
        add(lblpnr);
        
        pnr = new JTextField();
        pnr.setBounds(160, 50, 120, 25);
        add(pnr);
        
        show = new JButton("Show Details");
        show.setBackground(Color.BLACK);
        show.setForeground(Color.WHITE);
        show.setBounds(290, 50, 120, 25);
        show.addActionListener(this);
        add(show);
        
        table = new JTable();
        
        JScrollPane jsp = new JScrollPane(table);
        jsp.setBounds(0, 100, 800, 150);
        jsp.setBackground(Color.WHITE);
        add(jsp);
        
        setSize(800, 600);
        setLocation(400, 150);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae) {
	    String sql = "SELECT * FROM reservation WHERE PNR = ?";
        try (Connection c = Conn.getConnection();
             PreparedStatement stmt = c.prepareStatement(sql)) {
            stmt.setString(1, pnr.getText().trim());
            try (ResultSet rs = stmt.executeQuery()) {
            
                if (!rs.isBeforeFirst()) {
                    JOptionPane.showMessageDialog(this, "No Information Found");
                    return;
                }
                table.setModel(DatabaseUtils.buildTableModel(rs));
            }
        } catch(Exception e) {
            JOptionPane.showMessageDialog(this, "Unable to load journey details: " + e.getMessage(),
                    "Database error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new JourneyDetails();
    }
}
