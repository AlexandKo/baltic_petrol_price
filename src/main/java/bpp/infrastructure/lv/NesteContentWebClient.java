package bpp.infrastructure.lv;

import bpp.infrastructure.ContentWebClient;
import bpp.model.ErrorModel;
import bpp.model.NestePetrolPriceModel;
import bpp.model.Response;
import bpp.model.WebPageResponseModel;
import bpp.util.Country;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static bpp.util.MessageCodes.WEB_CLIENT_CONNECTION_FAILED;
import static bpp.util.PetrolNames.*;

@Component
@RequiredArgsConstructor
public class NesteContentWebClient extends ContentWebClient<Response<?>> {
    private static final String NESTE_SEARCH_PRICE_PATTERN = "(?<petrol95>\\d.?\\d{3})(\\n\\t)" +
            "(?<petrol95BestPriceAddress>.*)(\\n.*\\n\\t)" +
            "(?<petrol98>\\d.?\\d{3})(\\n\\t)" +
            "(?<petrol98BestPriceAddress>.*)(\\n.*\\n\\t)" +
            "(?<diesel>\\d.?\\d{3})(\\n\\t)" +
            "(?<dieselBestPriceAddress>.*)(\\n.*\\n\\t)" +
            "(?<dieselPro>\\d.?\\d{3})(\\n\\t)" +
            "(?<dieselProBestPriceAddress>.*)";
    @Value("${neste.lv_price_link}")
    private String nestePriceLink;
    private Pattern pattern;

    @PostConstruct
    private void before() {
        pattern = Pattern.compile(NESTE_SEARCH_PRICE_PATTERN);
    }

    @Override
    public Response<?> getContent() {
        WebPageResponseModel nesteWebContent = getWebContent(nestePriceLink);

        if (nesteWebContent.getId() == WEB_CLIENT_CONNECTION_FAILED) {
            ErrorModel errorModel = ErrorModel.builder()
                    .id(nesteWebContent.getId())
                    .country(Country.LV)
                    .errorMessage(nesteWebContent.getContent())
                    .build();
            return new Response<>(errorModel);
        }

        NestePetrolPriceModel nestePetrolPriceModel = getNestePetrolPriceModel(nesteWebContent);
        return new Response<>(nestePetrolPriceModel);
    }

    private NestePetrolPriceModel getNestePetrolPriceModel(WebPageResponseModel nesteWebContent) {
        NestePetrolPriceModel nestePetrolPriceModel = null;

        final Matcher matcher = pattern.matcher(nesteWebContent.getContent());

        while (matcher.find()) {
            nestePetrolPriceModel = NestePetrolPriceModel.builder()
                    .id(nesteWebContent.getId())
                    .country(Country.LV)
                    .petrol(createPriceFromString(matcher.group(PETROL)))
                    .petrolBestPriceAddress(matcher.group(PETROL_BEST_PRICE_ADDRESS))
                    .petrolPro(createPriceFromString(matcher.group(PETROL_PRO)))
                    .petrolProBestPriceAddress(matcher.group(PETROL_PRO_BEST_PRICE_ADDRESS))
                    .diesel(createPriceFromString(matcher.group(DIESEL)))
                    .dieselBestPriceAddress(matcher.group(DIESEL_BEST_PRICE_ADDRESS))
                    .dieselPro(createPriceFromString(matcher.group(DIESEL_PRO)))
                    .dieselProBestPriceAddress(matcher.group(DIESEL_PRO_BEST_PRICE_ADDRESS))
                    .build();
        }
        return nestePetrolPriceModel;
    }
}
