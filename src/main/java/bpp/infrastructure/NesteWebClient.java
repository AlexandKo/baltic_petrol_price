package bpp.infrastructure;

import bpp.entity.NestePriceEntity;
import bpp.model.PetrolPrice;
import bpp.model.WebPageResponse;
import bpp.repository.NestePriceRepository;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static bpp.util.CodeErrors.WEB_CLIENT_CONNECTION_FAILED;
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
    @Value("${neste.price_link}")
    private String nestePriceLink;
    private Pattern pattern;

    @PostConstruct
    private void before() {
        pattern = Pattern.compile(NESTE_SEARCH_PRICE_PATTERN);
    }

    @Override
    public PetrolPrice getContent() {
        PetrolPrice petrolPrice = null;
        WebPageResponse nesteWebContent = getWebContent(nestePriceLink);

        if (nesteWebContent.getId() == WEB_CLIENT_CONNECTION_FAILED) {
            return createFailedPetrolPrice(nesteWebContent.getId(), nesteWebContent.getContent());
        }

        final Matcher matcher = pattern.matcher(nesteWebContent.getContent());

        while (matcher.find()) {
            petrolPrice = PetrolPrice.builder()
                    .id(nesteWebContent.getId())
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
                .petrol(petrolPrice.getPetrol())
                .petrolBestPriceAddress(petrolPrice.getPetrolBestPriceAddress())
                .petrolPro(petrolPrice.getPetrolPro())
                .petrolProBestPriceAddress(petrolPrice.getPetrolProBestPriceAddress())
                .diesel(petrolPrice.getDiesel())
                .dieselBestPriceAddress(petrolPrice.getDieselBestPriceAddress())
                .dieselPro(petrolPrice.getDieselPro())
                .dieselProBestPriceAddress(petrolPrice.getDieselProBestPriceAddress())
                .build();

        nestePriceRepository.save(nestePriceEntity);
        return petrolPrice;
    }
}
