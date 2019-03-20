package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import system.User;
import system.UserAdministration;

public class LoginWindow extends JDialog{
	private static final long serialVersionUID = -8983400305551193302L;
	
	private static final int USIZE = 25;
	private static final int PSIZE = 25;

	private boolean success = false;
	private User currentuser = null;
	
	public LoginWindow(JFrame frame) {
		super(frame, "Login", true);
		
		GridBagConstraints cons = new GridBagConstraints();
		cons.ipadx = cons.ipady = 5;
		
		JPanel panel = new JPanel(new GridBagLayout());
		
		JLabel luname = new JLabel("Username: ");
		cons.gridx = cons.gridy = 0;
		cons.gridwidth = 1;
		panel.add(luname, cons);
		
		JTextField uname = new JTextField(USIZE);
		cons.gridx = 1;
		cons.gridy = 0;
		cons.gridwidth = 2;
		panel.add(uname, cons);
		
		JLabel lpsswd = new JLabel("Password: ");
		cons.gridx = 0;
		cons.gridy = 1;
		cons.gridwidth = 1;
		panel.add(lpsswd, cons);
		
		JPasswordField psswd = new JPasswordField(PSIZE);
		cons.gridx = cons.gridy = 1;
		cons.gridwidth = 2;
		panel.add(psswd, cons);
		
		this.getContentPane().add(panel, BorderLayout.CENTER);
		
		panel = new JPanel(new FlowLayout());
		JButton login = new JButton("Login");
		JButton register = new JButton("Register");
		JButton cancel = new JButton("Cancel");
		
		login.addActionListener((ActionEvent e) -> {
			if(!UserAdministration.login(
					uname.getText().trim(), 
					new String(psswd.getPassword()))) {
				JOptionPane.showMessageDialog(
						this, "Wrong username or password",
						"Error", JOptionPane.ERROR_MESSAGE
						);
				uname.setText("");
				psswd.setText("");
				success = false;
				return;
			}
			success = true;
			currentuser = UserAdministration.getUser(uname.getText().trim());
			this.dispose();
		});
		register.addActionListener((ActionEvent e) -> {
			currentuser = null;
			success = false;
			(new RegisterWindow(frame)).setVisible(true);
		});
		cancel.addActionListener((ActionEvent e) -> {
			currentuser = null;
			success = false;
			this.dispose();
		});
		
		panel.add(login);
		panel.add(register);
		panel.add(cancel);
		
		this.getContentPane().add(panel, BorderLayout.PAGE_END);
		
		this.pack();
		this.setResizable(false);
		this.setLocationRelativeTo(frame);
	}
	
	public boolean test() {
		return this.success;
	}
	
	public User getLoggedUser() {
		return currentuser;
	}
}
