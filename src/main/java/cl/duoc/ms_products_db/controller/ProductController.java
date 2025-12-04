package cl.duoc.ms_products_db.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.ms_products_db.model.dto.ProductDTO;
import cl.duoc.ms_products_db.service.ProductService;



@RestController
@RequestMapping ("/api/product")
public class ProductController {

        @Autowired
        ProductService productService;

        @GetMapping("/{id}")
        public ResponseEntity<ProductDTO> findProductById(@PathVariable (name = "id") Long id) {
            ProductDTO productDTO = productService.findProductById(id);
            return (productDTO != null)? new ResponseEntity<>(productDTO, HttpStatus.OK) :
                                         new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } 
        @GetMapping("")
        public List<ProductDTO> selectAllProduct() {
            return productService.selectAllProduct();
        }    
        @PostMapping
        public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
            ProductDTO createdProduct = productService.createProduct(productDTO);
            return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }    

        @DeleteMapping("/{id}")
        public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
             boolean deleted = productService.deleteProduct(id);
                 if (deleted) {
                     return new ResponseEntity<>("Producto Eliminado", HttpStatus.OK);
                    } else {
                        return new ResponseEntity<>("Producto no encontrado", HttpStatus.NOT_FOUND);
        }
    }
        @PutMapping("/{id}")
        public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
             ProductDTO updatedProduct = productService.updateProduct(id, productDTO);
             if (updatedProduct != null) {
                 return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
             } else {
                 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
