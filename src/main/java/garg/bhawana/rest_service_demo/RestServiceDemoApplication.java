package garg.bhawana.rest_service_demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RestServiceDemoApplication {
	private static final Logger logger = LoggerFactory.getLogger(RestServiceDemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(RestServiceDemoApplication.class, args);
	}

	@Bean
	CommandLineRunner initTaskRepository(TaskRepository repo) {
		return _ -> {
			logger.info("Preloading task "+repo.save(new Task("task 1")));
			logger.info("Preloading task "+repo.save(new Task("task 2")));
		};
	}
}
