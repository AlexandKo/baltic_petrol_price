package bpp.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class WebPageResponseModel {
    private int id;
    private String content;
}
