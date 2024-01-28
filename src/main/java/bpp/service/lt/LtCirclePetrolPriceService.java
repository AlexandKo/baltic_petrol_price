package bpp.service.lt;

import bpp.infrastructure.ContentWebClient;
import bpp.infrastructure.lt.LtCircleContentWebClient;
import bpp.model.Response;
import bpp.service.PetrolPriceService;
import bpp.usecase.CirclePepositoryUseCase;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class LtCirclePetrolPriceService implements PetrolPriceService {
    private final CirclePepositoryUseCase circlePepositoryUseCase;
    private final LtCircleContentWebClient contentWebClients;

    @Override
    public void savePetrolPrice() {
        Response<?> circlePetrolPriceResponse = contentWebClients.getContent();

        circlePepositoryUseCase.saveToDataBase(circlePetrolPriceResponse);
    }
}
