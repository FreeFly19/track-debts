package com.freefly19.trackdebts.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class JwtClaimMatcher<T> extends BaseMatcher<T> {
    private final String field;
    private final Matcher<?> matcher;
    private Object actualValue;

    private JwtClaimMatcher(String field, Matcher<?> matcher) {
        this.field = field;
        this.matcher = matcher;
    }

    @Override
    public boolean matches(Object item) {
        String token = (String) item;
        Claims claims = (Claims) Jwts.parser().parse(token).getBody();
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

    public static <T>Matcher<T> hasClaim(String claim, Matcher<T> stringMatcher) {
        return new JwtClaimMatcher<>(claim, stringMatcher);
    }
}
