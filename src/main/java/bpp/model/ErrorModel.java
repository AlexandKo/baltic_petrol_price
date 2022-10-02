package bpp.model;

import bpp.util.Country;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ErrorModel {
    @Schema(example = "404")
    private int id;
    @Schema(example = "LV")
    private Country country;
    @Schema(example = "Error description")
    private String errorMessage;
}
