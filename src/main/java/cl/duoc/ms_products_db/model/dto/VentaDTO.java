package cl.duoc.ms_products_db.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class VentaDTO {

    @JsonProperty(value = "nombreUsuario")
    private String nombreUsuario;

    @JsonProperty(value = "correoUsuario")
    private String correoUsuario;

    @JsonProperty(value = "direccion")
    private String direccion;


    @JsonProperty(value = "total")
    private Integer total;

}