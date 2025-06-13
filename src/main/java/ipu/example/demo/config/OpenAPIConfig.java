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

/**
 * Configuration class for customizing the OpenAPI (Swagger) documentation.
 * <p>
 * Sets metadata, server environments, contact information, and license details.
 */
@Configuration
public class OpenAPIConfig {

  @Value("${ipu.example.demo.dev-url}")
  private String devUrl;

  @Value("${ipu.example.demo.test-url}")
  private String testUrl;

  /**
   * Configures the {@link OpenAPI} bean used by SpringDoc to generate the OpenAPI documentation.
   *
   * @return an instance of {@link OpenAPI} with custom info, contact, license, and server settings
   */
  @Bean
  public OpenAPI myOpenAPI() {
    // Development server configuration
    Server devServer = new Server();
    devServer.setUrl(devUrl);
    devServer.setDescription("Server URL in Development environment");

    // Test server configuration
    Server prodServer = new Server();
    prodServer.setUrl(testUrl);
    prodServer.setDescription("Server URL in Test environment");

    // API contact information
    Contact contact = new Contact();
    contact.setEmail("max.mustermann@gmail.com");
    contact.setName("Max Mustermann");
    contact.setUrl("https://www.MaxMustermann.com");

    // API license information
    License mitLicense = new License()
        .name("The Unlicense")
        .url("https://choosealicense.com/licenses/unlicense/");

    // General API metadata
    Info info = new Info()
        .title("Tutorial Management API")
        .version("1.0")
        .description("This API exposes endpoints to manage tutorials.")
        .contact(contact)
        .license(mitLicense);

    // Return OpenAPI instance with info and servers
    return new OpenAPI()
        .info(info)
        .servers(List.of(devServer, prodServer));
  }
}
