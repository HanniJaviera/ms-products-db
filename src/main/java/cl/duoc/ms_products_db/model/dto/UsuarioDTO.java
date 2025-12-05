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
public class UsuarioDTO {

    @JsonProperty(value = "usuario_id")
    private Long idUsuario;

    @JsonProperty(value = "usuario_nombre")
    private String nombreUsuario;

    @JsonProperty(value = "usuario_appaterno")
    private String apellidoPaterno;

    @JsonProperty(value = "usuario_apmaterno")
    private String apellidoMaterno;

    @JsonProperty(value = "usuario_correo")
    private String correo;

    @JsonProperty(value = "usuario_celular")
    private String celular;

    @JsonProperty(value = "usuario_direccion")
    private String direccion;

    @JsonProperty(value = "usuario_password")
    private String password;
}