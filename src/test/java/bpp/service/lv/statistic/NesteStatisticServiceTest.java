package bpp.service.lv.statistic;

import bpp.entity.NestePriceEntity;
import bpp.repository.NestePriceRepository;
import bpp.service.chart.PetrolChart;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(value = {NesteStatisticService.class, PetrolChart.class})
@ExtendWith(SpringExtension.class)
class NesteStatisticServiceTest {
    @Autowired
    private NestePriceRepository nestePriceRepository;
    @Autowired
    private NesteStatisticService nesteStatisticService;

    @Test
    void noData_MustReturn_EmptyArray() {
        // Assume, Act
        byte[] result = nesteStatisticService.getWeeklyStatisticChart();

        // Assert
        assertThat(result).isEmpty();
    }

    @Test
    void createdChartAsByteArray_ReturnArray() {
        // Assume
        NestePriceEntity nestePriceEntity = NestePriceEntity.builder().build();
        nestePriceRepository.save(nestePriceEntity);

        // Act
        byte[] result = nesteStatisticService.getWeeklyStatisticChart();

        // Assert
        assertThat(result).isNotEmpty();
    }
}
