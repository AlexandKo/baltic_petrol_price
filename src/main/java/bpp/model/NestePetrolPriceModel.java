package bpp.model;

import bpp.util.Country;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class NestePetrolPriceModel {
    @Schema(example = "200")
    private int id;
    @Schema(example = "LV")
    private Country country;
    @Schema(example = "1.717")
    private BigDecimal petrol;
    @Schema(example = "1.757")
    private BigDecimal petrolPro;
    @Schema(example = "1.777")
    private BigDecimal diesel;
    @Schema(example = "1.887")
    private BigDecimal dieselPro;
    @Schema(example = "The fuel price is set for all gas stations")
    private String petrolBestPriceAddress;
    @Schema(example = "The fuel price is set for all gas stations")
    private String petrolProBestPriceAddress;
    @Schema(example = "The fuel price is set for all gas stations")
    private String dieselBestPriceAddress;
    @Schema(example = "The fuel price is set for all gas stations")
    private String dieselProBestPriceAddress;
    @Schema(example = "null")
    private String errorMessage;
}
