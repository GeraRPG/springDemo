package com.sayuri.demo.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sayuri.demo.dao.ProductsDao;
import com.sayuri.demo.entity.Product;

@RestController
@RequestMapping("Product")
public class productController {
	
	@Autowired
	private ProductsDao productDao;
	
	@GetMapping
	public ResponseEntity<List<Product>> getProduct(){
		List<Product> products = productDao.findAll();
		return ResponseEntity.ok(products);
	}
	
	@GetMapping(value="{productId}")
	public ResponseEntity<Product> getProductById(@PathVariable("productId") Long productId){
		Optional<Product> optionalProduct = productDao.findById(productId);
		if(optionalProduct.isPresent()) {
			return ResponseEntity.ok(optionalProduct.get());
		}else {			
			return ResponseEntity.noContent().build();
		}
			
	}
	@PostMapping
	public ResponseEntity<Product> createProduct(@RequestBody Product product){
		Product newProduct = productDao.save(product);
		return ResponseEntity.ok(newProduct);
	}
	@PutMapping
	public ResponseEntity<Product> updateProduct(@RequestBody Product product){
		Optional<Product> optionalProduct = productDao.findById(product.getId());
		if(optionalProduct.isPresent()) {
			Product updateProduct = optionalProduct.get();
			updateProduct.setName(product.getName());
			updateProduct.setDescription(product.getDescription());
			productDao.save(updateProduct);
			return ResponseEntity.ok(updateProduct);
		}else {			
			return ResponseEntity.notFound().build();
		}
	}
	@DeleteMapping(value="{productId}")
	public ResponseEntity<Void> deleteProduct(@PathVariable("productId") Long productId){
		productDao.deleteById(productId);
		return ResponseEntity.ok(null);
	}

}
