package bpp.infrastructure.lv;

import bpp.infrastructure.ContentWebClient;
import bpp.model.ErrorModel;
import bpp.model.GotikaPetrolPriceModel;
import bpp.model.Response;
import bpp.model.WebPageResponseModel;
import bpp.util.Country;
import jakarta.annotation.PostConstruct;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static bpp.util.MessageCodes.WEB_CLIENT_CONNECTION_FAILED;
import static bpp.util.Messages.PRICE_FOR_ALL_STATIONS;
import static bpp.util.PetrolNames.DIESEL;
import static bpp.util.PetrolNames.PETROL;

@Component
public class GotikaContentWebClient extends ContentWebClient<Response<?>> {
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
    public Response<?> getContent() {
        WebPageResponseModel gotikaWebContent = getWebContent(gotikaPriceLink);

        if (gotikaWebContent.getId() == WEB_CLIENT_CONNECTION_FAILED) {
            ErrorModel errorModel = ErrorModel.builder()
                    .id(gotikaWebContent.getId())
                    .country(Country.LV)
                    .errorMessage(gotikaWebContent.getContent())
                    .build();
            return new Response<>(errorModel);
        }

        GotikaPetrolPriceModel gotikaPetrolPriceModel = getGotikaPetrolPriceModel(gotikaWebContent);
        return new Response<>(gotikaPetrolPriceModel);
    }

    private GotikaPetrolPriceModel getGotikaPetrolPriceModel(WebPageResponseModel gotikaWebContent) {
        GotikaPetrolPriceModel gotikaPetrolPriceModel = null;

        final Matcher matcher = pattern.matcher(gotikaWebContent.getContent());

        while (matcher.find()) {
            gotikaPetrolPriceModel = GotikaPetrolPriceModel.builder()
                    .id(gotikaWebContent.getId())
                    .country(Country.LV)
                    .petrol(createPriceFromString(matcher.group(PETROL)))
                    .petrolBestPriceAddress(PRICE_FOR_ALL_STATIONS)
                    .diesel(createPriceFromString(matcher.group(DIESEL)))
                    .dieselBestPriceAddress(PRICE_FOR_ALL_STATIONS)
                    .build();
        }
        return gotikaPetrolPriceModel;
    }
}
