package bpp.service.lv;

import bpp.entity.ViadaPriceEntity;
import bpp.infrastructure.ContentWebClient;
import bpp.infrastructure.lv.ViadaContentWebClient;
import bpp.mapper.CirclePriceMapper;
import bpp.mapper.ViadaPriceMapper;
import bpp.model.PetrolPriceModel;
import bpp.repository.ViadaPriceRepository;
import bpp.service.PetrolPriceService;
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
public class ViadaPetrolPriceService implements PetrolPriceService {
    private final List<ContentWebClient> contentWebClients;
    private final ViadaPriceRepository viadaPriceRepository;

    @Override
    public void savePetrolPrice() {
        PetrolPriceModel viadaPetrolPriceModel = contentWebClients
                .stream()
                .filter(ViadaContentWebClient.class::isInstance)
                .map(ContentWebClient::getContent).findFirst()
                .orElse(null);

        if (viadaPetrolPriceModel != null) {
            ViadaPriceEntity viadaPriceEntity = ViadaPriceMapper.toViadaPriceEntity(viadaPetrolPriceModel);
            viadaPriceRepository.save(viadaPriceEntity);
            log.info(String.format(NEW_RECORD_INFO, "Viada", viadaPriceEntity.getCountry()));
        }
    }
}
