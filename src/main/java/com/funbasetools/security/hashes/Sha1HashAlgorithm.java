package com.funbasetools.security.hashes;

import com.funbasetools.ShouldNotReachThisPointException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class Sha1HashAlgorithm extends HashAlgorithmDigestBase {

    @Override
    protected MessageDigest getMessageDigest() {
        try {
            return MessageDigest.getInstance("SHA-1");
        }
        catch (NoSuchAlgorithmException ex){
            throw new ShouldNotReachThisPointException(ex);
        }
    }
}
