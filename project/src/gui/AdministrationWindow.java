package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import system.Administration;

public class AdministrationWindow extends MenuWindow {
	private static final long serialVersionUID = -6672556060999514265L;
	
	private static final String LOAD = "Load";
	private static final String RELOAD = "Reload";
	private static final String EXCEPTION = "Exception";
	
	private String file_taxes = null;
	private String file_products = null;
	private String file_companies = null;
	private String file_output = null;
	
	public AdministrationWindow() {
		super();
		
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints cons = new GridBagConstraints();
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.ipadx = 20;
		cons.gridy = 10;
		
		JLabel ltaxes = new JLabel("Pick taxes file: ");
		cons.gridx = cons.gridy = 0;
		cons.gridwidth = 2;
		panel.add(ltaxes, cons);
		
		JLabel lproducts = new JLabel("Pick products file: ");
		cons.gridx = 0;
		cons.gridy = 1;
		cons.gridwidth = 2;
		panel.add(lproducts, cons);
		
		JLabel lcompanies = new JLabel("Pick company file: ");
		cons.gridx = 0;
		cons.gridy = 2;
		cons.gridwidth = 2;
		panel.add(lcompanies, cons);
		
		cons.ipady = 0;
		
		JButton taxes = new JButton(LOAD);
		cons.gridx = 2;
		cons.gridy = 0;
		cons.gridwidth = 1;
		panel.add(taxes, cons);
		
		JButton products = new JButton(LOAD);
		cons.gridx = 2;
		cons.gridy = 1;
		cons.gridwidth = 1;
		panel.add(products, cons);
		
		JButton companies = new JButton(LOAD);
		cons.gridx = 2;
		cons.gridy = 2;
		cons.gridwidth = 1;
		panel.add(companies, cons);
		
		JButton rtaxes = new JButton(RELOAD);
		rtaxes.setEnabled(false);
		cons.gridx = 4;
		cons.gridy = 0;
		cons.gridwidth = 1;
		panel.add(rtaxes, cons);
		
		JButton rproducts = new JButton(RELOAD);
		rproducts.setEnabled(false);
		cons.gridx = 4;
		cons.gridy = 1;
		cons.gridwidth = 1;
		panel.add(rproducts, cons);
		
		JButton rcompanies = new JButton(RELOAD);
		rcompanies.setEnabled(false);
		cons.gridx = 4;
		cons.gridy = 2;
		cons.gridwidth = 1;
		panel.add(rcompanies, cons);
		
		this.getContentPane().add(panel, BorderLayout.CENTER);
		
		panel = new JPanel(new FlowLayout());
		JButton proceed = new JButton("Proceed");
		proceed.setEnabled(false);
		panel.add(proceed);
		
		JButton cancel = new JButton("Cancel");
		panel.add(cancel);
		
		JButton exit = new JButton("Exit");
		panel.add(exit);
		
		this.getContentPane().add(panel, BorderLayout.PAGE_END);
		
		taxes.addActionListener((ActionEvent e) -> {
			SearchWindow sw = new SearchWindow("Pick taxes file");
			if(sw.getPath() != null) {
				taxes.setEnabled(false);
				rtaxes.setEnabled(true);
				file_taxes = sw.getPath();
				if(!products.isEnabled() && !companies.isEnabled())
					proceed.setEnabled(true);
			} else {
				JOptionPane.showMessageDialog(
						this, "Taxes file wasn't selected",
						EXCEPTION, JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		products.addActionListener((ActionEvent e) -> {
			SearchWindow sw = new SearchWindow("Pick products file");
			if(sw.getPath() != null) {
				products.setEnabled(false);
				rproducts.setEnabled(true);
				file_products = sw.getPath();
				if(!taxes.isEnabled() && !companies.isEnabled())
					proceed.setEnabled(true);
			} else {
				JOptionPane.showMessageDialog(
						this, "Products file wasn't selected",
						EXCEPTION, JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		companies.addActionListener((ActionEvent e) -> {
			SearchWindow sw = new SearchWindow("Pick companies file");
			if(sw.getPath() != null) {
				companies.setEnabled(false);
				rcompanies.setEnabled(true);
				file_companies = sw.getPath();
				if(!taxes.isEnabled() && !products.isEnabled())
					proceed.setEnabled(true);
			} else {
				JOptionPane.showMessageDialog(
						this, "Companies file wasn't selected",
						EXCEPTION, JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		rtaxes.addActionListener((ActionEvent e) -> {
			taxes.setEnabled(true);
			rtaxes.setEnabled(false);
			file_taxes = null;
		});
		
		rproducts.addActionListener((ActionEvent e) -> {
			products.setEnabled(true);
			rproducts.setEnabled(false);
			file_products = null;
		});
		
		rcompanies.addActionListener((ActionEvent e) -> {
			rcompanies.setEnabled(true);
			companies.setEnabled(false);
			file_companies = null;
		});
		
		proceed.addActionListener((ActionEvent e) -> {
			SearchWindow sw = new SearchWindow("Pick output file");
			if(sw.getPath() != null) {
				try {
					Administration.get().load(file_taxes, file_products, file_companies);
					Administration.get().store(file_output);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(
							this, "Exception in producing output",
							EXCEPTION, JOptionPane.ERROR_MESSAGE);
					
					file_taxes = file_products = file_companies = null;
					file_output = null;
					
					rtaxes.setEnabled(false);
					rproducts.setEnabled(false);
					rcompanies.setEnabled(false);
					
					taxes.setEnabled(true);
					products.setEnabled(true);
					companies.setEnabled(true);
					
					proceed.setEnabled(false);
				}
				
			}
		});
		
		cancel.addActionListener((ActionEvent e) -> {
			(new MenuWindow()).run();
			this.dispose();
		});
		
		exit.addActionListener((ActionEvent e) -> this.dispose());
	}
}