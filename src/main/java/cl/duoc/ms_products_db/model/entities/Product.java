package cl.duoc.ms_products_db.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name= "product")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idproduct")
    private Long id;

    @Column(name="nombreproduct")
    private String nombreProduct;

    @Column(name="descripcion")
    private String descripcion;

    @Column(name="cantidad")
    private Long cantidad;

    @Column(name="precio")
    private Long precio;

}
