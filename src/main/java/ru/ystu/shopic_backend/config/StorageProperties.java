package ru.ystu.shopic_backend.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties("storage")
public class StorageProperties {
	// Папка, куда сохранять загружаемые файлы
	private String location = "src/main/resources/static";
}
