package com.demo.udema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Qualifier("userDetailsServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/assets/**", "/coming_soon/**", "/css/**", "/img/**", "/js/**", "/layerslider/**", "/sass/**", "/video/**", "/registration" ,"/login").permitAll()
//                .anyRequest().authenticated()
                .antMatchers("/adminPage/**").hasAnyRole("STUDENT","TEACHER", "ADMIN")
                .antMatchers("/userPage/**").hasAnyRole("STUDENT", "TEACHER", "ADMIN")
                .antMatchers("/addCategory/**").hasAnyRole("TEACHER", "ADMIN")
                .antMatchers("/addListing/**").hasAnyRole("TEACHER", "ADMIN")
                .antMatchers("/reviews/**").hasAnyRole("TEACHER","ADMIN")
//                .antMatchers("/adminPage/**").access("hasRole('0') and hasRole('1')")
                .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
            .logout().permitAll()
                .and()
            .exceptionHandling()
                .accessDeniedPage("/404");
    }

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
}