package com.freefly19.trackdebts.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

import java.util.Objects;

public class JwtClaimMatcher extends BaseMatcher<String> {
    private final String field;
    private Object expectedValue;
    private Object actualValue;

    public JwtClaimMatcher(String field) {
        this.field = field;
    }

    public JwtClaimMatcher equalTo(Object expectedValue) {
        this.expectedValue = expectedValue;
        return this;
    }

    @Override
    public boolean matches(Object item) {
        String token = (String) item;
        Claims claims = (Claims) Jwts.parser().parse(token).getBody();
        actualValue = claims.get(field);
        return Objects.equals(actualValue, expectedValue);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(String.format(
                "Field \"%s\" has \"%s\" value, but expected \"%s\"",
                field,
                actualValue,
                expectedValue));
    }
}
