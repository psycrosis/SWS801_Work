package com.packt.webstore.service;

import com.packt.webstore.domain.Product;

public interface OrderService {
	
	void addProduct(Product product);
	
	void processOrder(String  productId, long quantity);
}
