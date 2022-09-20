package bpp.infrastructure;

import bpp.model.PetrolPrice;
import bpp.model.WebPageResponse;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static bpp.util.CodeErrors.WEB_CLIENT_CONNECTION_FAILED;
import static bpp.util.Messages.PRICE_FOR_ALL_STATIONS;
import static bpp.util.PetrolNames.DIESEL;
import static bpp.util.PetrolNames.DIESEL_BEST_PRICE_ADDRESS;
import static bpp.util.PetrolNames.DIESEL_PRO;
import static bpp.util.PetrolNames.DIESEL_PRO_BEST_PRICE_ADDRESS;
import static bpp.util.PetrolNames.GAS;
import static bpp.util.PetrolNames.GAS_BEST_PRICE_ADDRESS;
import static bpp.util.PetrolNames.PETROL;
import static bpp.util.PetrolNames.PETROL_BEST_PRICE_ADDRESS;
import static bpp.util.PetrolNames.PETROL_PRO;
import static bpp.util.PetrolNames.PETROL_PRO_BEST_PRICE_ADDRESS;

@Component
public class CircleWebClient extends WebClient {
    private static final String CIRCLEK_SEARCH_PRICE_PATTERN = "" +
            "(?<petrol95>\\d.\\d\\d\\d)(.*\\n)" +
            "(?<petrol95BestPriceAddress>.*)(\\n.* \\d\\d\\t)" +
            "(?<petrol98>\\d.\\d\\d\\d)(.*\\n)" +
            "(?<petrol98BestPriceAddress>.*)(\\n.* D\\t)" +
            "(?<diesel>\\d.\\d\\d\\d)(.*\\n)" +
            "(?<dieselBestPriceAddress>.*)(\\n.* D\\t)" +
            "(?<dieselPro>\\d.\\d\\d\\d)(.*\\n)" +
            "(?<dieselProBestPriceAddress>.*)(\\n.*ze\\t)" +
            "(?<gas>\\d.\\d\\d\\d)( EUR\\t)" +
            "(?<gasBestPriceAddress>.*)";
    @Value("${circleK.price_link}")
    private String circlePriceLink;
    private Pattern pattern;

    @PostConstruct
    private void before() {
        pattern = Pattern.compile(CIRCLEK_SEARCH_PRICE_PATTERN);
    }

    @Override
    public PetrolPrice getContent() {
        PetrolPrice petrolPrice = null;
        WebPageResponse circleWebContent = super.getWebContent(circlePriceLink);

        if (circleWebContent.getId() == WEB_CLIENT_CONNECTION_FAILED) {
            return super.createFailedPetrolPrice(circleWebContent.getId(), circleWebContent.getContent());
        }

        final Matcher matcher = pattern.matcher(circleWebContent.getContent());

        while (matcher.find()) {
            petrolPrice = PetrolPrice.builder()
                    .id(circleWebContent.getId())
                    .petrol(new BigDecimal(matcher.group(PETROL)))
                    .petrolBestPriceAddress(setDescription(matcher.group(PETROL_BEST_PRICE_ADDRESS)))
                    .petrolPro(new BigDecimal(matcher.group(PETROL_PRO)))
                    .petrolProBestPriceAddress(setDescription(matcher.group(PETROL_PRO_BEST_PRICE_ADDRESS)))
                    .diesel(new BigDecimal(matcher.group(DIESEL)))
                    .dieselBestPriceAddress(setDescription(matcher.group(DIESEL_BEST_PRICE_ADDRESS)))
                    .dieselPro(new BigDecimal(matcher.group(DIESEL_PRO)))
                    .dieselProBestPriceAddress(setDescription(matcher.group(DIESEL_PRO_BEST_PRICE_ADDRESS)))
                    .gas(new BigDecimal(matcher.group(GAS)))
                    .gasProBestPriceAddress(setDescription(matcher.group(GAS_BEST_PRICE_ADDRESS)))
                    .build();
        }
        return petrolPrice;
    }

    private String setDescription(String bestPriceAddress) {
        return bestPriceAddress.contains("DUS") ? PRICE_FOR_ALL_STATIONS : bestPriceAddress;
    }
}
