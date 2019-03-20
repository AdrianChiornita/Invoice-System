package system;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import factory.CompanyType;
import factory.Factory;

public class Administration {
	private static Administration instance = new Administration();
	
	private List<Product> products;
	private List<Company> companies;
	private Map<String, Map<String, Double>> taxes;
	
	private Administration() {
		products = new ArrayList<>();
		companies = new ArrayList<>();
		taxes = new HashMap<>();
	}
	
	public static Administration get() {
		return instance;
	}

	public List<Company> getCompanies() {
		return companies;
	}

	public List<Product> getProducts() {
		return products;
	}
	
	public Map<String, Map<String, Double>> getTaxes() {
		return taxes;
	}
	
	public void loadTaxes(String filename) throws IOException {
		try(BufferedReader buffer = new BufferedReader(new FileReader(filename))) {
			String line = buffer.readLine();
			
			if(line != null) {
				String[] splitter = line.split("\\s");
				
				for(int index = 1; index < splitter.length; ++index)
					taxes.put(splitter[index], new HashMap<>());
				
				while((line = buffer.readLine()) != null) {
					String[] values = line.split("\\s");
					values[0] = values[0].equals("-") ? null : values[0];
					
					for(int index = 1; index < splitter.length; ++index)
						taxes.get(splitter[index]).put(values[0], 
								Double.parseDouble(values[index]));
				}
			}
		}
	}
	
	public void loadProducts(String filename) throws IOException {
		try(BufferedReader buffer = new BufferedReader(new FileReader(filename))) {
			
			String line = buffer.readLine();
			if(line != null) {
				String[] splitter = line.split("\\s");
				
				while((line = buffer.readLine()) != null) {
					String[] values = line.split("\\s");
					values[0] = values[0].equals("-") ? null : values[0];
					values[1] = values[1].equals("-") ? null : values[1];
					
					for(int index = 2; index < splitter.length; ++index) {
						Double price = values[index].equals("-") ?
								0.0 : Double.parseDouble(values[index]);
						products.add(new Product(values[0], values[1], splitter[index], price));
					}
				}
			}
		}
	}
	
	public void loadCompanies(String filename) throws IOException {
		try(BufferedReader buffer = new BufferedReader(new FileReader(filename))) {
			Company company = null;
			String line = null;
			while((line = buffer.readLine()) != null) {
				
				if(line.startsWith("Company")) {
					String[] splitter = line.split(":");
					
					if (company != null) companies.add(company);
					
					company = Factory.create(splitter[2], CompanyType.valueOf(splitter[1]));
				}
				
				if(line.startsWith("Invoice")) {
					Invoice inv = new Invoice(line);
					line = buffer.readLine();
					
					while((line = buffer.readLine()) != null) {
						if(line.equals("")) break;
						
						String[] values = line.split("\\s");
						String name = values[0];
						String country = values[1];
						Integer quantity = Integer.parseInt(values[2]);
						
						for (Product product : Administration.get().getProducts())
							if(product.getName().equals(name) && product.getOrigin().equals(country)) {
								inv.getOrder().add(new OrderedProduct(
										name, 
										product.getCategory(), 
										country,
										product.getPrice(),
										quantity,
										taxes.get(country).get(product.getCategory())
								));
							}
					}
					company.getInvoices().add(inv);
				}
			}
			companies.add(company);
		}
	}
	
	public void load(String ftaxes, String fproducts, String fcompanies) throws IOException {
		this.loadTaxes(ftaxes);
		this.loadProducts(fproducts);
		this.loadCompanies(fcompanies);
	}
	
	public void sort() {
		Collections.sort(this.getCompanies(), (first, second) -> {
				if (first.totalWithTaxes() > second.totalWithTaxes())
					return 1;
				else if (first.totalWithTaxes() < second.totalWithTaxes())
					return -1;
			return 0;
		});
		companies.forEach((company) -> 
			Collections.sort(company.getInvoices(), (first, second) -> {
					if (first.totalWithoutTaxes() > second.totalWithoutTaxes())
						return 1;
					else if (first.totalWithoutTaxes() < second.totalWithoutTaxes())
						return -1;
				return 0;
			})
		);
	}
	
	public void store(String filename) throws IOException {
		sort();
        
        try (PrintWriter writer = new PrintWriter(filename)) {
        	for(CompanyType type : CompanyType.values()) {
        		writer.print(type.toString() + "\r\n");
        		
        		for(Company company : companies) {
        			if(company.getType().equals(type)) {
        				writer.print(company.getName() + "\r\n");
        				writer.print("\r\nTOTAL: " + company.totalWithoutTaxes()
        				+ " " + company.totalWithTaxes() + " " + company.totalWithExempt() + "\r\n");
        				
        				writer.print("COUNTRIES:\n\r");
        				taxes.keySet().forEach(country -> {
        					if(company.totalWithoutTaxes(country) == 0)
        						writer.print(country + " " + 0.0 + "\r\n");
        					else
        						writer.print(country + " " + 
        								company.totalWithoutTaxes(country) + " " + 
        								company.totalWithTaxes(country) + " " + 
        								company.totalWithExempt(country) + "\r\n");
        				});
        				
        				writer.print("\n\r");
        				company.getInvoices().forEach((invoice) -> {
        					writer.print(invoice.getName() + "\r\n");
        					writer.print("\r\nTOTAL: " + invoice.totalWithoutTaxes()
            				+ " " + invoice.totalWithTaxes() + "\r\n");
            				
        					writer.print("COUNTRIES:\n\r");
            				taxes.keySet().forEach(country -> {
            					if(invoice.totalWithoutTaxes(country) == 0)
            						writer.print(country + " " + 0.0 + "\r\n");
            					else
            						writer.print(country + " " + 
            								invoice.totalWithoutTaxes(country) + " " + 
            								invoice.totalWithTaxes(country) +  "\r\n");
            				});
        				});
        				writer.print("\n\r");
        			}
        		}
        	}
        }
	}

	public void update(String filename) throws IOException {
		this.sort();
		
		 try (PrintWriter writer = new PrintWriter(filename)) {
			 
			 List<Product> copy = new ArrayList<>(products);
			 Collections.sort(copy, (first, second) -> {
				 if (first.getName().compareTo(second.getName()) == 0)
						return first.getOrigin().compareTo(second.getOrigin());
					else 
						return first.getName().compareTo(second.getName());
			 });
			 
			 List<String> countries = new ArrayList<>(taxes.keySet());
			 Collections.sort(countries);
			 
			 writer.print("Product Category ");
			 countries.forEach(country -> writer.print(country + " "));
			 writer.print("\r\n");
			 
			 int count = 0, len = countries.size();
			 for(Product product : copy) {
				 if (count == 0)
					 writer.print(product.getName() + " " + product.getCategory() + " ");
				 
				 writer.print(
						 ((product.getPrice() == 0.0) ? "-" : product.getPrice()) + 
						 ((count == len - 1) ? "\r\n" : " ")
						 );
				 
				 count = (count + 1) % len;
			 }
		 }
	}
}
