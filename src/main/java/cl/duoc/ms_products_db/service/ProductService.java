package cl.duoc.ms_products_db.service;

import java.util.List;
import java.util.Optional;

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
        productDTO.setNombreProduct(product.getNombreProduct());
        productDTO.setDescripcion(product.getDescripcion());
        productDTO.setCantidad(product.getCantidad());
        productDTO.setPrecio(product.getPrecio());
        return productDTO;
    }

    public List<Product> selectAllProduct(){
        List<Product> listaProducts = productRepository.findAll();
        return listaProducts;
    }

        // Convierte ProductDTO a entidad Product
    public Product translateDtoToEntity(ProductDTO productDTO){
        Product product = new Product();
        // El ID generalmente es generado por la DB, por lo que no lo configuramos para la creación
        product.setNombreProduct(productDTO.getNombreProduct());
        product.setDescripcion(productDTO.getDescripcion());
        product.setCantidad(productDTO.getCantidad());
        product.setPrecio(productDTO.getPrecio());
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

    @Transactional //Es fundamental para operaciones de escritura en la base de datos. Asegura que la eliminación se realice de forma segura y atómica.
    public boolean deleteProduct(Long id) {
        // Primero, verifica si el producto existe
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true; // Indica que se eliminó correctamente
        }
        return false; // Indica que el producto no fue encontrado
    }

        @Transactional 
        public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Optional<Product> existingProductOptional = productRepository.findById(id);

        if (existingProductOptional.isPresent()) {
            Product existingProduct = existingProductOptional.get();
            existingProduct.setNombreProduct(productDTO.getNombreProduct());
            existingProduct.setDescripcion(productDTO.getDescripcion());
            existingProduct.setCantidad(productDTO.getCantidad());
            existingProduct.setPrecio(productDTO.getPrecio());
            Product updatedProduct = productRepository.save(existingProduct); 
            return translateEntityToDto(updatedProduct);
        } else {
            // Si el producto no fue encontrado, retorna null
            return null;
        }
    }
}
