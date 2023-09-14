package com.postcard.toyou.service;

public enum SnsTokenEnum {
    KAKAO("카카오", "https://kauth.kakao.com/oauth/token"),
    NAVER("네이버", "https://nid.naver.com/oauth2.0/token"),
    GOOGLE("구글", "https://oauth2.googleapis.com/token");

    private final String name;
    private final String uri;

    SnsTokenEnum(String name, String uri) {
        this.name = name;
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public String getUri() {
        return uri;
    }

    public static SnsTokenEnum fromString(String btnType) {
        try {
            return SnsTokenEnum.valueOf(btnType.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid btnType: " + btnType);
        }
    }

}
