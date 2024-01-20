package bpp.service.lv.statistic;

import bpp.entity.VirsiPriceEntity;
import bpp.model.ChartCategoryModel;
import bpp.repository.VirsiPriceRepository;
import bpp.service.chart.PetrolChart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VirsiStatisticService {
    private static final String PETROL_CATEGORY_NAME = "Petrol";
    private static final String PETROL_PRO_CATEGORY_NAME = "Petrol Pro";
    private static final String DIESEL_CATEGORY_NAME = "Diesel";
    private static final String GAS_CATEGORY_NAME = "Gas";
    private final VirsiPriceRepository virsiPriceRepository;
    private final PetrolChart petrolChart;

    public byte[] getWeeklyStatisticChart() {
        LocalDateTime localDateTime = LocalDateTime.now();

        List<VirsiPriceEntity> virsiPriceEntitiesWeeklyList = virsiPriceRepository
                .findTop5ByCreatedDateBeforeOrderByCreatedDateAsc(localDateTime);

        ChartCategoryModel petrolCategory = getPetrolCategoryModel(virsiPriceEntitiesWeeklyList);

        ChartCategoryModel petrolProCategory = getPetrolProCategoryModel(virsiPriceEntitiesWeeklyList);

        ChartCategoryModel dieselCategory = getDieselCategoryModel(virsiPriceEntitiesWeeklyList);

        ChartCategoryModel dieseProlCategory = getGasCategoryModel(virsiPriceEntitiesWeeklyList);

        List<String> dateList = getDatesList(virsiPriceEntitiesWeeklyList);

        List<ChartCategoryModel> chartCategoryModelList = List.of(petrolCategory, petrolProCategory, dieselCategory,
                dieseProlCategory);

        return petrolChart.createPetrolChart(chartCategoryModelList, dateList);
    }

    public List<VirsiPriceEntity> getStatisticDataPerMonth() {
        return virsiPriceRepository
                .searchPrices(LocalDateTime.now(), LocalDateTime.now().minusDays(30));
    }

    private ChartCategoryModel getPetrolCategoryModel(List<VirsiPriceEntity> virsiPriceEntitiesWeeklyList) {
        List<Number> petrolPriceList = virsiPriceEntitiesWeeklyList
                .stream()
                .map(VirsiPriceEntity::getPetrol)
                .collect(Collectors.toList());

        return ChartCategoryModel
                .builder()
                .categoryValue(petrolPriceList)
                .categoryName(PETROL_CATEGORY_NAME)
                .build();
    }

    private ChartCategoryModel getPetrolProCategoryModel(List<VirsiPriceEntity> virsiPriceEntitiesWeeklyList) {
        List<Number> petrolProPriceList = virsiPriceEntitiesWeeklyList
                .stream()
                .map(VirsiPriceEntity::getPetrolPro)
                .collect(Collectors.toList());

        return ChartCategoryModel
                .builder()
                .categoryValue(petrolProPriceList)
                .categoryName(PETROL_PRO_CATEGORY_NAME)
                .build();
    }

    private ChartCategoryModel getDieselCategoryModel(List<VirsiPriceEntity> virsiPriceEntitiesWeeklyList) {
        List<Number> dieselPriceList = virsiPriceEntitiesWeeklyList
                .stream()
                .map(VirsiPriceEntity::getDiesel)
                .collect(Collectors.toList());

        return ChartCategoryModel
                .builder()
                .categoryValue(dieselPriceList)
                .categoryName(DIESEL_CATEGORY_NAME)
                .build();
    }

    private ChartCategoryModel getGasCategoryModel(List<VirsiPriceEntity> nestePriceEntitiesWeeklyList) {
        List<Number> dieselProPriceList = nestePriceEntitiesWeeklyList
                .stream()
                .map(VirsiPriceEntity::getGas)
                .collect(Collectors.toList());

        return ChartCategoryModel
                .builder()
                .categoryValue(dieselProPriceList)
                .categoryName(GAS_CATEGORY_NAME)
                .build();
    }

    private List<String> getDatesList(List<VirsiPriceEntity> virsiPriceEntitiesWeeklyList) {
        return virsiPriceEntitiesWeeklyList
                .stream()
                .map(i -> getDate(i.getCreatedDate()))
                .collect(Collectors.toList());
    }

    private String getDate(LocalDateTime localDateTime) {
        return String.format("%s.%s", localDateTime.getDayOfMonth(), localDateTime.getMonth().getValue());
    }
}
