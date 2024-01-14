package bpp.service.lv.statistic;

import bpp.entity.NestePriceEntity;
import bpp.model.ChartCategoryModel;
import bpp.repository.NestePriceRepository;
import bpp.service.chart.PetrolChart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NesteStatisticService {
    private static final String PETROL_CATEGORY_NAME = "Petrol";
    private static final String PETROL_PRO_CATEGORY_NAME = "Petrol Pro";
    private static final String DIESEL_CATEGORY_NAME = "Diesel";
    private static final String DIESEL_PRO_CATEGORY_NAME = "Diesel Pro";
    private final NestePriceRepository nestePriceRepository;
    private final PetrolChart petrolChart;

    public byte[] getWeeklyStatisticChart() {
        LocalDateTime localDateTime = LocalDateTime.now();

        List<NestePriceEntity> nestePriceEntitiesWeeklyList = nestePriceRepository
                .findTop5ByCreatedDateBeforeOrderByCreatedDateAsc(localDateTime);

        ChartCategoryModel petrolCategory = getPetrolCategoryModel(nestePriceEntitiesWeeklyList);

        ChartCategoryModel petrolProCategory = getPetrolProCategoryModel(nestePriceEntitiesWeeklyList);

        ChartCategoryModel dieselCategory = getDieselCategoryModel(nestePriceEntitiesWeeklyList);

        ChartCategoryModel dieseProlCategory = getDieselProCategoryModel(nestePriceEntitiesWeeklyList);

        List<String> dateList = getDatesList(nestePriceEntitiesWeeklyList);

        List<ChartCategoryModel> chartCategoryModelList = List.of(petrolCategory, petrolProCategory, dieselCategory,
                dieseProlCategory);

        return petrolChart.createPetrolChart(chartCategoryModelList, dateList);
    }

    public List<NestePriceEntity> getWeeklyData() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return nestePriceRepository
                .findTop5ByCreatedDateBeforeOrderByCreatedDateAsc(localDateTime);
    }

    private ChartCategoryModel getPetrolCategoryModel(List<NestePriceEntity> nestePriceEntitiesWeeklyList) {
        List<Number> petrolPriceList = nestePriceEntitiesWeeklyList
                .stream()
                .map(NestePriceEntity::getPetrol)
                .collect(Collectors.toList());

        return ChartCategoryModel
                .builder()
                .categoryValue(petrolPriceList)
                .categoryName(PETROL_CATEGORY_NAME)
                .build();
    }

    private ChartCategoryModel getPetrolProCategoryModel(List<NestePriceEntity> nestePriceEntitiesWeeklyList) {
        List<Number> petrolProPriceList = nestePriceEntitiesWeeklyList
                .stream()
                .map(NestePriceEntity::getPetrolPro)
                .collect(Collectors.toList());

        return ChartCategoryModel
                .builder()
                .categoryValue(petrolProPriceList)
                .categoryName(PETROL_PRO_CATEGORY_NAME)
                .build();
    }

    private ChartCategoryModel getDieselCategoryModel(List<NestePriceEntity> nestePriceEntitiesWeeklyList) {
        List<Number> dieselPriceList = nestePriceEntitiesWeeklyList
                .stream()
                .map(NestePriceEntity::getDiesel)
                .collect(Collectors.toList());

        return ChartCategoryModel
                .builder()
                .categoryValue(dieselPriceList)
                .categoryName(DIESEL_CATEGORY_NAME)
                .build();
    }

    private ChartCategoryModel getDieselProCategoryModel(List<NestePriceEntity> nestePriceEntitiesWeeklyList) {
        List<Number> dieselProPriceList = nestePriceEntitiesWeeklyList
                .stream()
                .map(NestePriceEntity::getDieselPro)
                .collect(Collectors.toList());

        return ChartCategoryModel
                .builder()
                .categoryValue(dieselProPriceList)
                .categoryName(DIESEL_PRO_CATEGORY_NAME)
                .build();
    }

    private List<String> getDatesList(List<NestePriceEntity> nestePriceEntitiesWeeklyList) {
        return nestePriceEntitiesWeeklyList
                .stream()
                .map(i -> getDate(i.getCreatedDate()))
                .collect(Collectors.toList());
    }

    private String getDate(LocalDateTime localDateTime) {
        return String.format("%s.%s", localDateTime.getDayOfMonth(), localDateTime.getMonth().getValue());
    }
}
