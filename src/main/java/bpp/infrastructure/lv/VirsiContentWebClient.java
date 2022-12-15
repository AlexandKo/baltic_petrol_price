package bpp.infrastructure.lv;

import bpp.infrastructure.ContentWebClient;
import bpp.model.ErrorModel;
import bpp.model.Response;
import bpp.model.VirsiPetrolPriceModel;
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
import static bpp.util.PetrolNames.GAS;
import static bpp.util.PetrolNames.GAS_BEST_PRICE_ADDRESS;
import static bpp.util.PetrolNames.PETROL;
import static bpp.util.PetrolNames.PETROL_BEST_PRICE_ADDRESS;
import static bpp.util.PetrolNames.PETROL_PRO;
import static bpp.util.PetrolNames.PETROL_PRO_BEST_PRICE_ADDRESS;

@Component
public class VirsiContentWebClient extends ContentWebClient<Response<?>> {
    private static final String VIRSI_SEARCH_PRICE_PATTERN = "DD." +
            "(?<diesel>\\d.?\\d{3})(\\n)" +
            "(?<dieselBestPriceAddress>.*)(\\n)" + "95E." +
            "(?<petrol95>\\d.?\\d{3})(\\n)" +
            "(?<petrol95BestPriceAddress>.*)(\\n)" + "98E." +
            "(?<petrol98>\\d.?\\d{3})(\\n)" +
            "(?<petrol98BestPriceAddress>.*)(\\n.*\\n)(.*\\n)" + "LPG." +
            "(?<gas>\\d.?\\d{3})(\\n)" +
            "(?<gasBestPriceAddress>.*)";
    @Value("${virsi.lv_price_link}")
    private String virsiPriceLink;
    private Pattern pattern;

    @PostConstruct
    private void before() {
        pattern = Pattern.compile(VIRSI_SEARCH_PRICE_PATTERN);
    }

    @Override
    public Response<?> getContent() {
        WebPageResponseModel virsiWebContent = getWebContent(virsiPriceLink);

        if (virsiWebContent.getId() == WEB_CLIENT_CONNECTION_FAILED) {
            ErrorModel errorModel = ErrorModel.builder()
                    .id(virsiWebContent.getId())
                    .country(Country.LV)
                    .errorMessage(virsiWebContent.getContent())
                    .build();
            return new Response<>(errorModel);
        }

        VirsiPetrolPriceModel virsiPetrolPriceModel = getVirsiPetrolPriceModel(virsiWebContent);
        return new Response<>(virsiPetrolPriceModel);
    }

    private VirsiPetrolPriceModel getVirsiPetrolPriceModel(WebPageResponseModel virsiWebContent) {
        VirsiPetrolPriceModel virsiPetrolPriceModel = null;

        final Matcher matcher = pattern.matcher(virsiWebContent.getContent());

        while (matcher.find()) {
            virsiPetrolPriceModel = VirsiPetrolPriceModel.builder()
                    .id(virsiWebContent.getId())
                    .country(Country.LV)
                    .petrol(createPriceFromString(matcher.group(PETROL)))
                    .petrolBestPriceAddress(setDescription(matcher.group(PETROL_BEST_PRICE_ADDRESS)))
                    .petrolPro(createPriceFromString(matcher.group(PETROL_PRO)))
                    .petrolProBestPriceAddress(setDescription(matcher.group(PETROL_PRO_BEST_PRICE_ADDRESS)))
                    .diesel(createPriceFromString(matcher.group(DIESEL)))
                    .dieselBestPriceAddress(setDescription(matcher.group(DIESEL_BEST_PRICE_ADDRESS)))
                    .gas(createPriceFromString(matcher.group(GAS)))
                    .gasBestPriceAddress(setDescription(matcher.group(GAS_BEST_PRICE_ADDRESS)))
                    .build();
        }
        return virsiPetrolPriceModel;
    }

    private String setDescription(String bestPriceAddress) {
        return bestPriceAddress.equals(Strings.EMPTY) ? PRICE_FOR_ALL_STATIONS : bestPriceAddress;
    }
}
