package cl.duoc.ms_products_db.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI apiInfo(){
        return new OpenAPI()
                   .info(new Info().title("API de Prueba DuocUC - FullStack")
                                    .description("Esta es una api de prueba...")
                                    .version("1.0.0"));
    }

}
