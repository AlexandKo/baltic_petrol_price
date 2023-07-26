package bpp.service.lv;

import bpp.entity.CirclePriceEntity;
import bpp.entity.GotikaPriceEntity;
import bpp.entity.NestePriceEntity;
import bpp.model.DailyPriceModel;
import bpp.repository.*;
import com.google.common.annotations.VisibleForTesting;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DailyPriceService {
    private static final String GOTIKA_STATION = "Gotika";
    private static final String NESTE_STATION = "Neste";
    private static final String CIRCLEK_STATION = "CircleK";
    private final GotikaPriceRepository gotikaPriceRepository;
    private final CirclePriceRepository circlePriceRepository;
    private final NestePriceRepository nestePriceRepository;
    private final ViadaPriceRepository viadaPriceRepository;
    private final VirsiPriceRepository virsiPriceRepository;

    public DailyPriceModel findBestDailyPrice() {
        Map<String, BigDecimal> petrolMap = new HashMap<>();
        Map<String, BigDecimal> dieselMap = new HashMap<>();

        GotikaPriceEntity gotikaLastPriceEntity = gotikaPriceRepository
                .searchLastDatePrices(LocalDateTime.now(), LocalDateTime.now().minusDays(1));
        NestePriceEntity nesteLastPriceEntity = nestePriceRepository
                .searchLastDatePrices(LocalDateTime.now(), LocalDateTime.now().minusDays(1));
        CirclePriceEntity circleLastPriceEntity = circlePriceRepository
                .searchLastDatePrices(LocalDateTime.now(), LocalDateTime.now().minusDays(1), "LV");

        petrolMap.put(GOTIKA_STATION, gotikaLastPriceEntity.getPetrol());
        petrolMap.put(NESTE_STATION, nesteLastPriceEntity.getPetrol());
        petrolMap.put(CIRCLEK_STATION, circleLastPriceEntity.getPetrol());
        dieselMap.put(GOTIKA_STATION, gotikaLastPriceEntity.getDiesel());
        dieselMap.put(NESTE_STATION, nesteLastPriceEntity.getDiesel());
        dieselMap.put(CIRCLEK_STATION, circleLastPriceEntity.getDiesel());

        List<Map.Entry<String, BigDecimal>> petrolBestPrice = findBestPrice(petrolMap);
        List<Map.Entry<String, BigDecimal>> dieselBestPrice = findBestPrice(dieselMap);

        return DailyPriceModel.builder()
                .date(gotikaLastPriceEntity.getCreatedDate().toLocalDate())
                .petrolPrice(petrolBestPrice)
                .dieselPrice(dieselBestPrice)
                .build();
    }

    @VisibleForTesting
    List<Map.Entry<String, BigDecimal>> findBestPrice(Map<String, BigDecimal> fuelMap) {
        Map.Entry<String, BigDecimal> minFuelPrice = fuelMap
                .entrySet()
                .stream()
                .min(Map.Entry.comparingByValue())
                .orElseThrow(NullPointerException::new);
        List<Map.Entry<String, BigDecimal>> minFuelList = new ArrayList<>();
        minFuelList.add(minFuelPrice);

        fuelMap.entrySet().forEach(entry -> {
            if (entry.getValue().compareTo(minFuelPrice.getValue()) == 0 && !(entry.getKey().equals(minFuelList.get(0).getKey()))) {
                minFuelList.add(entry);
            }
        });
        return minFuelList;
    }
}
