package com.xiaotree.jinyuserver.util;

import com.alibaba.druid.support.json.JSONUtils;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.xiaotree.jinyuserver.domain.dto.LoginUser;
import com.xiaotree.jinyuserver.mapper.DataCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


@Component
@Slf4j
public class TokenUtil {
    private static final String header = "XIAOTREE";

    private static final String secret = "xit_key";

    private static final Integer day = 14;

    private final DataCache dataCache;

    public TokenUtil(DataCache dataCache) {
        this.dataCache = dataCache;
    }


    public static String createToken(Integer userId, List<Integer> roleIds) {
        // 创建一个 SimpleDateFormat 实例，并指定日期时间格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 使用 SimpleDateFormat 格式化 Calendar 对象
        //String formattedDate = sdf.format(now.getTime());
        //System.out.println(formattedDate);
        Calendar now = Calendar.getInstance();
        now.add(Calendar.DATE, day);
//        System.out.println(sdf.format(now.getTime()));


        HashMap<String, String> headMap = new HashMap<>();
        headMap.put("header", header);
        return JWT.create()
                  .withHeader(JSONUtils.toJSONString(headMap))
                  .withClaim("userId", userId)
                  .withClaim("roleIds", roleIds)
                  .withExpiresAt(now.getTime())
                  .sign(Algorithm.HMAC256(secret));
    }

    /**
     * 校验token并解析token
     */
    public LoginUser verifyToken(String token) throws AuthenticationException {
        LoginUser user = null;
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
        try {
            DecodedJWT jwt = verifier.verify(token);
            // 获取负载中的属性值
            Integer userId = jwt.getClaim("userId").asInt();
            List<Integer> roleIds = jwt.getClaim("roleIds").asList(Integer.class);
            user = LoginUser.builder()
                            .id(userId)
                            .roles(this.dataCache.getRoles(roleIds))
                            .authorities(this.dataCache.getPerms(roleIds))
                            .build();
        } catch (TokenExpiredException e) {
            String errorMsg = "令牌已过期";
            log.error("{}：{} ", errorMsg, e.getMessage());
            throw new BadCredentialsException(errorMsg);
        } catch (SignatureVerificationException e) {
            String errorMsg = "令牌签名无效";
            log.error("{}：{} ", errorMsg, e.getMessage());
            throw new BadCredentialsException(errorMsg);
        } catch (JWTVerificationException e) {
            String errorMsg = "令牌验证失败";
            log.error("{}：{} ", errorMsg, e.getMessage());
            throw new BadCredentialsException(errorMsg);
        }
        return user;
    }
}
