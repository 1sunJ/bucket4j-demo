package com.example.bucket4j_demo.common.authentication;

public class AuthenticationContext {

    private static final ThreadLocal<String> authenticationHolder = new ThreadLocal<>();

    public static void setMemberId(String requestId) {
        authenticationHolder.set(requestId);
    }

    public static String getMemberId() {
        return authenticationHolder.get();
    }

    public static void clear() {
        authenticationHolder.remove();
    }

}
