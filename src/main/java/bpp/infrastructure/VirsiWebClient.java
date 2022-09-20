package bpp.infrastructure;

import bpp.model.PetrolPrice;
import bpp.model.WebPageResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static bpp.util.CodeErrors.WEB_CLIENT_CONNECTION_FAILED;
import static bpp.util.Messages.PRICE_FOR_ALL_STATIONS;
import static bpp.util.PetrolNames.DIESEL;
import static bpp.util.PetrolNames.DIESEL_BEST_PRICE_ADDRESS;
import static bpp.util.PetrolNames.GAS;
import static bpp.util.PetrolNames.GAS_BEST_PRICE_ADDRESS;
import static bpp.util.PetrolNames.PETROL;
import static bpp.util.PetrolNames.PETROL_BEST_PRICE_ADDRESS;
import static bpp.util.PetrolNames.PETROL_PRO;
import static bpp.util.PetrolNames.PETROL_PRO_BEST_PRICE_ADDRESS;

@Component
public class VirsiWebClient extends WebClient {
    private static final String VIRSI_SEARCH_PRICE_PATTERN = "" +
            "(?<diesel>\\d.?\\d{3})(\\n)" +
            "(?<dieselBestPriceAddress>.*)(\\n.*\\n)" +
            "(?<petrol95>\\d.?\\d{3})(\\n)" +
            "(?<petrol95BestPriceAddress>.*)(\\n.*\\n)" +
            "(?<petrol98>\\d.?\\d{3})(\\n)" +
            "(?<petrol98BestPriceAddress>.*)(\\n.*\\n)(\\d.?\\d{3})(\\n)(.*)(\\n.*\\n)" +
            "(?<gas>\\d.?\\d{3})(\\n)" +
            "(?<gasBestPriceAddress>.*)";
    @Value("${virsi.price_link}")
    private String virsiPriceLink;
    private Pattern pattern;

    @PostConstruct
    private void before() {
        pattern = Pattern.compile(VIRSI_SEARCH_PRICE_PATTERN);
    }

    @Override
    public PetrolPrice getContent() {
        PetrolPrice petrolPrice = null;
        WebPageResponse virsiWebContent = getWebContent(virsiPriceLink);

        if (virsiWebContent.getId() == WEB_CLIENT_CONNECTION_FAILED) {
            return createFailedPetrolPrice(virsiWebContent.getId(), virsiWebContent.getContent());
        }

        final Matcher matcher = pattern.matcher(virsiWebContent.getContent());

        while (matcher.find()) {
            petrolPrice = PetrolPrice.builder()
                    .id(virsiWebContent.getId())
                    .petrol(createPriceFromString(matcher.group(PETROL)))
                    .petrolBestPriceAddress(setDescription(matcher.group(PETROL_BEST_PRICE_ADDRESS)))
                    .petrolPro(createPriceFromString(matcher.group(PETROL_PRO)))
                    .petrolProBestPriceAddress(setDescription(matcher.group(PETROL_PRO_BEST_PRICE_ADDRESS)))
                    .diesel(createPriceFromString(matcher.group(DIESEL)))
                    .dieselBestPriceAddress(setDescription(matcher.group(DIESEL_BEST_PRICE_ADDRESS)))
                    .gas(createPriceFromString(matcher.group(GAS)))
                    .gasProBestPriceAddress(setDescription(matcher.group(GAS_BEST_PRICE_ADDRESS)))
                    .build();
        }
        return petrolPrice;
    }

    private String setDescription(String bestPriceAddress) {
        return bestPriceAddress.equals(Strings.EMPTY) ? PRICE_FOR_ALL_STATIONS : bestPriceAddress;
    }
}
