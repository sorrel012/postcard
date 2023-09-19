package com.postcard.toyou.service;

public enum SnsDisconEnum {
    KAKAO("카카오", "https://kapi.kakao.com/v1/user/unlink"),
    NAVER("네이버", "https://nid.naver.com/oauth2.0/token"),
    GOOGLE("구글", "https://www.googleapis.com/userinfo/v2/me");

    private final String name;
    private final String uri;

    SnsDisconEnum(String name, String uri) {
        this.name = name;
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public String getUri() {
        return uri;
    }

    public static SnsDisconEnum toUpper(String btnType) {
        try {
            return SnsDisconEnum.valueOf(btnType.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid btnType: " + btnType);
        }
    }

}
