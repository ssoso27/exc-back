package com.swordmaster.excalibur.config;

import com.swordmaster.excalibur.entrypoint.AuthEntryPoint;
import com.swordmaster.excalibur.filter.JwtRequestFilter;
import com.swordmaster.excalibur.service.SecurityUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String[] AUTH_WHITELIST = {
            "/accounts/signup"
            , "/accounts/signin"
            , "/v2/api-docs"
            , "/swagger-resources"
            , "/swagger-resources/**"
            , "/configuration/ui"
            , "/configuration/security"
            , "/swagger-ui/"
            , "/swagger-ui/**"
            , "/webjars/**"
            , "/analysis-sessions/**/quizzes/auto"
    };

    @Autowired
    SecurityUserService securityUserService;

    @Autowired
    AuthEntryPoint authEntryPoint;

    @Override
    protected void configure(HttpSecurity security) throws Exception
    {
        security
                .csrf().disable()
                .cors().disable()
                .httpBasic()
                .and()
                .authorizeRequests()
                    .antMatchers(AUTH_WHITELIST)
                        .permitAll()
                    .anyRequest()
                        .access("isAuthenticated()");

        security
                .addFilterBefore(jwtRequestFilter(), UsernamePasswordAuthenticationFilter.class);

        security
                .exceptionHandling()
                    .authenticationEntryPoint(authEntryPoint);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtRequestFilter jwtRequestFilter() {
        return new JwtRequestFilter();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(securityUserService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}