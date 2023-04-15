package com.api.base.security;

import java.util.Base64;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.api.base.utils.Constants;
import com.api.base.utils.enumerate.MessageCode;
import com.api.base.exception.BadAuthenticationException;
import com.api.base.service.impl.UserDetailsServiceImpl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @author BacDV
 *
 */
@Component
public class TokenProvider {
    private static final Logger log = LoggerFactory.getLogger(TokenProvider.class);

    @Value("${security.jwt.token.secret-key:beba9305-7e74-433e-93ee-8320f6d0c685}")
    private String secretKey;

    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String memberId, String memberName, long timeoutInseconds) {
        Claims claims = Jwts.claims().setSubject(memberId);
        claims.put(Constants.BEARER_TOKEN_MEMBER_NAME, memberName);
        claims.put(Constants.BEARER_TOKEN_TIMEOUT, timeoutInseconds);
        Date now = new Date();
        Date validity = new Date(now.getTime() + 1000 * timeoutInseconds);

        return Jwts.builder().setClaims(claims).setIssuedAt(now).setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey).compact();
    }

    public String refreshToken(Jws<Claims> claims, String token) {
        try {
            // get userId & role from token claims
            String userId = (String) claims.getBody().get(Constants.BEARER_TOKEN_SUB);
            String memberName = String.valueOf(claims.getBody().get(Constants.BEARER_TOKEN_MEMBER_NAME));
            long timeout = Long.parseLong(claims.getBody().get(Constants.BEARER_TOKEN_TIMEOUT).toString());

            // invalidate current token
            invalidateToken(userId, token);

            // generate a new token
            token = createToken(userId, memberName, timeout);
            BearerContextHolder.getContext().setRefreshToken(token);

            return token;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BadAuthenticationException(MessageCode.ERR_401.name());
        }
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsServiceImpl.loadUserByUsername(getMId(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getMId(String token) {
        return String.valueOf(
                Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get(Constants.BEARER_TOKEN_SUB));
    }

    public String getMemberName(String token) {
        return String.valueOf(Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody()
                .get(Constants.BEARER_TOKEN_MEMBER_NAME));
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader(Constants.AUTHORIZATION_HEADER);
        if (bearerToken != null && bearerToken.startsWith(Constants.BEARER_TOKEN_PREFIX)) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            Date onClaims = claims.getBody().getExpiration();
            Date current = new Date();
            long diff = onClaims.getTime() - current.getTime();

            boolean isValid = !onClaims.before(current);
            if (isValid && (diff > 0 && diff <= Constants.REFRESH_TOKEN_IN_TIME)) {
                refreshToken(claims, token);
            }
            return isValid;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BadAuthenticationException(MessageCode.ERR_401.name());
        }
    }

    public void invalidateToken(String userId, String token) {
        // TODO
    }
}
