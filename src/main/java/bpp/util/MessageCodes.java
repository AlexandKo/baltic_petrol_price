package bpp.util;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageCodes {
    public static final int WEB_CLIENT_CONNECTION_SUCCESSFULLY = 200;
    public static final int WEB_CLIENT_CONNECTION_FAILED = 404;
}
