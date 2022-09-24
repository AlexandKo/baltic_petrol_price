package bpp.service.lv;

import bpp.infrastructure.ContentWebClient;
import bpp.infrastructure.lv.CircleContentWebClient;
import bpp.model.Response;
import bpp.service.PetrolPriceService;
import bpp.usecase.CirclePepositoryUseCase;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class LvCirclePetrolPriceService implements PetrolPriceService {
    private final CirclePepositoryUseCase circlePepositoryUseCase;
    private final List<ContentWebClient<?>> contentWebClients;

    @Override
    public void savePetrolPrice() {
        Response<?> circlePetrolPriceResponse = contentWebClients
                .stream()
                .filter(CircleContentWebClient.class::isInstance)
                .map(CircleContentWebClient.class::cast)
                .map(CircleContentWebClient::getContent)
                .findFirst()
                .orElse(null);

        circlePepositoryUseCase.saveToDataBase(circlePetrolPriceResponse);
    }
}
