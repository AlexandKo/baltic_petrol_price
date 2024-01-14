package bpp.model;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Builder
@Getter
public class DailyPriceModel {
    private int id;
    private LocalDate date;
    private List<Map.Entry<String, BigDecimal>> petrolPrice;
    private List<Map.Entry<String, BigDecimal>> dieselPrice;
}
