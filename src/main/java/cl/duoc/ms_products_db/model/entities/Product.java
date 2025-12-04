package cl.duoc.ms_products_db.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Entity
@Table(name = "product")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;        

    @Column(name = "price")
    private Double price;        

    @Column(name = "image_src")
    private String imageSrc;   

    @Column(name = "info_page")
    private String infoPage;     

    @Column(name = "categoria")
    private String categoria;   

    @Column(name = "description", length = 1000) 
    private String description;  


    @ElementCollection
    @CollectionTable(name = "product_gallery", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "image_url")
    private List<String> gallery;  
}