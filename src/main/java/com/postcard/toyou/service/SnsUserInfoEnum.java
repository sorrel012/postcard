package com.postcard.toyou.service;

public enum SnsUserInfoEnum {
    KAKAO("카카오", "https://kapi.kakao.com/v2/user/me"),
    NAVER("네이버", "https://openapi.naver.com/v1/nid/me"),
    GOOGLE("구글", "https://www.googleapis.com/userinfo/v2/me");

    private final String name;
    private final String uri;

    SnsUserInfoEnum(String name, String uri) {
        this.name = name;
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public String getUri() {
        return uri;
    }

    public static SnsUserInfoEnum toUpperString(String btnType) {
        try {
            return SnsUserInfoEnum.valueOf(btnType.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid btnType: " + btnType);
        }
    }

}
