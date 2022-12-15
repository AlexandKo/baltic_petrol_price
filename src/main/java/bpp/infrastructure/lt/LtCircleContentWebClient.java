package bpp.infrastructure.lt;

import bpp.infrastructure.ContentWebClient;
import bpp.model.CirclePetrolPriceModel;
import bpp.model.ErrorModel;
import bpp.model.Response;
import bpp.model.WebPageResponseModel;
import bpp.util.Country;
import jakarta.annotation.PostConstruct;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static bpp.util.MessageCodes.WEB_CLIENT_CONNECTION_FAILED;
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
public class LtCircleContentWebClient extends ContentWebClient<Response<?>> {
    private static final String FULL_LINE_WITH_NEXT_LINE_CHAR_PATTERN = "(.*\\n)";
    private static final String THREE_LINES_WITH_NEXT_LINE_CHAR_PATTERN = ".*\\n.*\\n.*\\n";
    private static final String PETROL_PRICE = "\\d.?\\d{3})(\\n)";
    private static final String CIRCLEK_SEARCH_PRICE_PATTERN = "" +
            "(Apa)" + FULL_LINE_WITH_NEXT_LINE_CHAR_PATTERN + FULL_LINE_WITH_NEXT_LINE_CHAR_PATTERN +
            "(?<petrol95>" + PETROL_PRICE +
            "(?<petrol95BestPriceAddress>.*\\n.*\\n.*\\n)" + FULL_LINE_WITH_NEXT_LINE_CHAR_PATTERN +
            FULL_LINE_WITH_NEXT_LINE_CHAR_PATTERN + "(\\d.?\\d{3})(\\n)(.*\\n.*\\n.*\\n)" +
            FULL_LINE_WITH_NEXT_LINE_CHAR_PATTERN + FULL_LINE_WITH_NEXT_LINE_CHAR_PATTERN +
            "(?<petrol98>" + PETROL_PRICE +
            "(?<petrol98BestPriceAddress>" + THREE_LINES_WITH_NEXT_LINE_CHAR_PATTERN + ")" +
            FULL_LINE_WITH_NEXT_LINE_CHAR_PATTERN + FULL_LINE_WITH_NEXT_LINE_CHAR_PATTERN +
            "(?<diesel>" + PETROL_PRICE +
            "(?<dieselBestPriceAddress>" + THREE_LINES_WITH_NEXT_LINE_CHAR_PATTERN + ")" +
            FULL_LINE_WITH_NEXT_LINE_CHAR_PATTERN + FULL_LINE_WITH_NEXT_LINE_CHAR_PATTERN +
            "(?<dieselPro>" + PETROL_PRICE +
            "(?<dieselProBestPriceAddress>" + THREE_LINES_WITH_NEXT_LINE_CHAR_PATTERN + ")" +
            FULL_LINE_WITH_NEXT_LINE_CHAR_PATTERN + FULL_LINE_WITH_NEXT_LINE_CHAR_PATTERN +
            "(\\d.?\\d{3})(\\n)(.*\\n.*\\n.*\\n)(.*\\n)(.*\\n)" +
            "(?<gas>" + PETROL_PRICE +
            "(?<gasBestPriceAddress>" + THREE_LINES_WITH_NEXT_LINE_CHAR_PATTERN + ")";
    @Value("${circleK.lt_price_link}")
    private String ltCirclePriceLink;
    private Pattern pattern;

    @PostConstruct
    private void before() {
        pattern = Pattern.compile(CIRCLEK_SEARCH_PRICE_PATTERN);
    }

    @Override
    public Response<?> getContent() {
        WebPageResponseModel circleWebContent = getWebContent(ltCirclePriceLink);

        if (circleWebContent.getId() == WEB_CLIENT_CONNECTION_FAILED) {
            ErrorModel errorModel = ErrorModel.builder()
                    .id(circleWebContent.getId())
                    .country(Country.LT)
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
                    .country(Country.LT)
                    .petrol(createPriceWithDecimalPoint(matcher.group(PETROL)))
                    .petrolBestPriceAddress(setDescription(matcher.group(PETROL_BEST_PRICE_ADDRESS)))
                    .petrolPro(createPriceWithDecimalPoint(matcher.group(PETROL_PRO)))
                    .petrolProBestPriceAddress(setDescription(matcher.group(PETROL_PRO_BEST_PRICE_ADDRESS)))
                    .diesel(createPriceWithDecimalPoint(matcher.group(DIESEL)))
                    .dieselBestPriceAddress(setDescription(matcher.group(DIESEL_BEST_PRICE_ADDRESS)))
                    .dieselPro(createPriceWithDecimalPoint(matcher.group(DIESEL_PRO)))
                    .dieselProBestPriceAddress(setDescription(matcher.group(DIESEL_PRO_BEST_PRICE_ADDRESS)))
                    .gas(createPriceWithDecimalPoint(matcher.group(GAS)))
                    .gasBestPriceAddress(setDescription(matcher.group(GAS_BEST_PRICE_ADDRESS)))
                    .build();
        }
        return circlePetrolPriceModel;
    }

    private String setDescription(String bestPriceAddress) {
        return bestPriceAddress.replace("\n", Strings.EMPTY);
    }
}
