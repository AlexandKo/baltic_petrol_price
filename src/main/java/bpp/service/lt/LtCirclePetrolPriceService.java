package bpp.service.lt;

import bpp.infrastructure.lt.LtCircleContentWebClient;
import bpp.model.Response;
import bpp.service.PetrolPriceService;
import bpp.usecase.CircleRepositoryUseCase;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class LtCirclePetrolPriceService implements PetrolPriceService {
    private final CircleRepositoryUseCase circleRepositoryUseCase;
    private final LtCircleContentWebClient contentWebClients;

    @Override
    public void savePetrolPrice() {
        Response<?> circlePetrolPriceResponse = contentWebClients.getContent();

        circleRepositoryUseCase.saveToDataBase(circlePetrolPriceResponse);
    }
}
