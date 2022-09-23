package bpp.service;

import bpp.model.Response;

public interface PetrolPriceService {
    default boolean isResponseNull(Response<?> response) {
        return response == null;
    }

    void savePetrolPrice();
}
