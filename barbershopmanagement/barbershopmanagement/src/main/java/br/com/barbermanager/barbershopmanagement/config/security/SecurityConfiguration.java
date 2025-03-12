package br.com.barbermanager.barbershopmanagement.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
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
                        .requestMatchers("/auth/register").permitAll()
                        .requestMatchers("/auth/login").permitAll()
                        .requestMatchers("/client/new").permitAll()
                        .requestMatchers("/client/{clientId}").hasAnyRole("CLIENT", "ADMIN")
                        .requestMatchers("/client/barber-shop/**").hasAnyRole("BARBERSHOP","EMPLOYEE","ADMIN")
                        .requestMatchers("/client/**").hasAnyRole("CLIENT","ADMIN")
                        .requestMatchers("/barbershop/new").permitAll()
                        .requestMatchers("/barbershop/all").permitAll()
                        .requestMatchers("/barbershop/{barberShopId}").permitAll()
                        .requestMatchers("/barbershop/client/**").hasAnyRole("CLIENT","ADMIN")
                        .requestMatchers("/barbershop/insert-client/**").authenticated()
                        .requestMatchers("/barbershop/remove-client/**").authenticated()
                        .requestMatchers("/barbershop/**").hasAnyRole("BARBERSHOP","ADMIN")
                        .requestMatchers("/employee/{employeeId}").permitAll()
                        .requestMatchers("/employee/barbershop/**").permitAll()
                        .requestMatchers("/employee/delete/**").hasAnyRole("BARBERSHOP","EMPLOYEE","ADMIN")
                        .requestMatchers("/employee/active-employee/**").hasAnyRole("BARBERSHOP","EMPLOYEE","ADMIN")
                        .requestMatchers("/employee/update/**").hasAnyRole("BARBERSHOP","EMPLOYEE","ADMIN")
                        .requestMatchers("/employee/**").hasAnyRole("BARBERSHOP","ADMIN")
                        .requestMatchers("/item/new").hasAnyRole("BARBERSHOP","EMPLOYEE","ADMIN")
                        .requestMatchers("/item/{itemId}").permitAll()
                        .requestMatchers("/item/**").hasAnyRole("BARBERSHOP","EMPLOYEE","ADMIN")
                        .requestMatchers("/scheduling/barbershop/**").hasAnyRole("BARBERSHOP","EMPLOYEE","ADMIN")
                        .requestMatchers("/scheduling/client/**").hasRole("CLIENT")
                        .requestMatchers("/scheduling/employee/**").hasAnyRole("BARBERSHOP","EMPLOYEE","ADMIN")
                        .requestMatchers("/scheduling/item/**").hasAnyRole("BARBERSHOP","EMPLOYEE","ADMIN")
                        .requestMatchers("/scheduling/finish/**").hasAnyRole("BARBERSHOP","EMPLOYEE","ADMIN")
                        .requestMatchers("/scheduling/all").hasRole("ADMIN")
                        .requestMatchers("/scheduling/**").authenticated()
                        .anyRequest().hasRole("ADMIN")
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
