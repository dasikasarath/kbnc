package student.example.kbnc;

import java.util.*;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;



import io.jsonwebtoken.SignatureAlgorithm;


import io.jsonwebtoken.security.Keys;
import java.security.Key;



public class jwtUtil {

    private static String key="sarathkumardasikakbncollegevijayawadaandgudivadatenthinterdegreeaiml";
    private static Key k=Keys.hmacShaKeyFor(key.getBytes());

    public static String generateToken(StuEntity obj){
        return Jwts.builder()
                   .setSubject(obj.getName())
                   .claim("role", obj.getRole())
                   .setIssuedAt(new Date())
                   .setExpiration(new Date(System.currentTimeMillis()+1000*60*60))
                   .signWith(k,SignatureAlgorithm.HS256)
                   .compact();

    }


    public static String extractName(String token){
       return Jwts.parser()
            .setSigningKey(k)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
            
    }

    public static String extractrole(String token){
       return Jwts.parser()
            .setSigningKey(k)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .get("role",String.class);
            
    }
    

    public static boolean validate(String token){
        try{
            extractName(token);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }
}
