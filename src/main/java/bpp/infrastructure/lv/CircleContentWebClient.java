package bpp.infrastructure.lv;

import bpp.infrastructure.ContentWebClient;
import bpp.model.CirclePetrolPriceModel;
import bpp.model.ErrorModel;
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
public class CircleContentWebClient extends ContentWebClient<Response<?>> {
    private static final String FULL_LINE_WITH_NEXT_LINE_CHAR_PATTERN = "(.*\\n)";
    private static final String CIRCLEK_SEARCH_PRICE_PATTERN = "" +
            "(?<petrol95>\\d.?\\d{3})" + FULL_LINE_WITH_NEXT_LINE_CHAR_PATTERN +
            "(?<petrol95BestPriceAddress>.*)" + "(\\n.* \\d{2}\\t)" +
            "(?<petrol98>\\d.?\\d{3})" + FULL_LINE_WITH_NEXT_LINE_CHAR_PATTERN +
            "(?<petrol98BestPriceAddress>.*)(\\n.* D\\t)" +
            "(?<diesel>\\d.?\\d{3})" + FULL_LINE_WITH_NEXT_LINE_CHAR_PATTERN +
            "(?<dieselBestPriceAddress>.*)(\\n.* D\\t)" +
            "(?<dieselPro>\\d.?\\d{3})" + FULL_LINE_WITH_NEXT_LINE_CHAR_PATTERN +
            "(?<dieselProBestPriceAddress>.*)(\\n.*ze\\t)" +
            "(?<gas>\\d.?\\d{3})( EUR\\t)" +
            "(?<gasBestPriceAddress>.*)";
    @Value("${circleK.lv_price_link}")
    private String circlePriceLink;
    private Pattern pattern;

    @PostConstruct
    private void before() {
        pattern = Pattern.compile(CIRCLEK_SEARCH_PRICE_PATTERN);
    }

    @Override
    public Response<?> getContent() {
        WebPageResponseModel circleWebContent = getWebContent(circlePriceLink);

        if (circleWebContent.getId() == WEB_CLIENT_CONNECTION_FAILED) {
            ErrorModel errorModel = ErrorModel.builder()
                    .id(circleWebContent.getId())
                    .country(Country.LV)
                    .errorMessage(circleWebContent.getContent())
                    .build();
            return new Response<>(errorModel);
        }

        CirclePetrolPriceModel circlePetrolPriceModel = getCirclePetrolPriceModel(circleWebContent);
        return new Response<>(circlePetrolPriceModel);
    }

    private CirclePetrolPriceModel getCirclePetrolPriceModel(WebPageResponseModel circleWebContent) {
        CirclePetrolPriceModel circlePetrolPriceModel = null;

        final Matcher matcher = pattern.matcher(circleWebContent.getContent());

        while (matcher.find()) {
            circlePetrolPriceModel = CirclePetrolPriceModel.builder()
                    .id(circleWebContent.getId())
                    .country(Country.LV)
                    .petrol(createPriceFromString(matcher.group(PETROL)))
                    .petrolBestPriceAddress(setDescription(matcher.group(PETROL_BEST_PRICE_ADDRESS)))
                    .petrolPro(createPriceFromString(matcher.group(PETROL_PRO)))
                    .petrolProBestPriceAddress(setDescription(matcher.group(PETROL_PRO_BEST_PRICE_ADDRESS)))
                    .diesel(createPriceFromString(matcher.group(DIESEL)))
                    .dieselBestPriceAddress(setDescription(matcher.group(DIESEL_BEST_PRICE_ADDRESS)))
                    .dieselPro(createPriceFromString(matcher.group(DIESEL_PRO)))
                    .dieselProBestPriceAddress(setDescription(matcher.group(DIESEL_PRO_BEST_PRICE_ADDRESS)))
                    .gas(createPriceFromString(matcher.group(GAS)))
                    .gasBestPriceAddress(setDescription(matcher.group(GAS_BEST_PRICE_ADDRESS)))
                    .build();
        }
        return circlePetrolPriceModel;
    }

    private String setDescription(String bestPriceAddress) {
        return bestPriceAddress.contains("DUS") ? PRICE_FOR_ALL_STATIONS : bestPriceAddress;
    }
}
