package bpp.schedule;

import bpp.service.LvPetrolPriceService;
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
    private final List<LvPetrolPriceService> lvPetrolPriceServiceList;

    @Async
    @Scheduled(cron = "1 * * * * ?")
    public void scheduleTask() {
        lvPetrolPriceServiceList.forEach(LvPetrolPriceService::savePetrolPrice);
    }
}
