package com.daniel.aula2.Controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daniel.aula2.entities.Product;
import com.daniel.aula2.repositories.ProductRepository;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping()
    public String teste(){
        
        Optional<Product> product = productRepository.findById(1L);
        Product p = product.get();
        return p.getName();

    }
    
}
