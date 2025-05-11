package co.com.nequi;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import reactor.core.publisher.Hooks;

import java.util.Arrays;

@NoArgsConstructor
@SpringBootApplication
@ConfigurationPropertiesScan
@Slf4j
public class Main {
    public static void main(String[] args){
        Hooks.enableAutomaticContextPropagation();
        SpringApplication.run(Main.class, args);
    }
}