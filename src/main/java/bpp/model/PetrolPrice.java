package bpp.model;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class PetrolPrice {
    private int id;
    private BigDecimal petrol;
    private BigDecimal petrolPro;
    private BigDecimal diesel;
    private BigDecimal dieselPro;
    private BigDecimal gas;
    private String petrolBestPriceAddress;
    private String petrolProBestPriceAddress;
    private String dieselBestPriceAddress;
    private String dieselProBestPriceAddress;
    private String gasProBestPriceAddress;
    private String errorMessage;
}
