package bpp.service.lv;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class DailyPriceServiceTest {
    private final DailyPriceService dailyPriceService = new DailyPriceService(null, null, null, null, null);

    @Test
    void oneMinimalPrice() {
        Map<String, BigDecimal> petrol = new HashMap<>();
        petrol.put("Neste", new BigDecimal("1.45"));
        petrol.put("Gotika", new BigDecimal("1.44"));

        List<Map.Entry<String, BigDecimal>> result = dailyPriceService.findBestPrice(petrol);

        assertThat(result.get(0).getKey()).isEqualTo("Gotika");
        assertThat(result.get(0).getValue()).isEqualTo(new BigDecimal("1.44"));
    }

    @Test
    void twoMinimalPrices() {
        Map<String, BigDecimal> petrol = new HashMap<>();
        petrol.put("Neste", new BigDecimal("1.44"));
        petrol.put("Gotika", new BigDecimal("1.44"));

        List<Map.Entry<String, BigDecimal>> result = dailyPriceService.findBestPrice(petrol);

        assertThat(result.get(0).getKey()).isEqualTo("Neste");
        assertThat(result.get(0).getValue()).isEqualTo(new BigDecimal("1.44"));
        assertThat(result.get(1).getKey()).isEqualTo("Gotika");
        assertThat(result.get(1).getValue()).isEqualTo(new BigDecimal("1.44"));
    }
}