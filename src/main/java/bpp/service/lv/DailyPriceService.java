package bpp.service.lv;

import bpp.entity.*;
import bpp.model.DailyPriceModel;
import bpp.model.Response;
import bpp.repository.*;
import com.google.common.annotations.VisibleForTesting;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DailyPriceService {
    private static final String GOTIKA_STATION = "Gotika";
    private static final String NESTE_STATION = "Neste";
    private static final String CIRCLEK_STATION = "CircleK";
    private static final String VIADA_STATION = "Viada";
    private static final String VIRSI_STATION = "Virsi";
    private final GotikaPriceRepository gotikaPriceRepository;
    private final CirclePriceRepository circlePriceRepository;
    private final NestePriceRepository nestePriceRepository;
    private final ViadaPriceRepository viadaPriceRepository;
    private final VirsiPriceRepository virsiPriceRepository;

    public Response<DailyPriceModel> findBestDailyPrice() {
        Map<String, BigDecimal> petrolMap = new HashMap<>();
        Map<String, BigDecimal> dieselMap = new HashMap<>();

        LocalDate lastPriceDate = createFuelMap(petrolMap, dieselMap);

        List<Map.Entry<String, BigDecimal>> petrolBestPrice = findBestPrice(petrolMap);
        List<Map.Entry<String, BigDecimal>> dieselBestPrice = findBestPrice(dieselMap);

        return new Response<>(DailyPriceModel.builder()
                .date(lastPriceDate)
                .petrolPrice(petrolBestPrice)
                .dieselPrice(dieselBestPrice)
                .build());
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

    private LocalDate createFuelMap(Map<String, BigDecimal> petrolMap, Map<String, BigDecimal> dieselMap) {
        GotikaPriceEntity gotikaLastPriceEntity = gotikaPriceRepository
                .searchLastDatePrices(LocalDateTime.now(), LocalDateTime.now().minusDays(1));
        NestePriceEntity nesteLastPriceEntity = nestePriceRepository
                .searchLastDatePrices(LocalDateTime.now(), LocalDateTime.now().minusDays(1));
        CirclePriceEntity circleLastPriceEntity = circlePriceRepository
                .searchLastDatePrices(LocalDateTime.now(), LocalDateTime.now().minusDays(1), "LV");
        ViadaPriceEntity viadaLastPriceEntity = viadaPriceRepository
                .searchLastDatePrices(LocalDateTime.now(), LocalDateTime.now().minusDays(1));
        VirsiPriceEntity virsiLastPriceEntity = virsiPriceRepository
                .searchLastDatePrices(LocalDateTime.now(), LocalDateTime.now().minusDays(1));

        petrolMap.put(GOTIKA_STATION, gotikaLastPriceEntity.getPetrol());
        petrolMap.put(NESTE_STATION, nesteLastPriceEntity.getPetrol());
        petrolMap.put(CIRCLEK_STATION, circleLastPriceEntity.getPetrol());
        petrolMap.put(VIADA_STATION, viadaLastPriceEntity.getPetrolEco());
        petrolMap.put(VIRSI_STATION, virsiLastPriceEntity.getPetrol());
        dieselMap.put(GOTIKA_STATION, gotikaLastPriceEntity.getDiesel());
        dieselMap.put(NESTE_STATION, nesteLastPriceEntity.getDiesel());
        dieselMap.put(CIRCLEK_STATION, circleLastPriceEntity.getDiesel());
        dieselMap.put(VIADA_STATION, viadaLastPriceEntity.getDiesel());
        dieselMap.put(VIRSI_STATION, virsiLastPriceEntity.getDiesel());
        return gotikaLastPriceEntity.getCreatedDate().toLocalDate();
    }
}
