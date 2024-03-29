package bpp.infrastructure.lv;

import bpp.infrastructure.ContentWebClient;
import bpp.model.ErrorModel;
import bpp.model.Response;
import bpp.model.ViadaPetrolPriceModel;
import bpp.model.WebPageResponseModel;
import bpp.util.Country;
import jakarta.annotation.PostConstruct;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static bpp.util.MessageCodes.WEB_CLIENT_CONNECTION_FAILED;
import static bpp.util.Messages.PRICE_FOR_ALL_STATIONS;
import static bpp.util.PetrolNames.DIESEL;
import static bpp.util.PetrolNames.DIESEL_BEST_PRICE_ADDRESS;
import static bpp.util.PetrolNames.DIESEL_ECTO;
import static bpp.util.PetrolNames.DIESEL_ECTO_BEST_PRICE_ADDRESS;
import static bpp.util.PetrolNames.GAS;
import static bpp.util.PetrolNames.GAS_BEST_PRICE_ADDRESS;
import static bpp.util.PetrolNames.PETROL_E;
import static bpp.util.PetrolNames.PETROL_ECTO;
import static bpp.util.PetrolNames.PETROL_ECTO_BEST_PRICE_ADDRESS;
import static bpp.util.PetrolNames.PETROL_ECTO_PLUS;
import static bpp.util.PetrolNames.PETROL_ECTO_PLUS_BEST_PRICE_ADDRESS;
import static bpp.util.PetrolNames.PETROL_E_BEST_PRICE_ADDRESS;
import static bpp.util.PetrolNames.PETROL_PRO;
import static bpp.util.PetrolNames.PETROL_PRO_BEST_PRICE_ADDRESS;

@Component
public class ViadaContentWebClient extends ContentWebClient<Response<?>> {
    private static final String FULL_LINE_WITH_TAB_CHAR_PATTERN = "( .*\\t)";
    private static final String NEW_LINE_AND_TAB_CHARS_PATTERN = "(\\n\\t)";
    private static final String ANY_CHARS_PATTERN = "(.*)";
    private static final String VIADA_SEARCH_PRICE_PATTERN = "" +
            "(" + ANY_CHARS_PATTERN + "\\tCena EUR.*\\n)(\\t)" +
            "(?<petrolEcto>\\d.?\\d{3})" + FULL_LINE_WITH_TAB_CHAR_PATTERN +
            "(?<petrolEctoBestPriceAddress>" + ANY_CHARS_PATTERN + ")" + NEW_LINE_AND_TAB_CHARS_PATTERN +
            "(?<petrolEctoPlus>\\d.?\\d{3})" + FULL_LINE_WITH_TAB_CHAR_PATTERN +
            "(?<petrolEctoPlusBestPriceAddress>" + ANY_CHARS_PATTERN + ")" + NEW_LINE_AND_TAB_CHARS_PATTERN +
            "(?<petrol98>\\d.?\\d{3})" + FULL_LINE_WITH_TAB_CHAR_PATTERN +
            "(?<petrol98BestPriceAddress>" + ANY_CHARS_PATTERN + ")" + NEW_LINE_AND_TAB_CHARS_PATTERN +
            "(?<diesel>\\d.?\\d{3})" + FULL_LINE_WITH_TAB_CHAR_PATTERN +
            "(?<dieselBestPriceAddress>" + ANY_CHARS_PATTERN + ")" + NEW_LINE_AND_TAB_CHARS_PATTERN +
            "(?<dieselEcto>\\d.?\\d{3})" + FULL_LINE_WITH_TAB_CHAR_PATTERN +
            "(?<dieselEctoBestPriceAddress>" + ANY_CHARS_PATTERN + ")" + NEW_LINE_AND_TAB_CHARS_PATTERN +
            "(?<gas>\\d.?\\d{3})" + FULL_LINE_WITH_TAB_CHAR_PATTERN +
            "(?<gasBestPriceAddress>" + ANY_CHARS_PATTERN + ")" + NEW_LINE_AND_TAB_CHARS_PATTERN +
            "(?<petrol85>\\d.?\\d{3})" + FULL_LINE_WITH_TAB_CHAR_PATTERN +
            "(?<petrolEBestPriceAddress>" + ANY_CHARS_PATTERN + ")";
    @Value("${viada.lv_price_link}")
    private String viadaPriceLink;
    private Pattern pattern;

    @PostConstruct
    private void before() {
        pattern = Pattern.compile(VIADA_SEARCH_PRICE_PATTERN);
    }

    @Override
    public Response<?> getContent() {
        WebPageResponseModel viadaWebContent = getWebContent(viadaPriceLink);

        if (viadaWebContent.getId() == WEB_CLIENT_CONNECTION_FAILED) {
            ErrorModel errorModel = ErrorModel.builder()
                    .id(viadaWebContent.getId())
                    .country(Country.LV)
                    .errorMessage(viadaWebContent.getContent())
                    .build();
            return new Response<>(errorModel);
        }

        ViadaPetrolPriceModel viadaPetrolPriceModel = getViadaPetrolPriceModel(viadaWebContent);
        return new Response<>(viadaPetrolPriceModel);
    }

    private ViadaPetrolPriceModel getViadaPetrolPriceModel(WebPageResponseModel viadaWebContent) {
        ViadaPetrolPriceModel viadaPetrolPriceModel = null;

        final Matcher matcher = pattern.matcher(viadaWebContent.getContent());

        while (matcher.find()) {
            viadaPetrolPriceModel = ViadaPetrolPriceModel.builder()
                    .id(viadaWebContent.getId())
                    .country(Country.LV)
                    .petrolEcto(createPriceFromString(matcher.group(PETROL_ECTO)))
                    .petrolEctoBestPriceAddress(matcher.group(PETROL_ECTO_BEST_PRICE_ADDRESS))
                    .petrolEctoPlus(createPriceFromString(matcher.group(PETROL_ECTO_PLUS)))
                    .petrolEctoPlusBestPriceAddress(matcher.group(PETROL_ECTO_PLUS_BEST_PRICE_ADDRESS))
                    .petrolPro(createPriceFromString(matcher.group(PETROL_PRO)))
                    .petrolProBestPriceAddress(setDescription(matcher.group(PETROL_PRO_BEST_PRICE_ADDRESS)))
                    .diesel(createPriceFromString(matcher.group(DIESEL)))
                    .dieselBestPriceAddress(setDescription(matcher.group(DIESEL_BEST_PRICE_ADDRESS)))
                    .dieselEcto(createPriceFromString(matcher.group(DIESEL_ECTO)))
                    .dieselEctoBestPriceAddress(setDescription(matcher.group(DIESEL_ECTO_BEST_PRICE_ADDRESS)))
                    .gas(createPriceFromString(matcher.group(GAS)))
                    .gasBestPriceAddress(setDescription(matcher.group(GAS_BEST_PRICE_ADDRESS)))
                    .petrolEco(createPriceFromString(matcher.group(PETROL_E)))
                    .petrolEcoBestPriceAddress(matcher.group(PETROL_E_BEST_PRICE_ADDRESS))
                    .build();
        }
        return viadaPetrolPriceModel;
    }

    private String setDescription(String bestPriceAddress) {
        return bestPriceAddress.equals(Strings.EMPTY) ? PRICE_FOR_ALL_STATIONS : bestPriceAddress;
    }
}
