package bpp.infrastructure.lv;

import bpp.entity.NestePriceEntity;
import bpp.infrastructure.WebClient;
import bpp.model.PetrolPriceModel;
import bpp.model.WebPageResponseModel;
import bpp.repository.NestePriceRepository;
import bpp.util.Country;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static bpp.util.MessageCodes.WEB_CLIENT_CONNECTION_FAILED;
import static bpp.util.Messages.PRICE_FOR_ALL_STATIONS;
import static bpp.util.PetrolNames.DIESEL;
import static bpp.util.PetrolNames.DIESEL_PRO;
import static bpp.util.PetrolNames.PETROL;
import static bpp.util.PetrolNames.PETROL_PRO;

@Component
@RequiredArgsConstructor
public class NesteWebClient extends WebClient {
    private final NestePriceRepository nestePriceRepository;
    private static final String NESTE_SEARCH_PRICE_PATTERN = "" +
            "(?<petrol95>\\d.?\\d{3})(\\n\\t.*\\n.*\\n\\t)" +
            "(?<petrol98>\\d.?\\d{3})(\\n\\t.*\\n.*\\n\\t)" +
            "(?<diesel>\\d.?\\d{3})(\\n\\t.*\\n.*\\n\\t)" +
            "(?<dieselPro>\\d.?\\d{3})";
    @Value("${neste.lv_price_link}")
    private String nestePriceLink;
    private Pattern pattern;

    @PostConstruct
    private void before() {
        pattern = Pattern.compile(NESTE_SEARCH_PRICE_PATTERN);
    }

    @Override
    public PetrolPriceModel getContent() {
        PetrolPriceModel petrolPriceModel = null;
        WebPageResponseModel nesteWebContent = getWebContent(nestePriceLink);

        if (nesteWebContent.getId() == WEB_CLIENT_CONNECTION_FAILED) {
            return createFailedPetrolPrice(nesteWebContent.getId(), nesteWebContent.getContent());
        }

        final Matcher matcher = pattern.matcher(nesteWebContent.getContent());

        while (matcher.find()) {
            petrolPriceModel = PetrolPriceModel.builder()
                    .id(nesteWebContent.getId())
                    .country(Country.LV)
                    .petrol(createPriceFromString(matcher.group(PETROL)))
                    .petrolBestPriceAddress(PRICE_FOR_ALL_STATIONS)
                    .petrolPro(createPriceFromString(matcher.group(PETROL_PRO)))
                    .petrolProBestPriceAddress(PRICE_FOR_ALL_STATIONS)
                    .diesel(createPriceFromString(matcher.group(DIESEL)))
                    .dieselBestPriceAddress(PRICE_FOR_ALL_STATIONS)
                    .dieselPro(createPriceFromString(matcher.group(DIESEL_PRO)))
                    .dieselProBestPriceAddress(PRICE_FOR_ALL_STATIONS)
                    .build();
        }

        NestePriceEntity nestePriceEntity = NestePriceEntity.builder()
                .country(petrolPriceModel.getCountry())
                .petrol(petrolPriceModel.getPetrol())
                .petrolBestPriceAddress(petrolPriceModel.getPetrolBestPriceAddress())
                .petrolPro(petrolPriceModel.getPetrolPro())
                .petrolProBestPriceAddress(petrolPriceModel.getPetrolProBestPriceAddress())
                .diesel(petrolPriceModel.getDiesel())
                .dieselBestPriceAddress(petrolPriceModel.getDieselBestPriceAddress())
                .dieselPro(petrolPriceModel.getDieselPro())
                .dieselProBestPriceAddress(petrolPriceModel.getDieselProBestPriceAddress())
                .build();

        nestePriceRepository.save(nestePriceEntity);
        System.out.println("Neste go");
        return petrolPriceModel;
    }
}
