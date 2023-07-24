package bpp.schedule;

import bpp.service.PetrolPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class PetrolPriceScheduleTask {
    private final List<PetrolPriceService> petrolPriceServiceList;

    @Scheduled(cron = "${bpp.update_time}")
    public void scheduleTask() {
        petrolPriceServiceList.forEach(PetrolPriceService::savePetrolPrice);
    }
}
