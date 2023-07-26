package bpp.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Builder
@Getter
public class DailyPriceModel {
    @Schema(example = "2023-06-07")
    private LocalDate date;
    @Schema(requiredProperties = {"Gotika", "1.447"})
    private List<Map.Entry<String, BigDecimal>> petrolPrice;
    private List<Map.Entry<String, BigDecimal>> dieselPrice;
}
