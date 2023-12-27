package bpp.util;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Messages {
    public static final String PRICE_FOR_ALL_STATIONS = "The fuel price is set for all gas stations";
    public static final String CONNECTION_ERROR = "Error during connect to resource %s";
    public static final String NEW_RECORD_INFO = "Added new record: %s petrol station, country %s";
    public static final String NOT_FOUND_ERROR = "No access to %s web site, country %s";
    public static final String SERVICE_NOT_FOUND_ERROR = "Service %s not found";
    public static final String PARSING_DATA_ERROR = "Error parsing data for %s station";
}
