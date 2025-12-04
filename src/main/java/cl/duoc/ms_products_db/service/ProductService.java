package cl.duoc.ms_products_db.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.ms_products_db.model.dto.ProductDTO;
import cl.duoc.ms_products_db.model.entities.Product;
import cl.duoc.ms_products_db.model.repositories.ProductRepository;
import jakarta.transaction.Transactional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public ProductDTO findProductById(Long id){
        Optional<Product> product = productRepository.findById(id);       
        ProductDTO productDto = null;

        if(product.isPresent()){
            productDto = translateEntityToDto(product.get());          
        } 
        return productDto;
    }


    public ProductDTO translateEntityToDto(Product product){
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setTitle(product.getTitle());          
        productDTO.setDescription(product.getDescription()); 
        productDTO.setPrice(product.getPrice());          
        productDTO.setImageSrc(product.getImageSrc());    
        productDTO.setCategoria(product.getCategoria()); 
        productDTO.setInfoPage(product.getInfoPage());    
        productDTO.setGallery(product.getGallery());      
        

        return productDTO;
    }


    public List<ProductDTO> selectAllProduct(){
        List<Product> listaProducts = productRepository.findAll();
        return listaProducts.stream()
                            .map(this::translateEntityToDto)
                            .collect(Collectors.toList());
    }


    public Product translateDtoToEntity(ProductDTO productDTO){
        Product product = new Product();
        product.setTitle(productDTO.getTitle());
        product.setDescription(productDTO.getDescription()); 
        product.setPrice(productDTO.getPrice());           
        product.setImageSrc(productDTO.getImageSrc());
        product.setCategoria(productDTO.getCategoria());
        product.setInfoPage(productDTO.getInfoPage());
        product.setGallery(productDTO.getGallery());
        
        return product;
    }

    public ProductDTO createProduct (ProductDTO productDTO){
        Product product = translateDtoToEntity(productDTO);
        Product newProduct = productRepository.save(product);

        ProductDTO newProductDTO = null;
        if(newProduct != null){
            newProductDTO = translateEntityToDto(newProduct);
        }
        return newProductDTO;
    }

    @Transactional
    public boolean deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional 
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Optional<Product> existingProductOptional = productRepository.findById(id);

        if (existingProductOptional.isPresent()) {
            Product existingProduct = existingProductOptional.get();
            

            existingProduct.setTitle(productDTO.getTitle());
            existingProduct.setDescription(productDTO.getDescription()); 
            existingProduct.setPrice(productDTO.getPrice());          
            existingProduct.setImageSrc(productDTO.getImageSrc());
            existingProduct.setCategoria(productDTO.getCategoria());
            existingProduct.setInfoPage(productDTO.getInfoPage());
            existingProduct.setGallery(productDTO.getGallery());

            Product updatedProduct = productRepository.save(existingProduct); 
            return translateEntityToDto(updatedProduct);
        } else {
            return null;
        }
    }
}