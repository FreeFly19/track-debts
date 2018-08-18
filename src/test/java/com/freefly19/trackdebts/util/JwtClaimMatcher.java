package com.freefly19.trackdebts.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SigningKeyResolver;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class JwtClaimMatcher<T> extends BaseMatcher<T> {
    private final String field;
    private final Matcher<?> matcher;
    private Object actualValue;
    private String key;

    private JwtClaimMatcher(String field, Matcher<?> matcher) {
        this.field = field;
        this.matcher = matcher;
    }

    public JwtClaimMatcher<T> withKey(String key) {
        this.key = key;
        return this;
    }

    @Override
    public boolean matches(Object item) {
        String token = (String) item;
        JwtParser parser = Jwts.parser();

        if (key != null) parser = parser.setSigningKey(key);

        Claims claims = (Claims) parser
                .parse(token)
                .getBody();
        actualValue = claims.get(field);
        return matcher.matches(actualValue);
    }

    @Override
    public void describeTo(Description description) {
        matcher.describeTo(description);
    }

    @Override
    public void describeMismatch(Object item, Description description) {
        matcher.describeMismatch(actualValue, description);
    }

    public static <T>JwtClaimMatcher<T> hasClaim(String claim, Matcher<T> stringMatcher) {
        return new JwtClaimMatcher<>(claim, stringMatcher);
    }
}
