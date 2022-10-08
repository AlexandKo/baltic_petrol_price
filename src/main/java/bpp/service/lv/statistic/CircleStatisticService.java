package bpp.service.lv.statistic;

import bpp.entity.CirclePriceEntity;
import bpp.model.ChartCategoryModel;
import bpp.repository.CirclePriceRepository;
import bpp.service.chart.PetrolChart;
import bpp.util.Country;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CircleStatisticService {
    private static final String PETROL_CATEGORY_NAME = "Petrol";
    private static final String PETROL_PRO_CATEGORY_NAME = "Petrol Pro";
    private static final String DIESEL_CATEGORY_NAME = "Diesel";
    private static final String DIESEL_PRO_CATEGORY_NAME = "Diesel Pro";
    private static final String GAS_CATEGORY_NAME = "Gas";
    private final CirclePriceRepository circlePriceRepository;
    private final PetrolChart petrolChart;

    public byte[] getWeeklyStatisticChart(Country country) {
        LocalDateTime localDateTime = LocalDateTime.now();

        List<CirclePriceEntity> circlePriceEntitiesWeeklyList = circlePriceRepository
                .findTop5ByCreatedDateBeforeAndCountryOrderByCreatedDateDesc(localDateTime, country);

        ChartCategoryModel petrolCategory = getPetrolCategoryModel(circlePriceEntitiesWeeklyList);

        ChartCategoryModel petrolProCategory = getPetrolProCategoryModel(circlePriceEntitiesWeeklyList);

        ChartCategoryModel dieselCategory = getDieselCategoryModel(circlePriceEntitiesWeeklyList);

        ChartCategoryModel dieseProlCategory = getDieselProCategoryModel(circlePriceEntitiesWeeklyList);

        ChartCategoryModel gasCategory = getGasCategoryModel(circlePriceEntitiesWeeklyList);

        List<String> dateList = getDatesList(circlePriceEntitiesWeeklyList);

        List<ChartCategoryModel> chartCategoryModelList = List.of(petrolCategory, petrolProCategory, dieselCategory,
                dieseProlCategory, gasCategory);

        return petrolChart.createPetrolChart(chartCategoryModelList, dateList);
    }

    private ChartCategoryModel getPetrolCategoryModel(List<CirclePriceEntity> circlePriceEntitiesWeeklyList) {
        List<Number> petrolPriceList = circlePriceEntitiesWeeklyList
                .stream()
                .map(CirclePriceEntity::getPetrol)
                .collect(Collectors.toList());

        return ChartCategoryModel
                .builder()
                .categoryValue(petrolPriceList)
                .categoryName(PETROL_CATEGORY_NAME)
                .build();
    }

    private ChartCategoryModel getPetrolProCategoryModel(List<CirclePriceEntity> circlePriceEntitiesWeeklyList) {
        List<Number> petrolProPriceList = circlePriceEntitiesWeeklyList
                .stream()
                .map(CirclePriceEntity::getPetrolPro)
                .collect(Collectors.toList());

        return ChartCategoryModel
                .builder()
                .categoryValue(petrolProPriceList)
                .categoryName(PETROL_PRO_CATEGORY_NAME)
                .build();
    }

    private ChartCategoryModel getDieselCategoryModel(List<CirclePriceEntity> circlePriceEntitiesWeeklyList) {
        List<Number> dieselPriceList = circlePriceEntitiesWeeklyList
                .stream()
                .map(CirclePriceEntity::getDiesel)
                .collect(Collectors.toList());

        return ChartCategoryModel
                .builder()
                .categoryValue(dieselPriceList)
                .categoryName(DIESEL_CATEGORY_NAME)
                .build();
    }

    private ChartCategoryModel getDieselProCategoryModel(List<CirclePriceEntity> circlePriceEntitiesWeeklyList) {
        List<Number> dieselProPriceList = circlePriceEntitiesWeeklyList
                .stream()
                .map(CirclePriceEntity::getDieselPro)
                .collect(Collectors.toList());

        return ChartCategoryModel
                .builder()
                .categoryValue(dieselProPriceList)
                .categoryName(DIESEL_PRO_CATEGORY_NAME)
                .build();
    }

    private ChartCategoryModel getGasCategoryModel(List<CirclePriceEntity> circlePriceEntitiesWeeklyList) {
        List<Number> dieselProPriceList = circlePriceEntitiesWeeklyList
                .stream()
                .map(CirclePriceEntity::getGas)
                .collect(Collectors.toList());

        return ChartCategoryModel
                .builder()
                .categoryValue(dieselProPriceList)
                .categoryName(GAS_CATEGORY_NAME)
                .build();
    }

    private List<String> getDatesList(List<CirclePriceEntity> circlePriceEntitiesWeeklyList) {
        return circlePriceEntitiesWeeklyList
                .stream()
                .map(i -> getDate(i.getCreatedDate()))
                .collect(Collectors.toList());
    }

    private String getDate(LocalDateTime localDateTime) {
        return String.format("%s.%s", localDateTime.getDayOfMonth(), localDateTime.getMonth().getValue());
    }
}
