package cl.duoc.ms_products_db.model.dto;

import java.util.List; // <--- Importante para la galería

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProductDTO {

    private Long id;

    private String title;       

    private String description;  

    private Double price;       

    private String imageSrc;    

    private String categoria;    

    private String infoPage;     

    private List<String> gallery; 
}
