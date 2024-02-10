package bpp.service.lv;

import bpp.infrastructure.lv.CircleContentWebClient;
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
public class LvCirclePetrolPriceService implements PetrolPriceService {
    private final CircleRepositoryUseCase circleRepositoryUseCase;
    private final CircleContentWebClient contentWebClients;

    @Override
    public void savePetrolPrice() {
        Response<?> circlePetrolPriceResponse = contentWebClients.getContent();

        circleRepositoryUseCase.saveToDataBase(circlePetrolPriceResponse);
    }
}
