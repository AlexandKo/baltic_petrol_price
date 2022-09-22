package bpp.service.lv;

import bpp.entity.ViadaPriceEntity;
import bpp.infrastructure.ContentWebClient;
import bpp.infrastructure.lv.ViadaContentWebClient;
import bpp.mapper.ViadaPriceMapper;
import bpp.model.PetrolPriceModel;
import bpp.repository.ViadaPriceRepository;
import bpp.service.PetrolPriceService;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static bpp.util.MessageCodes.WEB_CLIENT_CONNECTION_SUCCESSFULLY;
import static bpp.util.Messages.NEW_RECORD_ERROR;
import static bpp.util.Messages.NEW_RECORD_INFO;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ViadaPetrolPriceService implements PetrolPriceService {
    private static final String PETROL_STATION = "Viada";
    private final List<ContentWebClient> contentWebClients;
    private final ViadaPriceRepository viadaPriceRepository;

    @Override
    public void savePetrolPrice() {
        Optional<PetrolPriceModel> viadaPetrolPriceModelOptional = contentWebClients
                .stream()
                .filter(ViadaContentWebClient.class::isInstance)
                .map(ContentWebClient::getContent).findFirst();

        viadaPetrolPriceModelOptional.ifPresent(viadaPetrolPriceModel -> {
            if (viadaPetrolPriceModel.getId() == WEB_CLIENT_CONNECTION_SUCCESSFULLY) {
                ViadaPriceEntity viadaPriceEntity = ViadaPriceMapper.toViadaPriceEntity(viadaPetrolPriceModel);
                viadaPriceRepository.save(viadaPriceEntity);
                log.info(String.format(NEW_RECORD_INFO, PETROL_STATION, viadaPriceEntity.getCountry()));
            } else {
                log.error(String.format(NEW_RECORD_ERROR, PETROL_STATION, viadaPetrolPriceModel.getCountry()));
            }
        });
    }
}
