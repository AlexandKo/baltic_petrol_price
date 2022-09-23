package bpp.model;

import lombok.Getter;

@Getter
public class Response<T> {
    private final T responseModel;

    public Response(T responseModel) {
        this.responseModel = responseModel;
    }
}
