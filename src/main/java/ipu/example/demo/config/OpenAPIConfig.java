package ipu.example.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

  @Value("${ipu.example.demo.dev-url}")
  private String devUrl;

  @Value("${ipu.example.demo.test-url}")
  private String testUrl;

  @Bean
  public OpenAPI myOpenAPI() {
    Server devServer = new Server();
    devServer.setUrl(devUrl);
    devServer.setDescription("Server URL in Development environment");

    Server prodServer = new Server();
    prodServer.setUrl(testUrl);
    prodServer.setDescription("Server URL in Test environment");

    Contact contact = new Contact();
    contact.setEmail("max.mustermann@gmail.com");
    contact.setName("Max Mustermann");
    contact.setUrl("https://www.MaxMustermann.com");

    License mitLicense = new License().name("The Unlicense").url("https://choosealicense.com/licenses/unlicense/");

    Info info = new Info()
        .title("Tutorial Management API")
        .version("1.0")
        .contact(contact)
        .description("This API exposes endpoints to manage tutorials.")
        .license(mitLicense);

    return new OpenAPI().info(info).servers(List.of(devServer, prodServer));
  }
}
