package bpp.model;

import bpp.util.Country;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class VirsiPetrolPriceModel {
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
    @Schema(example = "0.825")
    private BigDecimal gas;
    @Schema(example = "Satekles iela 2, RĪga, LV-1050")
    private String petrolBestPriceAddress;
    @Schema(example = "Satekles iela 2, RĪga, LV-1050")
    private String petrolProBestPriceAddress;
    @Schema(example = "Satekles iela 2, RĪga, LV-1050")
    private String dieselBestPriceAddress;
    @Schema(example = "Uzvaras bulvāris 16, Rīga, LV-1048")
    private String gasBestPriceAddress;
    @Schema(example = "null")
    private String errorMessage;
}
