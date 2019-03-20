package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import system.UserAdministration;

public class StartWindow {
	private static final int WIDTH = 300;
	private static final int HEIGHT = 200;
	
	private JFrame frame;
	
	public StartWindow (String name) {
	    frame = new JFrame(name);
	    
	    JPanel panel = new JPanel(new GridBagLayout());      
	    JLabel msg = new JLabel("Welcome to the Invoice System");
	    
	    panel.add(msg);
	    frame.getContentPane().add(panel, BorderLayout.CENTER);
	
	    panel = new JPanel(new FlowLayout());
	    JButton login = new JButton("Login");
	    JButton exit = new JButton("Exit");
	    
	    login.addActionListener((ActionEvent e) -> {
	    	try {
				UserAdministration.load();
				
				LoginWindow log = new LoginWindow(frame);
		    	log.setVisible(true);
		    	
		    	if (log.test()) {
		    		MenuWindow.set(name, log.getLoggedUser());
		    		
		    		MenuWindow menu = new MenuWindow();
		    		menu.setLocation(
		    				frame.getLocation().x - MenuWindow.WIDTH/2,
		    				frame.getLocation().y - MenuWindow.HEIGHT/2
		    				);
		    		menu.setVisible(true);
		    		frame.dispose();
		    	}
		    	
			} catch (IOException ex) {
				JOptionPane.showMessageDialog(
						frame, "Failed loading users!",
						"Error", JOptionPane.ERROR_MESSAGE);
				frame.dispose();
			}
	    });
	    exit.addActionListener((ActionEvent e) -> frame.dispose());
	    
	    panel.add(login);
       	panel.add(exit);
        frame.getContentPane().add(panel, BorderLayout.PAGE_END);
        
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        
        frame.setLocation(
        		(dim.width - frame.getSize().width) / 2, 
                (dim.height - frame.getSize().height) / 2
                );
        frame.setResizable(false);  
    }
     
    public void setVisible (boolean visible) {
        frame.setVisible(visible);
    }
}
