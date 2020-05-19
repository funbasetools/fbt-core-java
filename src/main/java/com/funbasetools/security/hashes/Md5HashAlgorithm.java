package com.funbasetools.security.hashes;

import com.funbasetools.ShouldNotReachThisPointException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5HashAlgorithm extends HashAlgorithmDigestBase {

    @Override
    protected MessageDigest getMessageDigest() {
        try {
            return MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException ex){
            throw new ShouldNotReachThisPointException(ex);
        }
    }
}
