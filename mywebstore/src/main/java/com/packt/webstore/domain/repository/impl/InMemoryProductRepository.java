package com.packt.webstore.domain.repository.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.packt.webstore.domain.Product;
import com.packt.webstore.domain.repository.ProductRepository;

@Repository
public class InMemoryProductRepository implements ProductRepository{
	
	private List<Product> listOfProducts = new ArrayList<Product>();
	
	
	public void addProduct(Product product) {
		listOfProducts.add(product);
	}
	
	public void removeProduct(Product product) {
		listOfProducts.remove(product);
	}
	
	public InMemoryProductRepository() {
		Product Q;
		//
		//
		Q = new Product("G15001","Designing Apps using Spring MVC Vol.1", new BigDecimal(30));
		Q.setDescription("The 1st volume in a set of 5 books on designing Spring MVC apps");
		Q.setCategory("Guides");
		Q.setManufacturer("LL");
		Q.setUnitsInStock(100);
		listOfProducts.add(Q);
		
		Q = new Product("G15002","Designing Apps using Spring MVC Vol.2", new BigDecimal(30));
		Q.setDescription("The 2nd volume in a set of 5 books on designing Spring MVC apps");
		Q.setCategory("Guides");
		Q.setManufacturer("AC");
		Q.setUnitsInStock(100);
		listOfProducts.add(Q);
		
		Q = new Product("G15003","Designing Apps using Spring MVC Vol.3", new BigDecimal(30));
		Q.setDescription("The 3rd volume in a set of 5 books on designing Spring MVC apps");
		Q.setCategory("Guides");
		Q.setManufacturer("AC");
		Q.setUnitsInStock(100);
		listOfProducts.add(Q);
		
		// <Volume 4 is intentionally missing>
		
		Q = new Product("G15005","Designing Apps using Spring MVC Vol.5", new BigDecimal(30));
		Q.setDescription("The 5th volume in a set of 5 books on designing Spring MVC apps");
		Q.setCategory("Guides");
		Q.setManufacturer("AC");
		Q.setUnitsInStock(100);
		listOfProducts.add(Q);
		
		
		Q = new Product("A12001","The Three Little Pigs (as told by a Coder)", new BigDecimal(70));
		Q.setDescription("Khori Armstrong discusses his tribulations with building an application around programmers with differing opinions.");
		Q.setCategory("Autobiography");
		Q.setManufacturer("DC");
		Q.setUnitsInStock(100);
		listOfProducts.add(Q);
		
		Q = new Product("A12002","The Boy Who Cried Wolf (as told by a Coder)", new BigDecimal(70));
		Q.setDescription("Khori Armstrong discusses his tribulations with debuggers and testers, as well as their assessment of threat levels.");
		Q.setCategory("Autobiography");
		Q.setManufacturer("LL");
		Q.setUnitsInStock(100);
		listOfProducts.add(Q);
		
		Q = new Product("A12003","Little Red Riding Hood (as told by a Coder)", new BigDecimal(70));
		Q.setDescription("Khori Armstrong discusses his tribulations with handling clients and managers and what to do to stay out of trouble.");
		Q.setCategory("Autobiography");
		Q.setManufacturer("DC");
		Q.setUnitsInStock(100);
		listOfProducts.add(Q);
		
		
		Q = new Product("C17001","Best Hardware of 2011", new BigDecimal(12));
		Q.setDescription("Contains all of 2011's latest and hottest hardware. Don't miss these deals!");
		Q.setCategory("Catalogue");
		Q.setManufacturer("DC");
		Q.setUnitsInStock(100);
		listOfProducts.add(Q);
		
		Q = new Product("C17001","Best Hardware of 2012", new BigDecimal(12));
		Q.setDescription("Contains all of 2012's latest and hottest hardware. Don't miss these deals!");
		Q.setCategory("Catalogue");
		Q.setManufacturer("LL");
		Q.setUnitsInStock(100);
		listOfProducts.add(Q);
		
		Q = new Product("C17001","Best Hardware of 2013", new BigDecimal(12));
		Q.setDescription("Contains all of 2013's latest and hottest hardware. Don't miss these deals!");
		Q.setCategory("Catalogue");
		Q.setManufacturer("LL");
		Q.setUnitsInStock(100);
		listOfProducts.add(Q);
		
		Q = new Product("C17001","Best Hardware of 2014", new BigDecimal(12));
		Q.setDescription("Contains all of 2014's latest and hottest hardware. Don't miss these deals!");
		Q.setCategory("Catalogue");
		Q.setManufacturer("AC");
		Q.setUnitsInStock(100);
		listOfProducts.add(Q);
	}

	public List<Product> getAllProducts() {
		return listOfProducts;
	}

	public Product getProductById(String productId) {
		Product productById = null;
		
		for(Product product : listOfProducts) {
			if(product!=null && product.getProductId()!=null && product.getProductId().equals(productId)){
				productById = product;
				break;
			}
		}
		
		if(productById == null){
			throw new IllegalArgumentException("No products found with the product id: "+ productId);
		}
		
		return productById;
	}

	public List<Product> getProductsByCategory(String category) {
		List<Product> productsByCategory = new ArrayList<Product>();
			
		for(Product product: listOfProducts) {
			if(category.equalsIgnoreCase(product.getCategory())){
				productsByCategory.add(product);
			}
		}
		
		return productsByCategory;
	}

	public Set<Product> getProductsByFilter(Map<String, List<String>> filterParams) {
		Set<Product> productsByBrand = new HashSet<Product>();
		Set<Product> productsByCategory = new HashSet<Product>();
		Set<Product> productsByTitle = new HashSet<Product>();

		Set<String> criterias = filterParams.keySet();
		
		if(criterias.contains("brand")) {
			for(String brandName: filterParams.get("brand")) {
				for(Product product: listOfProducts) {
					if(brandName.equalsIgnoreCase(product.getManufacturer())){
						productsByBrand.add(product);
					}
				}
			}
		}
		
		if(criterias.contains("category")) {
			for(String category: filterParams.get("category")) {
				productsByCategory.addAll(this.getProductsByCategory(category));
			}
		}
		
		if(criterias.contains("title")) {
			for(String title: filterParams.get("title")) {
				
				for(Product product: listOfProducts) {
					if(product.getName().contains(title)) {
						productsByBrand.add(product);
					}
				}
			}
		}
		
		
		productsByCategory.addAll(productsByBrand);
		productsByCategory.addAll(productsByTitle);
		
		return productsByCategory;
	}
}
