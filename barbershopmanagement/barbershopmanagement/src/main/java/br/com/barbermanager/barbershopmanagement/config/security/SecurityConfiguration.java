package br.com.barbermanager.barbershopmanagement.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity

public class SecurityConfiguration {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/auth/login").permitAll()
                        .requestMatchers("/client").hasRole("CLIENT")
                        .requestMatchers("/client/new").permitAll()
                        .requestMatchers("/client/barber-shop/").hasAnyRole("BARBERSHOP", "CLIENT", "EMPLOYEE")
                        .requestMatchers("/barbershop").hasRole("BARBERSHOP")
                        .requestMatchers("/barbershop/new").permitAll()
                        .requestMatchers("/barbershop/client").hasAnyRole("BARBERSHOP", "CLIENT", "EMPLOYEE")
                        .requestMatchers("/employee").hasAnyRole("EMPLOYEE", "BARBERSHOP")
                        .requestMatchers("/employee/active-employee").hasRole("BARBERSHOP")
                        .requestMatchers("/employee/delete").hasRole("BARBERSHOP")
                        .requestMatchers("/employee/new").permitAll()
                        .requestMatchers("/employee/barbershop/").hasAnyRole("EMPLOYEE","BARBERSHOP", "CLIENT")
                        .requestMatchers("/item").authenticated()
                        .requestMatchers("/item/new").hasAnyRole("EMPLOYEE", "BARBERSHOP")
                        .requestMatchers("/item/delete").hasAnyRole("EMPLOYEE", "BARBERSHOP")
                        .requestMatchers("/item/update").hasAnyRole("EMPLOYEE", "BARBERSHOP")
                        .requestMatchers("/item/active-item").hasAnyRole("EMPLOYEE", "BARBERSHOP")
                        .requestMatchers("/item/all").authenticated()
                        .anyRequest()
                        .authenticated()
                )
                .addFilterBefore(this.securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
