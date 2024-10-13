package com.apec.pos.security;

import com.apec.pos.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;


@Configuration
public class ConfigSecurity {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private JwtFilterSecurity jwtFilterSecurity;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(crsf -> crsf.disable())
                .cors().and()
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/swagger-ui/**",
                            "/v3/api-docs/**").permitAll();
                    auth.requestMatchers("/pay/**").permitAll();
                    auth.requestMatchers("/error").permitAll();
                    auth.requestMatchers("/ws-food/**").permitAll();
                    auth.requestMatchers("/auth/**").permitAll();
                    auth.requestMatchers("/ADMIN/*").hasAnyAuthority("ADMIN", "EMPLOYEE");
                    auth.requestMatchers("/ADMIN/MANAGER/**").hasAuthority("ADMIN");
                    auth.requestMatchers("/user/**").hasAnyAuthority("USER","ADMIN");
                    auth.anyRequest().authenticated();
                });
        http.sessionManagement(
                session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );
        http.headers().cacheControl().disable();  // In Security Config
        http.addFilterBefore(jwtFilterSecurity, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public CorsFilter corsFilter() {
        // Tạo cấu hình CORS mới
        CorsConfiguration corsConfig = new CorsConfiguration();
        // Cho phép các nguồn gốc cụ thể (không sử dụng *)
        corsConfig.setAllowedOrigins(Arrays.asList(
                "http://localhost:5173",
                "http://127.0.0.1:5173",
                "http://localhost:5174",
                "http://14.225.206.173:8081",
                "http://103.175.146.145:8081",
                "http://103.175.146.145:5173",
                "http://103.175.146.145:5174"
        ));
        // Cho phép gửi credentials
        corsConfig.setAllowCredentials(true);
        // Thêm các phương thức HTTP được cho phép
        corsConfig.addAllowedMethod("GET");
        corsConfig.addAllowedMethod("PATCH");
        corsConfig.addAllowedMethod("POST");
        corsConfig.addAllowedMethod("PUT");
        corsConfig.addAllowedMethod("DELETE");
        corsConfig.addAllowedMethod("OPTIONS");
        // Cho phép tất cả các tiêu đề
        corsConfig.setAllowedHeaders(Arrays.asList("*"));

        // Tạo nguồn cấu hình CORS dựa trên URL
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Đăng ký cấu hình CORS cho tất cả các đường dẫn
        source.registerCorsConfiguration("/**", corsConfig);
        // Tạo bộ lọc CORS với nguồn cấu hình đã đăng ký
        return new CorsFilter(source);
    }
}
