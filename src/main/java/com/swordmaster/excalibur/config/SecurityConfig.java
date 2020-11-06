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
        // TODO: swagger, signin, signup 풀어줘야함
        security
                .csrf().disable()
                .httpBasic()
                .and()
                .authorizeRequests()
                    .antMatchers("/accounts/*") // TODO: 지금은 테스트용으로 해당 url을 걸어두었지만, 꼭 이거 제거해줘야함
                        .access("isAuthenticated()")
                    .anyRequest().authenticated();

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