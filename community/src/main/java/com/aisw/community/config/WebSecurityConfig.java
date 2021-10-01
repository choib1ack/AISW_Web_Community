package com.aisw.community.config;

import com.aisw.community.component.advice.handler.ExceptionHandlerFilter;
import com.aisw.community.config.jwt.JwtAuthenticationFilter;
import com.aisw.community.config.jwt.JwtAuthorizationFilter;
import com.aisw.community.component.provider.JwtTokenProvider;
import com.aisw.community.component.provider.RedisProvider;
import com.aisw.community.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExceptionHandlerFilter exceptionHandlerFilter;

    @Autowired
    private CorsConfig corsConfig;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private RedisProvider redisProvider;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilter(corsConfig.corsFilter())
                .csrf().disable()
                // 세션 사용 안 함
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                // Basic: Authorization에 id, pw 넣어서 보냄
                // Bearer: Authorization에 token 넣어서 보냄
                .httpBasic().disable()

                .addFilter(new JwtAuthenticationFilter(authenticationManager(), jwtTokenProvider, redisProvider))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), userRepository, jwtTokenProvider, redisProvider))
                .authorizeRequests()
                .antMatchers("/api/auth/**")
                .access("hasRole('ROLE_GENERAL') or hasRole('ROLE_STUDENT') or hasRole('ROLE_COUNCIL') or hasRole('ROLE_ADMIN') or hasRole('ROLE_DEVELOPER')")
                .antMatchers("/api/auth-student/**")
                .access("hasRole('ROLE_STUDENT') or hasRole('ROLE_COUNCIL') or hasRole('ROLE_ADMIN') or hasRole('ROLE_DEVELOPER')")
                .antMatchers("/api/auth-council/**")
                .access("hasRole('ROLE_COUNCIL') or hasRole('ROLE_ADMIN') or hasRole('ROLE_DEVELOPER')")
                .antMatchers("/api/auth-admin/**")
                .access("hasRole('ROLE_ADMIN') or hasRole('ROLE_DEVELOPER')")
                .anyRequest().permitAll();
        http.addFilterBefore(exceptionHandlerFilter, JwtAuthorizationFilter.class);
    }
}
