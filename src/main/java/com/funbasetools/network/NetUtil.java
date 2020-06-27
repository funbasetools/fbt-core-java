package com.funbasetools.network;

import com.funbasetools.Lazy;
import com.funbasetools.Try;

import java.net.InetAddress;

public final class NetUtil {

    private static final Lazy<Try<byte[]>> addressBytes = Lazy.of(
        () -> Try.of(() -> InetAddress.getLocalHost().getAddress())
    );

    public static Try<byte[]> tryGetAddressBytes() {
        return addressBytes.get();
    }

    public static byte[] getAddressBytes() {
        return tryGetAddressBytes()
            .toOptional()
            .orElse(new byte[4]);
    }

    private NetUtil() {
    }
}
