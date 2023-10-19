package com.postcard.toyou.config;

import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;

public class TagFilterUtil {
    private static final PolicyFactory POLICY = new HtmlPolicyBuilder()
            .allowElements("a", "p", "div", "img", "figure", "strong", "i", "u", "s", "span", "ul", "ol", "li")
            .allowUrlProtocols("https")
            .allowAttributes("src", "href").onElements("a", "img")
            .allowAttributes("style", "class").onElements("figure")
            .requireRelNofollowOnLinks()
            .toFactory();

    public static String filter(String input) {
        return POLICY.sanitize(input);
    }

}
