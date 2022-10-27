package com.example.exambeackend.controller;

import com.example.exambeackend.entity.Product;
import com.example.exambeackend.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {

    @Autowired
    ProductRepo productRepo;

    @GetMapping
    public List<Product> getAll() {
        return productRepo.findAll();
    }

    @PostMapping
    public Product addProduct(@RequestBody Product p) {
        return productRepo.save(p);
    }

    @PostMapping("sell")
    public Product sellProduct(@RequestParam(name = "id") long id, @RequestParam(name = "quantity") int quantity) {
        Product product = productRepo.findById(id).orElse(null);
        if (product == null){
            return null;
        }
        int result = product.getQuantity() - quantity;
        if (result < 0){
            return null;
        }
        product.setQuantity(result);
        return productRepo.save(product);
    }
}
