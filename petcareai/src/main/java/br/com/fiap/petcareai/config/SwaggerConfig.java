package br.com.fiap.petcareai.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("PetCare AI+ API")
                        .description("""
                                API inteligente para gerenciamento contínuo da saúde de pets.
                                
                                Funcionalidades:
                                - Cadastro de tutores e pets
                                - Controle de vacinas, consultas e medicamentos
                                - Monitoramento IoT (simulado) com alertas automáticos
                                - Motor de recomendações com IA baseada em regras
                                """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Raphael Gomes Mancera")
                                .email("manceragomesbr@gmail.com")
                                .url("https://github.com/RaphaelGomesMancera/PetCare-AI-Java"))
                        .license(new License().name("MIT License")))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Servidor local")
                ));
    }
}

