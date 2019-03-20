package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import system.UserAdministration;

public class RegisterWindow extends JDialog {
	private static final long serialVersionUID = 5353026831981351422L;
	
	private static final int BOXSIZE = 25;
	private static final String NAME = "Register";
	
	public RegisterWindow(JFrame frame) {
		super(frame, NAME, true);
		
		GridBagConstraints cons = new GridBagConstraints();
		cons.ipadx = cons.ipady = 5;
		cons.fill = GridBagConstraints.HORIZONTAL;
		
		JPanel panel = new JPanel(new GridBagLayout());
		
		JLabel llname = new JLabel("Last Name: ");
		cons.gridx = cons.gridy = 0;
		cons.gridwidth = 1;
		panel.add(llname, cons);
		
		JTextField lname = new JTextField(BOXSIZE);
		cons.gridx = 1;
		cons.gridy = 0;
		cons.gridwidth = 2;
		panel.add(lname, cons);
		
		JLabel lfname = new JLabel("First Name: ");
		cons.gridx = 0;
		cons.gridy = 1;
		cons.gridwidth = 1;
		panel.add(lfname, cons);
		
		JTextField fname = new JTextField(BOXSIZE);
		cons.gridx = cons.gridy = 1;
		cons.gridwidth = 2;
		panel.add(fname, cons);
		
		JLabel lemail = new JLabel("Email: ");
		cons.gridx = 0;
		cons.gridy = 2;
		cons.gridwidth = 1;
		panel.add(lemail, cons);
		
		JTextField email = new JTextField(BOXSIZE);
		cons.gridx = 1;
		cons.gridy = 2;
		cons.gridwidth = 2;
		panel.add(email, cons);
		
		JLabel luname = new JLabel("Username: ");
		cons.gridx = 0;
		cons.gridy = 3;
		cons.gridwidth = 1;
		panel.add(luname, cons);
		
		JTextField uname = new JTextField(BOXSIZE);
		cons.gridx = 1;
		cons.gridy = 3;
		cons.gridwidth = 2;
		panel.add(uname, cons);
		
		JLabel lpsswd = new JLabel("Password: ");
		cons.gridx = 0;
		cons.gridy = 4;
		cons.gridwidth = 1;
		panel.add(lpsswd, cons);
		
		JPasswordField psswd = new JPasswordField(BOXSIZE);
		cons.gridx = 1;
		cons.gridy = 4;
		cons.gridwidth = 2;
		panel.add(psswd, cons);
		
		JLabel lcpsswd = new JLabel("Confirm Password: ");
		cons.gridx = 0;
		cons.gridy = 5;
		cons.gridwidth = 1;
		panel.add(lcpsswd, cons);
		
		JPasswordField cpsswd = new JPasswordField(BOXSIZE);
		cons.gridx = 1;
		cons.gridy = 5;
		cons.gridwidth = 2;
		panel.add(cpsswd, cons);
		
		this.getContentPane().add(panel, BorderLayout.CENTER);
		
		panel = new JPanel(new FlowLayout());
		JButton register = new JButton(NAME);
		JButton cancel = new JButton("Cancel");
		
		register.addActionListener((ActionEvent e) -> {
			if(!(new String(psswd.getPassword())).equals(
					(new String(cpsswd.getPassword())))) {
				JOptionPane.showMessageDialog(
						this, "Passwords don't match!", 
						"Error", JOptionPane.ERROR_MESSAGE);
				psswd.setText("");
				cpsswd.setText("");
				return;
			}
			if(!UserAdministration.register(
					lname.getText().trim(), 
					fname.getText().trim(), 
					email.getText().trim(), 
					uname.getText().trim(),
					(new String(psswd.getPassword())))) {
				JOptionPane.showMessageDialog(
						this, "Wrong Data introduced!", 
						"Error", JOptionPane.ERROR_MESSAGE);
				lname.setText(""); 
				fname.setText(""); 
				email.setText(""); 
				uname.setText("");
				psswd.setText("");
				cpsswd.setText("");
				return;
			}
			
			try {
				UserAdministration.store();
			} catch (IOException ex) {
				JOptionPane.showMessageDialog(
						this, "Can't store user data!", 
						"Error", JOptionPane.ERROR_MESSAGE);
			}
			
			JOptionPane.showMessageDialog(
					this, "Welcome" + fname.getText().trim() 
					+ "!\n You've been successfuly registered.", 
					NAME, JOptionPane.INFORMATION_MESSAGE);
			this.dispose();
		});
		
		cancel.addActionListener((ActionEvent e) -> this.dispose());
		
		panel.add(register);
		panel.add(cancel);
		
		this.getContentPane().add(panel, BorderLayout.PAGE_END);
		
		this.pack();
		this.setResizable(false);
		this.setLocationRelativeTo(frame);
	}
}
