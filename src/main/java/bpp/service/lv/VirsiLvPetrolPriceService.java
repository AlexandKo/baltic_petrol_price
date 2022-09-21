package bpp.service.lv;

import bpp.entity.NestePriceEntity;
import bpp.entity.VirsiPriceEntity;
import bpp.infrastructure.ContentWebClient;
import bpp.infrastructure.lv.NesteContentWebClient;
import bpp.infrastructure.lv.VirsiContentWebClient;
import bpp.mapper.NestePriceMapper;
import bpp.mapper.VirsiPriceMapper;
import bpp.model.PetrolPriceModel;
import bpp.repository.VirsiPriceRepository;
import bpp.service.LvPetrolPriceService;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static bpp.util.Messages.NEW_RECORD_INFO;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class VirsiLvPetrolPriceService implements LvPetrolPriceService {
    private final List<ContentWebClient> contentWebClients;
    private final VirsiPriceRepository virsiPriceRepository;

    @Override
    public void savePetrolPrice() {
        PetrolPriceModel virsiPetrolPriceModel = contentWebClients
                .stream()
                .filter(VirsiContentWebClient.class::isInstance)
                .map(ContentWebClient::getContent).findFirst()
                .orElse(null);

        if (virsiPetrolPriceModel != null) {
            VirsiPriceEntity virsiPriceEntity = VirsiPriceMapper.toVirsiPriceEntity(virsiPetrolPriceModel);
            virsiPriceRepository.save(virsiPriceEntity);
            log.info(String.format(NEW_RECORD_INFO, "Virsi", virsiPriceEntity.getCountry()));
        }
    }
}
