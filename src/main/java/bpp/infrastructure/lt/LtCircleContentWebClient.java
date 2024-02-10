package bpp.infrastructure.lt;

import bpp.infrastructure.ContentWebClient;
import bpp.model.CirclePetrolPriceModel;
import bpp.model.ErrorModel;
import bpp.model.Response;
import bpp.model.WebPageResponseModel;
import bpp.util.Country;
import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static bpp.util.MessageCodes.WEB_CLIENT_CONNECTION_FAILED;

@Component
public class LtCircleContentWebClient extends ContentWebClient<Response<?>> {
    private static final String EMPTY_STRING = "";
    private static final String CIRCLEK_SEARCH_PRICE_PATTERN = "(.*?)\\nImage\\n(\\d,\\d{2,3})(\\n.*\\n.*\\n.*)";
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
        final Matcher matcher = pattern.matcher(circleWebContent.getContent());

        int index = 0;
        final Map<Integer, String> groups = new HashMap<>();
        while (matcher.find()) {
            for (int i = 2; i <= 3; i++) {
                groups.put(index++, matcher.group(i));
            }
        }

        return CirclePetrolPriceModel.builder()
                .id(circleWebContent.getId())
                .country(Country.LT)
                .petrol(createPriceWithDecimalPoint(groups.get(0)))
                .petrolBestPriceAddress(setDescription(groups.get(1).replace("\\n", EMPTY_STRING)))
                .petrolPro(createPriceWithDecimalPoint(groups.get(4)))
                .petrolProBestPriceAddress(setDescription(groups.get(5).replace("\\n", EMPTY_STRING)))
                .diesel(createPriceWithDecimalPoint(groups.get(6)))
                .dieselBestPriceAddress(setDescription(groups.get(7).replace("\\n", EMPTY_STRING)))
                .dieselPro(createPriceWithDecimalPoint(groups.get(8)))
                .dieselProBestPriceAddress(setDescription(groups.get(9).replace("\\n", EMPTY_STRING)))
                .gas(createPriceWithDecimalPoint(groups.get(10)))
                .gasBestPriceAddress(setDescription(groups.get(11).replace("\\n", EMPTY_STRING)))
                .build();
    }

    private String setDescription(String bestPriceAddress) {
        return bestPriceAddress.replace("\n", Strings.EMPTY);
    }
}
