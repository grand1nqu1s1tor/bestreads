package io.javabrains.bestreadsdataloader;

import java.nio.file.Path;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

import io.javabrains.bestreadsdataloader.author.Author;
import io.javabrains.bestreadsdataloader.author.AuthorRepository;
import io.javabrains.bestreadsdataloader.connection.DataStaxAstraProperties;

@SpringBootApplication
@EnableConfigurationProperties(DataStaxAstraProperties.class)
public class BestreadsApplication {
	
	@Autowired AuthorRepository authorRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(BestreadsApplication.class, args);
	}
	
	@PostConstruct
	public void start() {
		Author author = new Author();
		author.setId("id");
		author.setName("name");
		author.setPersonalName("pName");
		authorRepository.save(author);
		System.out.println("Application started");
	}
	
	@Bean
    public CqlSessionBuilderCustomizer sessionBuilderCustomizer(DataStaxAstraProperties astraProperties) {
        Path bundle = astraProperties.getSecureConnectBundle().toPath();
        return builder -> builder.withCloudSecureConnectBundle(bundle);
	}
}
