package br.com.smartcarweb.api.jwt.filter;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTUtil {

	//private static final long EXPIRATION_TIME = 860_000_000; // 10 days
	private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 10; // 10 days	
	//private static final long EXPIRATION_TIME = 1L; // 1 minuto
	private static final String SECRET = "MySecret";
	public static final String TOKEN_PREFIX = "Bearer";

	public static String create(String subject) {
		Key key = new SecretKeySpec(SECRET.getBytes(), 0, SECRET.getBytes().length, "DES");
		String JWT = Jwts.builder().setSubject(subject)
				.setExpiration(toDate(LocalDateTime.now().plusMinutes(EXPIRATION_TIME)))
				.setIssuedAt(new Date())
				.signWith(SignatureAlgorithm.HS512, key).compact();
		return JWT;
	}

	public static Jws<Claims> decode(String token) {
		Key key = new SecretKeySpec(SECRET.getBytes(), 0, SECRET.getBytes().length, "DES");
		return Jwts.parser().setSigningKey(key).parseClaimsJws(token);
	}
	
	public static String getUser(String token) {
		Key key = new SecretKeySpec(SECRET.getBytes(), 0, SECRET.getBytes().length, "DES");
		String user = Jwts.parser().setSigningKey(key).parseClaimsJws(token.replace(JWTUtil.TOKEN_PREFIX, "")).getBody()
				.getSubject();
		return user;
	}

	private static Date toDate(LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}
	
}
