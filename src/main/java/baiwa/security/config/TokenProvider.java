package baiwa.security.config;

import static baiwa.security.domain.Constants.ACCESS_TOKEN_VALIDITY_SECONDS;
import static baiwa.security.domain.Constants.AUTHORITIES_KEY;
import static baiwa.security.domain.Constants.OGANIZATION_KEY;
import static baiwa.security.domain.Constants.SIGNING_KEY;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import baiwa.security.domain.AuthToken;
import baiwa.security.domain.UserInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class TokenProvider implements Serializable {

	
	
	@Autowired
	private JdbcTemplate  jdbcTemplate;
	
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SIGNING_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(Authentication authentication) {
        final String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority) .collect(Collectors.joining(","));
        
        System.out.println(" In  TokenProvider.generateToken  authorities:"+authorities);
        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .claim(OGANIZATION_KEY, "Chaingmai")
                .signWith(SignatureAlgorithm.HS256, SIGNING_KEY)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS*1000))
                .compact();
    }
    
    public AuthToken generateToken2(Authentication authentication) {
        final String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority) .collect(Collectors.joining(","));
        AuthToken returnToken = new AuthToken();
        System.out.println(" In  TokenProvider.generateToken2  authorities:"+authorities);
        String tokenStr= Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .claim(OGANIZATION_KEY, "Chaingmai")
                .signWith(SignatureAlgorithm.HS256, SIGNING_KEY)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS*1000))
                .compact();
        returnToken.setToken(tokenStr);
        
        returnToken.setUsername(authentication.getName());
        returnToken.setAuthorities(authorities);
        //test
        
        
        UserInfo userInfo = getUserInfo(authentication.getName());
        
        
        returnToken.setOrganizeCode(userInfo.getOrganizeCode());
        returnToken.setOrganizeDesc(userInfo.getOrganizeDesc());
        returnToken.setFullName(userInfo.getFullName());
        
        return returnToken;
    }

 
    public UserInfo getUserInfo(String username){
    	String sql = "SELECT u.airport_code airport_code, u.airport_des airport_des ,u.name name, u.surname surname from fw_users u   WHERE "+
    			     "  u.user_name = '"+username+"' ";
    	System.out.println("   SQL get Organize:"+sql);
    	UserInfo userInfo = (UserInfo)jdbcTemplate.queryForObject(sql,
    		new RowMapper<UserInfo>() {
	            public UserInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
	                UserInfo user = new UserInfo();
	                user.setOrganizeCode(rs.getString("airport_code"));
	                user.setOrganizeDesc(rs.getString("airport_des"));
	                user.setFullName(rs.getString("name")+" "+rs.getString("surname"));
	                return user;
	            }
        });
    	return userInfo;
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (
              username.equals(userDetails.getUsername())
                    && !isTokenExpired(token));
    }

    UsernamePasswordAuthenticationToken getAuthentication(final String token, final Authentication existingAuth, final UserDetails userDetails) {

        final JwtParser jwtParser = Jwts.parser().setSigningKey(SIGNING_KEY);

        final Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);

        final Claims claims = claimsJws.getBody();

        final Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }

}
