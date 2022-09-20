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
import static bpp.util.PetrolNames.PETROL;

@Component
public class GotikaWebClient extends WebClient {
    private static final String GOTIKA_SEARCH_PRICE_PATTERN = "" +
            "(?<petrol95>\\d.\\d\\d\\d)(.*\\n)(.*\\n)" +
            "(?<diesel>\\d.\\d\\d\\d)";
    @Value("${gotikaAuto.price_link}")
    private String gotikaPriceLink;
    private Pattern pattern;

    @PostConstruct
    private void before() {
        pattern = Pattern.compile(GOTIKA_SEARCH_PRICE_PATTERN);
    }

    @Override
    public PetrolPrice getContent() {
        PetrolPrice petrolPrice = null;
        WebPageResponse gotikaWebContent = super.getWebContent(gotikaPriceLink);

        if (gotikaWebContent.getId() == WEB_CLIENT_CONNECTION_FAILED) {
            return super.createFailedPetrolPrice(gotikaWebContent.getId(), gotikaWebContent.getContent());
        }

        final Matcher matcher = pattern.matcher(gotikaWebContent.getContent());

        while (matcher.find()) {
            petrolPrice = PetrolPrice.builder()
                    .id(gotikaWebContent.getId())
                    .petrol(new BigDecimal(matcher.group(PETROL)))
                    .petrolBestPriceAddress(PRICE_FOR_ALL_STATIONS)
                    .diesel(new BigDecimal(matcher.group(DIESEL)))
                    .dieselBestPriceAddress(PRICE_FOR_ALL_STATIONS)
                    .build();
        }
        return petrolPrice;
    }
}
