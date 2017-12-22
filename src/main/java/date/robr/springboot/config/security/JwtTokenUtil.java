package date.robr.springboot.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Component
//@ConfigurationProperties(prefix="jwt")
public class JwtTokenUtil implements Serializable {

	private static final long serialVersionUID = -3301605591108950415L;

	private static final String CLAIM_KEY_USERNAME = "sub";
	private static final String CLAIM_KEY_USERUID = "aud";
	private static final String CLAIM_KEY_CREATED = "created";

	@Value("${jwt.secret}")
	private String secret;
	@Value("${jwt.expiration}")
	private Long expiration;



	public String getUsernameFromToken(String token) {

		String username;
		try {
			final Claims claims = getClaimsFromToken(token);
			username = claims.getSubject();

		} catch (Exception e) {
			username = null;
		}
		return username;
	}
	private Timestamp getCreatedDateFromToken(String token) {
		Timestamp created;
		try {
			final Claims claims = getClaimsFromToken(token);
			created = new Timestamp((Long) claims.get(CLAIM_KEY_CREATED));
		} catch (Exception e) {
			created = null;
		}
		return created;
	}
	private String getUserUidFromToken(String token) {
		String userUid;
		try {
			final Claims claims = getClaimsFromToken(token);
			userUid =  (String) claims.get(CLAIM_KEY_USERUID);
		} catch (Exception e) {
			userUid = null;
		}
		return userUid;
	}
	private Date getExpirationDateFromToken(String token) {
		Date expiration;
		try {
			final Claims claims = getClaimsFromToken(token);
			expiration = claims.getExpiration();
		} catch (Exception e) {
			expiration = null;
		}
		return expiration;
	}

	private Claims getClaimsFromToken(String token) {

		Claims claims;
		try {
			claims = Jwts.parser()
					.setSigningKey(secret)
					.parseClaimsJws(token)
					.getBody();
		} catch (Exception e) {
			claims = null;
		}
		return claims;
	}

	private Date generateExpirationDate() {
		return new Date(System.currentTimeMillis() + expiration * 1000);
	}

	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
		return (lastPasswordReset != null && created.before(lastPasswordReset));
	}

	public String generateToken(UserDetails userDetails) {
		
		JwtUser jwtUser = (JwtUser)userDetails;
		Map<String, Object> claims = new HashMap<>();

		claims.put(CLAIM_KEY_USERNAME, jwtUser.getUsername());
		claims.put(CLAIM_KEY_USERUID, jwtUser.getUserUid());
		claims.put(CLAIM_KEY_CREATED, new Date());
		return generateToken(claims);
	}

	private String generateToken(Map<String, Object> claims) {
		return Jwts.builder()
				.setClaims(claims)
				.setExpiration(generateExpirationDate())
				.signWith(SignatureAlgorithm.HS512, secret)
				.compact();
	}

	public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset,String userUid) {
		final Date created = getCreatedDateFromToken(token);
		return userUid.equals(getUserUidFromToken(token)) && !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
				&& !isTokenExpired(token);
	}

	public String refreshToken(String token) {
		String refreshedToken;
		try {
			final Claims claims = getClaimsFromToken(token);
			claims.put(CLAIM_KEY_CREATED, new Date());
			refreshedToken = generateToken(claims);
		} catch (Exception e) {
			refreshedToken = null;
		}
		return refreshedToken;
	}

	Boolean validateToken(String token, JwtUser userDetails) {
		final String userName = getUsernameFromToken(token);
		final String userUid = getUserUidFromToken(token);
		final Timestamp created = getCreatedDateFromToken(token);
		//final Date expiration = getExpirationDateFromToken(token);
		return (
				userName.equals(userDetails.getUsername())
				&& userUid.equals(userDetails.getUserUid())
				&& !isTokenExpired(token)
				&& !isCreatedBeforeLastPasswordReset(created, userDetails.getLastPasswordResetDate()));
	}
}