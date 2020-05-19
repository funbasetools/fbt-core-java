package com.funbasetools.security.hashes;

import com.funbasetools.ShouldNotReachThisPointException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class Sha256HashAlgorithm extends HashAlgorithmDigestBase {

    @Override
    protected MessageDigest getMessageDigest() {
        try {
            return MessageDigest.getInstance("SHA-256");
        }
        catch (NoSuchAlgorithmException ex){
            throw new ShouldNotReachThisPointException(ex);
        }
    }
}
