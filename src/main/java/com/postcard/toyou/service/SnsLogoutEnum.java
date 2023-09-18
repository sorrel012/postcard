package com.postcard.toyou.service;

public enum SnsLogoutEnum {
    NAVER("네이버", "https://nid.naver.com/oauth2.0/token"),
    GOOGLE("구글", "https://oauth2.googleapis.com/token");

    private final String name;
    private final String uri;

    SnsLogoutEnum(String name, String uri) {
        this.name = name;
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public String getUri() {
        return uri;
    }

    public static SnsLogoutEnum logoutString(String btnType) {
        try {
            return SnsLogoutEnum.valueOf(btnType.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid btnType: " + btnType);
        }
    }

}
