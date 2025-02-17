package ru.ystu.shopic_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.ystu.shopic_backend.config.StorageProperties;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableConfigurationProperties(StorageProperties.class)
public class ShopicBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopicBackendApplication.class, args);
	}
}
