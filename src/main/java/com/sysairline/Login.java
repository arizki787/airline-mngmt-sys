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
			System.out.println("Submit is pressed");
			String username = tfusername.getText();
			String password = tfpassword.getText();
			String sql = "SELECT * FROM login WHERE username = ? and password = ?";
			
			try (Connection c = Conn.getConnection();
				 PreparedStatement stmt = c.prepareStatement(sql)) {
			
				stmt.setString(1, username);
				stmt.setString(2, password);
				
				ResultSet rs = stmt.executeQuery();
				
				if (rs.next()) {
					new Home();
					setVisible(false);
				} else {
					JOptionPane.showMessageDialog(null, "Invalid Username or Password");
					setVisible(false);
				}
				
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (ae.getSource() == close) {
			setVisible(false);
		}
	}
	
	public static void main(String[] args) {
		new Login();
		
	}
}
