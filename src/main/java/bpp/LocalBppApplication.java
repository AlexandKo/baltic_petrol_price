package bpp;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class LocalBppApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(LocalBppApplication.class).run(args);
    }
}
