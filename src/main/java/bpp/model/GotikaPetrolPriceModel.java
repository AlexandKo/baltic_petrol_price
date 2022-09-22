package bpp.model;

import bpp.util.Country;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GotikaPetrolPriceModel {
    @Schema(example = "200")
    private int id;
    @Schema(example = "LV")
    private Country country;
    @Schema(example = "1.687")
    private BigDecimal petrol;
    @Schema(example = "1.747")
    private BigDecimal diesel;
    @Schema(example = "The fuel price is set for all gas stations")
    private String petrolBestPriceAddress;
    @Schema(example = "The fuel price is set for all gas stations")
    private String dieselBestPriceAddress;
    @Schema(example = "null")
    private String errorMessage;
}
