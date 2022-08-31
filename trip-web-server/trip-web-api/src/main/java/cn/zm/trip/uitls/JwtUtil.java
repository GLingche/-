package cn.zm.trip.uitls;
import entity.BaseRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

public class JwtUtil {

    public static final long EXPIRE = 1000 * 60 * 60 * 12;
    public static final String APP_SECRET = "BDbRigUDaFfWLPHO";

    /*
    登录使用JWT技术。
    jwt 可以生成 一个加密的token，做为用户登录的令牌，当用户登录成功之后，发放给客户端。
    请求需要登录的资源或者接口的时候，将token携带，后端验证token是否合法。
    jwt 有三部分组成：A.B.C
    A：Header，{"type":"JWT","alg":"HS256"} 固定
    B：playload，存放信息，比如，用户id，过期时间等等，可以被解密，不能存放敏感信息
    C:  签证，A和B加上秘钥 加密而成，只要秘钥不丢失，可以认为是安全的。
    jwt 验证，主要就是验证C部分 是否合法。
    */


    public static String getToken(String userId, BaseRole role){

        String JwtToken = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")

                .setSubject("user")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))

                .claim("id", userId)
                .claim("role", role.getName())

                .signWith(SignatureAlgorithm.HS256, APP_SECRET)
                .compact();

        return JwtToken;
    }

    /**
     * 判断token是否存在与有效
     * @param jwtToken
     * @return
     */
    public static boolean checkToken(String jwtToken) {
        if(StringUtils.isEmpty(jwtToken)) return false;
        try {
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 判断token是否存在与有效
     * @param request
     * @return
     */
    public static boolean checkToken(HttpServletRequest request) {
        try {
            String token = request.getHeader("token");
            if(StringUtils.isEmpty(token)) return false;
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 根据token获取会员id
     * @param request
     * @return
     */
    public static String getUserIdByToken(HttpServletRequest request) {
        String jwtToken = request.getHeader("token");
        if(StringUtils.isEmpty(jwtToken)) return "";
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        Claims claims = claimsJws.getBody();
        return (String)claims.get("id");
    }

    public static String getUserRoleByToken(HttpServletRequest request) {
        String jwtToken = request.getHeader("token");
        if(StringUtils.isEmpty(jwtToken)) return null;
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        Claims claims = claimsJws.getBody();
        String role = (String) claims.get("role");
        return role;
    }
}
