package bpp;

import bpp.infrastructure.CircleWebClient;
import bpp.infrastructure.NesteWebClient;
import bpp.model.PetrolPrice;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class LocalBppApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(LocalBppApplication.class, args);
        NesteWebClient nesteWebClient = context.getBean(NesteWebClient.class);
        PetrolPrice nestePetrolPrice = nesteWebClient.getContent();

        CircleWebClient circleWebClient = context.getBean(CircleWebClient.class);
        PetrolPrice circlePetrolPrice = circleWebClient.getContent();

        System.out.println();
    }
}
