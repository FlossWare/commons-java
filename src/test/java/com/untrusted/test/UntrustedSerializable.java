package com.untrusted.test;

import java.io.Serializable;

/**
 * Test class in an untrusted package (not org.flossware.*, java.lang.*, java.util.*)
 * Used to test ObjectInputFilter REJECTED path in StringUtil.
 */
public class UntrustedSerializable implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String data;

    public UntrustedSerializable(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }
}
