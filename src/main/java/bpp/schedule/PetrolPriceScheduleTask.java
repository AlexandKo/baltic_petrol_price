package bpp.schedule;

import bpp.infrastructure.WebClient;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableAsync
@EnableScheduling
@RequiredArgsConstructor
public class PetrolPriceScheduleTask {
    private final List<WebClient> webClients;

    @Async
    @Scheduled(cron = "1 * * * * ?")
    public void scheduleTask() {
        System.out.println("It work");
        webClients.forEach(WebClient::getContent);
    }
}
