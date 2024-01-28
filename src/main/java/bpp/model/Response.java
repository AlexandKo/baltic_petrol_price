package bpp.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response<T> {
    private final T responseModel;

    public Response(T responseModel) {
        this.responseModel = responseModel;
    }
}
