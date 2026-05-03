package student.example.kbnc;

import java.io.IOException;
import java.util.*;

import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class filter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        String url=request.getRequestURI();
        String name=null;
        String role=null;
        String header=null;
        String token=null;

        if(url.startsWith("/auth") || url.startsWith("/index.html") || url.startsWith("/app.js") || url.startsWith("/style.css")){
            filterChain.doFilter(request, response);
            return;
        }

         header=request.getHeader("Authorization");
        if(header!=null && header.startsWith("Bearer ")){
             token=header.substring(7);
            try{
                name=jwtUtil.extractName(token);
                role=jwtUtil.extractrole(token);

            }
            catch(Exception e){
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("unauthorised access");
                return; 
            }
        }

        if(name!=null && role!=null && jwtUtil.validate(token) ){

            //List<SimpleGrantedAuthority> authorities=List.of(new SimpleGrantedAuthority("ROLE_"+role));\\

             List<org.springframework.security.core.GrantedAuthority> authorities =
                    List.of(new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_"+role));

                   // System.out.println("=== FILTER DEBUG ===");
//System.out.println("URL: " + url);
//System.out.println("Header: " + header);
//System.out.println("Name: " + name);
//System.out.println("Role: " + role);
//System.out.println("Authority set: ROLE_" + role);

            UsernamePasswordAuthenticationToken auth= new UsernamePasswordAuthenticationToken(name,null,authorities);
            SecurityContextHolder.getContext().setAuthentication(auth);
            filterChain.doFilter(request,response);
            

        }

        else{
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("unauthorized access");
        //return//
        }

        
    filterChain.doFilter(request, response);
       
    }

   


}