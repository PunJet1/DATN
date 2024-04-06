package com.watch.restController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.watch.entity.ProductSize;
import com.watch.service.ProductSizeService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/productsizes")

public class ProductSizeRestController {

    @Autowired
    ProductSizeService productSizeService;

    @GetMapping("{id}")
    public ProductSize getProductSizeById(@PathVariable Integer id) {
        return productSizeService.getById(id);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable Integer id) {
        ProductSize productSize = productSizeService.getById(id);
        if (productSize != null) {
            productSizeService.delete(productSize);
        }
    }

    @GetMapping("/product/{productId}")
    public List<ProductSize> getProductSizesByProductId(@PathVariable Integer productId) {
        return productSizeService.findById(productId);
    }

    @GetMapping
    public List<ProductSize> getAllProductSizes() {
        return productSizeService.findAll();
    }

    @PostMapping
    public ProductSize create(@RequestBody ProductSize productSize) {
        return productSizeService.save(productSize);
    }

    // @PutMapping("{id}")
    // public ProductSize update(@PathVariable("id") Integer id, @RequestBody ProductSize productSize) {
    //     return productSizeService.save(productSize);
    // }
}
