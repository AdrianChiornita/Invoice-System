package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import system.User;

public class MenuWindow extends JFrame{
	private static final long serialVersionUID = -8423756206309455473L;
	
	public static final int WIDTH = 400;
	public static final int HEIGHT = 400;
	
	protected static String name = null;
	protected static User user = null;
	
	public MenuWindow() {
		super();
		
		JPanel panel = new JPanel(new FlowLayout());
		JLabel identifier = new JLabel(
					user.getFname() + " " + user.getLname()
					+ " - " + user.getEmail()
				);
		panel.add(identifier);
		
		this.getContentPane().add(panel, BorderLayout.NORTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(WIDTH, HEIGHT);
	    this.setResizable(false);
	}
	
	public static void set(String name, User user) {
		MenuWindow.name = name;
		MenuWindow.user = user;
	}
	
	public void run() {
		
	}
}
