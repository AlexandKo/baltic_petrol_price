package bpp.schedule;

import bpp.service.PetrolPriceService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class PetrolPriceScheduleTask {
    private final List<PetrolPriceService> petrolPriceServiceList;

    @Async
    @Scheduled(cron = "1 * * * * ?")
    public void scheduleTask() {
        petrolPriceServiceList.forEach(PetrolPriceService::savePetrolPrice);
    }
}
