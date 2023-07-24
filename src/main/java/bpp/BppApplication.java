package bpp;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class BppApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(BppApplication.class).run(args);
    }
}
