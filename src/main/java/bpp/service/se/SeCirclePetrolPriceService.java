package bpp.service.se;

import bpp.infrastructure.se.SeCircleContentWebClient;
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
public class SeCirclePetrolPriceService implements PetrolPriceService {
    private final CircleRepositoryUseCase circleRepositoryUseCase;
    private final SeCircleContentWebClient contentWebClients;

    @Override
    public void savePetrolPrice() {
        Response<?> circlePetrolPriceResponse = contentWebClients.getContent();

        circleRepositoryUseCase.saveToDataBase(circlePetrolPriceResponse);
    }
}