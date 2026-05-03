package student.example.kbnc;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class securityConfig {

    

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf(csrf->csrf.disable());

        http.cors(cors->{});

       
        http.authorizeHttpRequests(auth->
            auth.requestMatchers("/auth/**","/index.html","/style.css","/app.js").permitAll()
            .requestMatchers("/admin/**").hasRole("ADMIN")
            .requestMatchers("/user/**").hasRole("USER")
            .anyRequest().authenticated()
        );

        


         http.addFilterBefore(new filter(),UsernamePasswordAuthenticationFilter.class);// it is stopping html//
        
        return http.build();
    }
    
}
