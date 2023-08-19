package hdang09;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "F-Code Shorten URL API",
                version = "1.0.0",
                description = "An API to transform long, ugly links into nice, short URLs for F-Code club."
        )
)
public class ShortenUrlApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShortenUrlApplication.class, args);
    }

}
