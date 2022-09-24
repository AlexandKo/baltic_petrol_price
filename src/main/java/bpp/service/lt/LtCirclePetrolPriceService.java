package bpp.service.lt;

import bpp.infrastructure.ContentWebClient;
import bpp.infrastructure.lt.LtCircleContentWebClient;
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
public class LtCirclePetrolPriceService implements PetrolPriceService {
    private final CirclePepositoryUseCase circlePepositoryUseCase;
    private final List<ContentWebClient<?>> contentWebClients;

    @Override
    public void savePetrolPrice() {
        Response<?> circlePetrolPriceResponse = contentWebClients
                .stream()
                .filter(LtCircleContentWebClient.class::isInstance)
                .map(LtCircleContentWebClient.class::cast)
                .map(LtCircleContentWebClient::getContent)
                .findFirst()
                .orElse(null);

        circlePepositoryUseCase.saveToDataBase(circlePetrolPriceResponse);
    }
}
