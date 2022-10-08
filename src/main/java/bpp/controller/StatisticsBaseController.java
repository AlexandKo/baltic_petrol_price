package bpp.controller;

import bpp.model.ErrorModel;
import bpp.util.Country;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public abstract class StatisticsBaseController {
    private static final int EMPTY_ARRAY = 0;
    private static final int RESPONSE_CODE = 404;
    private static final String CREATE_CHART_CREATION_ERROR = "Error during created a Neste petrol station statistics";

    public ResponseEntity<Object> createStatisticsResponse(byte[] weeklyStatisticChart) {
        if (isStatisticChartEmpty(weeklyStatisticChart)) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ErrorModel.builder()
                            .id(RESPONSE_CODE)
                            .country(Country.LV)
                            .errorMessage(CREATE_CHART_CREATION_ERROR)
                            .build());
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.IMAGE_PNG)
                .body(weeklyStatisticChart);
    }

    private boolean isStatisticChartEmpty(byte[] weeklyStatisticChart) {
        return weeklyStatisticChart.length == EMPTY_ARRAY;
    }
}
