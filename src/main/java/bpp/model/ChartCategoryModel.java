package bpp.model;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ChartCategoryModel {
    private List<Number> categoryValue;
    private String categoryName;
}
