package bpp.service.lv.statistic;

import bpp.entity.GotikaPriceEntity;
import bpp.model.ChartCategoryModel;
import bpp.repository.GotikaPriceRepository;
import bpp.service.chart.PetrolChart;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GotikaStatisticService {
    private static final String PETROL_CATEGORY_NAME = "Petrol";
    private static final String DIESEL_CATEGORY_NAME = "Diesel";
    private final GotikaPriceRepository gotikaPriceRepository;
    private final PetrolChart petrolChart;

    public byte[] getWeeklyStatisticChart() {
        LocalDateTime localDateTime = LocalDateTime.now();

        List<GotikaPriceEntity> gotikaPriceEntitiesWeeklyList = gotikaPriceRepository
                .findTop5ByCreatedDateBeforeOrderByCreatedDateDesc(localDateTime);

        ChartCategoryModel petrolCategory = getPetrolCategoryModel(gotikaPriceEntitiesWeeklyList);

        ChartCategoryModel dieselCategory = getDieselCategoryModel(gotikaPriceEntitiesWeeklyList);

        List<String> dateList = getDatesList(gotikaPriceEntitiesWeeklyList);

        List<ChartCategoryModel> chartCategoryModelList = List.of(petrolCategory, dieselCategory);

        return petrolChart.createPetrolChart(chartCategoryModelList, dateList);
    }

    private ChartCategoryModel getPetrolCategoryModel(List<GotikaPriceEntity> gotikaPriceEntitiesWeeklyList) {
        List<Number> petrolPriceList = gotikaPriceEntitiesWeeklyList
                .stream()
                .map(GotikaPriceEntity::getPetrol)
                .collect(Collectors.toList());

        return ChartCategoryModel
                .builder()
                .categoryValue(petrolPriceList)
                .categoryName(PETROL_CATEGORY_NAME)
                .build();
    }

    private ChartCategoryModel getDieselCategoryModel(List<GotikaPriceEntity> gotikaPriceEntitiesWeeklyList) {
        List<Number> dieselPriceList = gotikaPriceEntitiesWeeklyList
                .stream()
                .map(GotikaPriceEntity::getDiesel)
                .collect(Collectors.toList());

        return ChartCategoryModel
                .builder()
                .categoryValue(dieselPriceList)
                .categoryName(DIESEL_CATEGORY_NAME)
                .build();
    }

    private List<String> getDatesList(List<GotikaPriceEntity> gotikaPriceEntitiesWeeklyList) {
        return gotikaPriceEntitiesWeeklyList
                .stream()
                .map(i -> getDate(i.getCreatedDate()))
                .collect(Collectors.toList());
    }

    private String getDate(LocalDateTime localDateTime) {
        return String.format("%s.%s", localDateTime.getDayOfMonth(), localDateTime.getMonth().getValue());
    }
}
