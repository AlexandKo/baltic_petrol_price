package bpp.controller.lv;

import bpp.controller.StatisticsBase;
import bpp.entity.GotikaPriceEntity;
import bpp.entity.NestePriceEntity;
import bpp.entity.ViadaPriceEntity;
import bpp.entity.VirsiPriceEntity;
import bpp.model.ErrorModel;
import bpp.model.StationStatisticModel;
import bpp.service.lv.statistic.GotikaStatisticService;
import bpp.service.lv.statistic.NesteStatisticService;
import bpp.service.lv.statistic.ViadaStatisticService;
import bpp.service.lv.statistic.VirsiStatisticService;
import bpp.service.statistics.CircleStatisticService;
import bpp.util.Country;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/lv/petrol/statistic")
@RequiredArgsConstructor
public class LvStatisticController extends StatisticsBase {
    private final NesteStatisticService nesteStatistic;
    private final GotikaStatisticService gotikaStatisticService;
    private final VirsiStatisticService virsiStatisticService;
    private final CircleStatisticService circleStatisticService;
    private final ViadaStatisticService viadaStatisticService;

    @GetMapping("/neste")
    @Operation(description = "Return weekly statistics for Neste Gas Station Latvia", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return statistic chart png format", content = {@Content(mediaType = MediaType.IMAGE_PNG_VALUE)}),
            @ApiResponse(responseCode = "404", description = "Not found data for statistic", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorModel.class))})
    })
    public ResponseEntity<Object> getNesteStatistic() {
        return createStatisticsResponse(nesteStatistic.getWeeklyStatisticChart());
    }

    @GetMapping("/neste/weekly")
    @Operation(description = "Return weekly data for Neste Gas Station Latvia", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return weekly data", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", description = "Not found data for statistic", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorModel.class))})
    })
    public ResponseEntity<Object> getNesteWeeklyData() {
        List<NestePriceEntity> nesteStatisticWeeklyData = nesteStatistic.getWeeklyData();
        StationStatisticModel<List<NestePriceEntity>> stationStatisticModel = new StationStatisticModel<>(200, nesteStatisticWeeklyData);

        return ResponseEntity.ok().body(stationStatisticModel);
    }

    @GetMapping("/gotika")
    @Operation(description = "Return weekly statistics for Gotika Gas Station Latvia", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return statistic chart png format", content = {@Content(mediaType = MediaType.IMAGE_PNG_VALUE)}),
            @ApiResponse(responseCode = "404", description = "Not found data for statistic", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorModel.class))})
    })
    public ResponseEntity<Object> getGotikaStatistic() {
        return createStatisticsResponse(gotikaStatisticService.getWeeklyStatisticChart());
    }

    @GetMapping("/gotika/weekly")
    @Operation(description = "Return weekly data for Gotika Gas Station Latvia", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return weekly data", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", description = "Not found data for statistic", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorModel.class))})
    })
    public ResponseEntity<Object> getGotikaWeeklyData() {
        List<GotikaPriceEntity> gotikaStatisticWeeklyData = gotikaStatisticService.getWeeklyData();

        StationStatisticModel<List<GotikaPriceEntity>> stationStatisticModel = new StationStatisticModel<>(200, gotikaStatisticWeeklyData);

        return ResponseEntity.ok().body(stationStatisticModel);
    }

    @GetMapping("/virsi")
    @Operation(description = "Return weekly statistics for Virshi Gas Station Latvia", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return statistic chart png format", content = {@Content(mediaType = MediaType.IMAGE_PNG_VALUE)}),
            @ApiResponse(responseCode = "404", description = "Not found data for statistic", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorModel.class))})
    })
    public ResponseEntity<Object> getVirsiStatistic() {
        return createStatisticsResponse(virsiStatisticService.getWeeklyStatisticChart());
    }

    @GetMapping("/virsi/weekly")
    @Operation(description = "Return weekly data for Virshi Gas Station Latvia", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return weekly data", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", description = "Not found data for statistic", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorModel.class))})
    })
    public ResponseEntity<Object> virsiStatisticWeeklyData() {
        List<VirsiPriceEntity> virsiStatisticWeeklyData = virsiStatisticService.getWeeklyData();

        StationStatisticModel<List<VirsiPriceEntity>> stationStatisticModel = new StationStatisticModel<>(200, virsiStatisticWeeklyData);

        return ResponseEntity.ok().body(stationStatisticModel);
    }

    @GetMapping("/circlek")
    @Operation(description = "Return weekly statistics for CircleK Gas Station Latvia", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return statistic chart png format", content = {@Content(mediaType = MediaType.IMAGE_PNG_VALUE)}),
            @ApiResponse(responseCode = "404", description = "Not found data for statistic", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorModel.class))})
    })
    public ResponseEntity<Object> getCircleKStatistic() {
        return createStatisticsResponse(circleStatisticService.getWeeklyStatisticChart(Country.LV));
    }

    @GetMapping("/viada")
    @Operation(description = "Return weekly statistics for Viada Gas Station Latvia", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return statistic chart png format", content = {@Content(mediaType = MediaType.IMAGE_PNG_VALUE)}),
            @ApiResponse(responseCode = "404", description = "Not found data for statistic", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorModel.class))})
    })
    public ResponseEntity<Object> getViadaStatistic() {
        return createStatisticsResponse(viadaStatisticService.getWeeklyStatisticChart());
    }

    @GetMapping("/viada/weekly")
    @Operation(description = "Return weekly data for Viada Gas Station Latvia", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return weekly data", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", description = "Not found data for statistic", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorModel.class))})
    })
    public ResponseEntity<Object> viadaStatisticWeeklyData() {
        List<ViadaPriceEntity> viadaStatisticWeeklyData = viadaStatisticService.getWeeklyData();

        StationStatisticModel<List<ViadaPriceEntity>> stationStatisticModel = new StationStatisticModel<>(200, viadaStatisticWeeklyData);

        return ResponseEntity.ok().body(stationStatisticModel);
    }
}
