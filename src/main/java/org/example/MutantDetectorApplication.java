package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
public class MutantDetectorApplication {

    private static final Logger log = LoggerFactory.getLogger(MutantDetectorApplication.class);

    private final Environment env;

    public MutantDetectorApplication(Environment env) {
        this.env = env;
    }

    public static void main(String[] args) {
        SpringApplication.run(MutantDetectorApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void printSwaggerUrl() {
        String protocol = "http";
        String host = "localhost";
        try {
            host = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.warn("The host name could not be determined, using `localhost` as fallback");
        }
        String port = env.getProperty("server.port", "8080");
        String contextPath = env.getProperty("server.servlet.context-path", "");
        String swaggerUiPath = "/swagger-ui.html";

        log.info("\n----------------------------------------------------------\n\t" +
                        "Application '{}' is running! Access URLs:\n\t" +
                        "Local: \t\t{}://localhost:{}{}{}\n\t" +
                        "External: \t{}://{}:{}{}{}\n\t" +
                        "Profile(s): \t{}\n----------------------------------------------------------",
                env.getProperty("spring.application.name"),
                protocol,
                port,
                contextPath,
                swaggerUiPath,
                protocol,
                host,
                port,
                contextPath,
                swaggerUiPath,
                env.getActiveProfiles());
    }
}
