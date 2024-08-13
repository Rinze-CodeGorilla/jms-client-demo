package nl.codegorilla.jmsclientdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JmsClientDemoApplication {
    static String clientId;

    public static void main(String[] args) {
        clientId = args.length > 0 ? args[0] : "default client";
        SpringApplication.run(JmsClientDemoApplication.class, args);
    }

    @Bean
    public String getClientId(
    ) {
        return clientId;
    }

}
