package com.epam.vm.techtalk.dbinitialization.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.epam.vm.techtalk.dbinitialization.repositories.InitializationStrategyRepository;

@SpringBootApplication
@ComponentScan("com.epam.vm.techtalk.dbinitialization")
@EntityScan("com.epam.vm.techtalk.dbinitialization.domain")
@EnableJpaRepositories(basePackageClasses=InitializationStrategyRepository.class)
public class DemoApplication {
	
	private static final Logger log = LoggerFactory.getLogger(DemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class);
	}
	
	@Bean
	public CommandLineRunner demo(InitializationStrategyRepository repository) {
		return (args) -> {
			
			//repository.save(new InitializationStrategy("1", 2));
			
			// show all strategies
			log.info("Database initialization Strategies rating list:");
			log.info("-----------------------------------------------");
			repository.findAll().forEach(p -> log.info(p.toString()));
		};
	}
}
