package cl.duoc.ms_products_db.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString; 

@Entity
@Table(name = "venta")
@Getter
@Setter
@ToString
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "numero_venta")
    private Long numeroVenta; 

    @Column(name = "nombre_usuario")
    private String nombreUsuario;

    @Column(name = "correo_usuario")
    private String correoUsuario;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "total")
    private Integer total;
}
