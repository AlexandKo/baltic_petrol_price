package bpp.infrastructure.lv;

import bpp.infrastructure.ContentWebClient;
import bpp.model.PetrolPriceModel;
import bpp.model.WebPageResponseModel;
import bpp.util.Country;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static bpp.util.MessageCodes.WEB_CLIENT_CONNECTION_FAILED;
import static bpp.util.Messages.PRICE_FOR_ALL_STATIONS;
import static bpp.util.PetrolNames.DIESEL;
import static bpp.util.PetrolNames.PETROL;

@Component
public class GotikaContentWebClient extends ContentWebClient {
    private static final String GOTIKA_SEARCH_PRICE_PATTERN = "" +
            "(?<petrol95>\\d.?\\d{3})(.*\\n)(.*\\n)" +
            "(?<diesel>\\d.?\\d{3})";
    @Value("${gotikaAuto.lv_price_link}")
    private String gotikaPriceLink;
    private Pattern pattern;

    @PostConstruct
    private void before() {
        pattern = Pattern.compile(GOTIKA_SEARCH_PRICE_PATTERN);
    }

    @Override
    public PetrolPriceModel getContent() {
        PetrolPriceModel petrolPriceModel = null;
        WebPageResponseModel gotikaWebContent = getWebContent(gotikaPriceLink);

        if (gotikaWebContent.getId() == WEB_CLIENT_CONNECTION_FAILED) {
            return createFailedPetrolPrice(gotikaWebContent.getId(), gotikaWebContent.getContent());
        }

        final Matcher matcher = pattern.matcher(gotikaWebContent.getContent());

        while (matcher.find()) {
            petrolPriceModel = PetrolPriceModel.builder()
                    .id(gotikaWebContent.getId())
                    .country(Country.LV)
                    .petrol(createPriceFromString(matcher.group(PETROL)))
                    .petrolBestPriceAddress(PRICE_FOR_ALL_STATIONS)
                    .diesel(createPriceFromString(matcher.group(DIESEL)))
                    .dieselBestPriceAddress(PRICE_FOR_ALL_STATIONS)
                    .build();
        }
        return petrolPriceModel;
    }
}
