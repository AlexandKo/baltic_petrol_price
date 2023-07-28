package bpp.service.lv.statistic;

import bpp.entity.ViadaPriceEntity;
import bpp.model.ChartCategoryModel;
import bpp.repository.ViadaPriceRepository;
import bpp.service.chart.PetrolChart;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ViadaStatisticService {
    private static final String PETROL_ECTO_CATEGORY_NAME = "Petrol Ecto";
    private static final String PETROL_ECTO_PLUS_CATEGORY_NAME = "Petrol Ecto Plus";
    private static final String PETROL_PRO_CATEGORY_NAME = "Petrol Pro";
    private static final String DIESEL_CATEGORY_NAME = "Diesel";
    private static final String DIESEL_ECTO_CATEGORY_NAME = "Diesel Ecto";
    private static final String GAS_CATEGORY_NAME = "Gas";
    private static final String PETROL_ECO_CATEGORY_NAME = "Petrol Eco";
    private final ViadaPriceRepository viadaPriceRepository;
    private final PetrolChart petrolChart;

    public byte[] getWeeklyStatisticChart() {
        LocalDateTime localDateTime = LocalDateTime.now();

        List<ViadaPriceEntity> viadaPriceEntitiesWeeklyList = viadaPriceRepository
                .findTop5ByCreatedDateBeforeOrderByCreatedDateAsc(localDateTime);

        ChartCategoryModel petrolEctoCategory = getPetrolEctoCategoryModel(viadaPriceEntitiesWeeklyList);

        ChartCategoryModel petrolEctoPlusCategory = getPetrolEctoPLusCategoryModel(viadaPriceEntitiesWeeklyList);

        ChartCategoryModel petrolProCategory = getPetrolProCategoryModel(viadaPriceEntitiesWeeklyList);

        ChartCategoryModel dieselCategory = getDieselCategoryModel(viadaPriceEntitiesWeeklyList);

        ChartCategoryModel dieselEctoCategory = getDieselEctoCategoryModel(viadaPriceEntitiesWeeklyList);

        ChartCategoryModel gasCategory = getGasCategoryModel(viadaPriceEntitiesWeeklyList);

        ChartCategoryModel petrolEcoCategory = getPetrolEcoCategoryModel(viadaPriceEntitiesWeeklyList);

        List<String> dateList = getDatesList(viadaPriceEntitiesWeeklyList);

        List<ChartCategoryModel> chartCategoryModelList = List.of(petrolEctoCategory, petrolEctoPlusCategory, petrolProCategory,
                dieselCategory, dieselEctoCategory, gasCategory, petrolEcoCategory);

        return petrolChart.createPetrolChart(chartCategoryModelList, dateList);
    }

    private ChartCategoryModel getPetrolEctoCategoryModel(List<ViadaPriceEntity> viadaPriceEntitiesWeeklyList) {
        List<Number> petrolPriceList = viadaPriceEntitiesWeeklyList
                .stream()
                .map(ViadaPriceEntity::getPetrolEcto)
                .collect(Collectors.toList());

        return ChartCategoryModel
                .builder()
                .categoryValue(petrolPriceList)
                .categoryName(PETROL_ECTO_CATEGORY_NAME)
                .build();
    }

    private ChartCategoryModel getPetrolEctoPLusCategoryModel(List<ViadaPriceEntity> viadaPriceEntitiesWeeklyList) {
        List<Number> petrolProPriceList = viadaPriceEntitiesWeeklyList
                .stream()
                .map(ViadaPriceEntity::getPetrolEctoPlus)
                .collect(Collectors.toList());

        return ChartCategoryModel
                .builder()
                .categoryValue(petrolProPriceList)
                .categoryName(PETROL_ECTO_PLUS_CATEGORY_NAME)
                .build();
    }

    private ChartCategoryModel getPetrolProCategoryModel(List<ViadaPriceEntity> nestePriceEntitiesWeeklyList) {
        List<Number> petrolProPriceList = nestePriceEntitiesWeeklyList
                .stream()
                .map(ViadaPriceEntity::getPetrolPro)
                .collect(Collectors.toList());

        return ChartCategoryModel
                .builder()
                .categoryValue(petrolProPriceList)
                .categoryName(PETROL_PRO_CATEGORY_NAME)
                .build();
    }

    private ChartCategoryModel getDieselCategoryModel(List<ViadaPriceEntity> viadaPriceEntitiesWeeklyList) {
        List<Number> dieselPriceList = viadaPriceEntitiesWeeklyList
                .stream()
                .map(ViadaPriceEntity::getDiesel)
                .collect(Collectors.toList());

        return ChartCategoryModel
                .builder()
                .categoryValue(dieselPriceList)
                .categoryName(DIESEL_CATEGORY_NAME)
                .build();
    }

    private ChartCategoryModel getDieselEctoCategoryModel(List<ViadaPriceEntity> viadaPriceEntitiesWeeklyList) {
        List<Number> dieselProPriceList = viadaPriceEntitiesWeeklyList
                .stream()
                .map(ViadaPriceEntity::getDieselEcto)
                .collect(Collectors.toList());

        return ChartCategoryModel
                .builder()
                .categoryValue(dieselProPriceList)
                .categoryName(DIESEL_ECTO_CATEGORY_NAME)
                .build();
    }

    private ChartCategoryModel getGasCategoryModel(List<ViadaPriceEntity> viadaPriceEntitiesWeeklyList) {
        List<Number> dieselProPriceList = viadaPriceEntitiesWeeklyList
                .stream()
                .map(ViadaPriceEntity::getGas)
                .collect(Collectors.toList());

        return ChartCategoryModel
                .builder()
                .categoryValue(dieselProPriceList)
                .categoryName(GAS_CATEGORY_NAME)
                .build();
    }

    private ChartCategoryModel getPetrolEcoCategoryModel(List<ViadaPriceEntity> viadaPriceEntitiesWeeklyList) {
        List<Number> dieselProPriceList = viadaPriceEntitiesWeeklyList
                .stream()
                .map(ViadaPriceEntity::getPetrolEco)
                .collect(Collectors.toList());

        return ChartCategoryModel
                .builder()
                .categoryValue(dieselProPriceList)
                .categoryName(PETROL_ECO_CATEGORY_NAME)
                .build();
    }

    private List<String> getDatesList(List<ViadaPriceEntity> viadaPriceEntitiesWeeklyList) {
        return viadaPriceEntitiesWeeklyList
                .stream()
                .map(i -> getDate(i.getCreatedDate()))
                .collect(Collectors.toList());
    }

    private String getDate(LocalDateTime localDateTime) {
        return String.format("%s.%s", localDateTime.getDayOfMonth(), localDateTime.getMonth().getValue());
    }
}
