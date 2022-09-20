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
import static bpp.util.PetrolNames.DIESEL_PRO;
import static bpp.util.PetrolNames.PETROL;
import static bpp.util.PetrolNames.PETROL_PRO;

@Component
public class NesteWebClient extends WebClient {
    private static final String NESTE_SEARCH_PRICE_PATTERN = "" +
            "(?<petrol95>\\d.\\d\\d\\d)(\\n\\t.*\\n.*\\n\\t)" +
            "(?<petrol98>\\d.\\d\\d\\d)(\\n\\t.*\\n.*\\n\\t)" +
            "(?<diesel>\\d.\\d\\d\\d)(\\n\\t.*\\n.*\\n\\t)" +
            "(?<dieselPro>\\d.\\d\\d\\d)";
    @Value("${neste.price_link}")
    private String nestePriceLink;
    private Pattern pattern;

    @PostConstruct
    private void before() {
        pattern = Pattern.compile(NESTE_SEARCH_PRICE_PATTERN);
    }

    @Override
    public PetrolPrice getContent() {
        PetrolPrice petrolPrice = null;
        WebPageResponse nesteWebContent = super.getWebContent(nestePriceLink);

        if (nesteWebContent.getId() == WEB_CLIENT_CONNECTION_FAILED) {
            return super.createFailedPetrolPrice(nesteWebContent.getId(), nesteWebContent.getContent());
        }

        final Matcher matcher = pattern.matcher(nesteWebContent.getContent());

        while (matcher.find()) {
            petrolPrice = PetrolPrice.builder()
                    .id(nesteWebContent.getId())
                    .petrol(new BigDecimal(matcher.group(PETROL)))
                    .petrolBestPriceAddress(PRICE_FOR_ALL_STATIONS)
                    .petrolPro(new BigDecimal(matcher.group(PETROL_PRO)))
                    .petrolProBestPriceAddress(PRICE_FOR_ALL_STATIONS)
                    .diesel(new BigDecimal(matcher.group(DIESEL)))
                    .dieselBestPriceAddress(PRICE_FOR_ALL_STATIONS)
                    .dieselPro(new BigDecimal(matcher.group(DIESEL_PRO)))
                    .dieselProBestPriceAddress(PRICE_FOR_ALL_STATIONS)
                    .build();
        }
        return petrolPrice;
    }
}
