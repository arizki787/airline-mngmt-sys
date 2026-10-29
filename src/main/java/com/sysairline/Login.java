package com.sysairline;

import java.awt.Color;
import java.awt.event.*;
import java.sql.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends JFrame implements ActionListener{
	
	JButton submit, close;
	JTextField tfusername;
	JPasswordField tfpassword;
	
	public Login() {
		getContentPane().setBackground(Color.WHITE); //need to learn about content pane
		setLayout(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		
		JLabel lblusername= new JLabel("Username");
		lblusername.setBounds(20, 20, 100, 20);
		add(lblusername);
		
		tfusername = new JTextField();
		tfusername.setBounds(130, 20, 200, 20);
		add(tfusername);
		

		JLabel lblpassword= new JLabel("Password");
		lblpassword.setBounds(20, 40, 100, 20);
		add(lblpassword);
		
		tfpassword = new JPasswordField();
		tfpassword.setBounds(130, 40, 200, 20);
		add(tfpassword);
		
		submit = new JButton("Submit");
		submit.setBounds(40, 120, 120, 20);
		submit.addActionListener(this);
		add(submit);

		close = new JButton("Close");
		close.setBounds(190, 120, 120,20);
		close.addActionListener(this);
		add(close);
		
		setSize(400, 250);
		setLocationRelativeTo(null);
		setVisible(true);

	}
	
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == submit) {
			String username = tfusername.getText();
			String password = new String(tfpassword.getPassword());
			if (username.isBlank() || password.isBlank()) {
				JOptionPane.showMessageDialog(this, "Enter both username and password.",
						"Missing information", JOptionPane.WARNING_MESSAGE);
				return;
			}
			String sql = "SELECT * FROM login WHERE username = ? and password = ?";
			
			try (Connection c = Conn.getConnection();
				 PreparedStatement stmt = c.prepareStatement(sql)) {
			
				stmt.setString(1, username);
				stmt.setString(2, password);
				try (ResultSet rs = stmt.executeQuery()) {
					if (rs.next()) {
						new Home();
						dispose();
					} else {
						JOptionPane.showMessageDialog(this, "Invalid Username or Password");
					}
				}
				
				
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "Unable to authenticate: " + e.getMessage(),
						"Database error", JOptionPane.ERROR_MESSAGE);
			}
		} else if (ae.getSource() == close) {
			dispose();
		}
	}
	
	public static void main(String[] args) {
		new Login();
		
	}
}
