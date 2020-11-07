package com.swordmaster.excalibur.config;

import com.swordmaster.excalibur.entrypoint.AuthEntryPoint;
import com.swordmaster.excalibur.filter.JwtRequestFilter;
import com.swordmaster.excalibur.service.SecurityUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    SecurityUserService securityUserService;

    @Autowired
    AuthEntryPoint authEntryPoint;

    @Override
    protected void configure(HttpSecurity security) throws Exception
    {
        security
                .csrf().disable()
                .httpBasic()
                .and()
                .authorizeRequests()
                    .antMatchers("/swagger-ui/", "/accounts/signup", "/accounts/signin")
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