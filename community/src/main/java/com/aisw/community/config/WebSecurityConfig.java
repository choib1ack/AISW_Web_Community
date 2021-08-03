package com.aisw.community.config;

//import com.aisw.community.provider.CustomAuthenticationProvider;

import com.aisw.community.config.jwt.JwtAuthenticationFilter;
import com.aisw.community.config.jwt.JwtAuthorizationFilter;
import com.aisw.community.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CorsConfig corsConfig;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Autowired
//    private UserDetailsService userDetailsService;

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
//        auth.authenticationProvider(authenticationProvider());
//    }

//    @Bean
//    public AuthenticationProvider authenticationProvider(){
//        return new CustomAuthenticationProvider();
//    }

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

                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), userRepository))
                .authorizeRequests()
                .antMatchers("/auth/**")
                .access("hasRole('Role_GENERAL') or hasRole('ROLE_STUDENT') or hasRole('ROLE_COUNCIL') or hasRole('ROLE_ADMIN') or hasRole('ROLE_DEVELOPER')")
                .antMatchers("/auth-student/**")
                .access("hasRole('ROLE_STUDENT') or hasRole('ROLE_COUNCIL') or hasRole('ROLE_ADMIN') or hasRole('ROLE_DEVELOPER')")
                .antMatchers("/auth-council/**")
                .access("hasRole('ROLE_COUNCIL') or hasRole('ROLE_ADMIN') or hasRole('ROLE_DEVELOPER')")
                .antMatchers("/auth-admin/**")
                .access("hasRole('ROLE_ADMIN') or hasRole('ROLE_DEVELOPER')")
                .anyRequest().permitAll();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {

    }
}
