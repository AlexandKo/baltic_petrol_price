package bpp.infrastructure.se;

import bpp.infrastructure.ContentWebClient;
import bpp.model.CirclePetrolPriceModel;
import bpp.model.ErrorModel;
import bpp.model.Response;
import bpp.model.WebPageResponseModel;
import bpp.util.Country;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static bpp.util.MessageCodes.WEB_CLIENT_CONNECTION_SUCCESSFULLY;
import static bpp.util.Messages.PRICE_FOR_ALL_STATIONS;

@Component
@RequiredArgsConstructor
public class SeCircleContentWebClient extends ContentWebClient<Response<?>> {
    private static final String CIRCLEK_SEARCH_PRICE_PATTERN = "(.*?)\\n\\tPris: (\\d{2},\\d{2})";
    private final RestTemplate restTemplate;
    ;
    @Value("${circleK.se_price_link}")
    private String seCirclePriceLink;
    @Value("${exchange.link}")
    private String exchangeUrl;
    private Pattern pattern;

    @PostConstruct
    private void before() {
        pattern = Pattern.compile(CIRCLEK_SEARCH_PRICE_PATTERN);
    }

    @Override
    public Response<?> getContent() {
        WebPageResponseModel circleWebContent = getWebContent(seCirclePriceLink);

        if (circleWebContent.getId() == WEB_CLIENT_CONNECTION_SUCCESSFULLY) {
            ErrorModel errorModel = ErrorModel.builder()
                    .id(circleWebContent.getId())
                    .country(Country.SE)
                    .errorMessage("No Data")
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
            groups.put(index, matcher.group(2));
            index++;
        }

        return CirclePetrolPriceModel.builder()
                .id(circleWebContent.getId())
                .country(Country.SE)
                .petrol(convertToEuro(groups.get(0)))
                .petrolBestPriceAddress(PRICE_FOR_ALL_STATIONS)
                .petrolPro(convertToEuro(groups.get(1)))
                .petrolProBestPriceAddress(PRICE_FOR_ALL_STATIONS)
                .diesel(convertToEuro(groups.get(3)))
                .dieselBestPriceAddress(PRICE_FOR_ALL_STATIONS)
                .dieselPro(convertToEuro(groups.get(4)))
                .dieselProBestPriceAddress(PRICE_FOR_ALL_STATIONS)
                .gas(convertToEuro(groups.get(6)))
                .gasBestPriceAddress(PRICE_FOR_ALL_STATIONS)
                .petrolAutomatic(convertToEuro(groups.get(8)))
                .petrolProAutomatic(convertToEuro(groups.get(9)))
                .dieselAutomatic(convertToEuro(groups.get(10)))
                .build();
    }

    private BigDecimal convertToEuro(String volume) {
        BigDecimal price = createPriceWithDecimalPoint(volume);

        String priceInEuro = restTemplate.getForObject(exchangeUrl + "NOK&volume=" + price, String.class);
        if (priceInEuro == null) {
            return new BigDecimal(BigInteger.ZERO);
        }
        return new BigDecimal(priceInEuro);
    }
}
