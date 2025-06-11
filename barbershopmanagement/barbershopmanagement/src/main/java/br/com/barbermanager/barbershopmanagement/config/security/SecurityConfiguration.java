package br.com.barbermanager.barbershopmanagement.config.security;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
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
@SecurityScheme(
        name = SecurityConfiguration.SECURITY,
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer")
public class SecurityConfiguration {

    public static final String SECURITY = "bearerAuth";

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
                                .requestMatchers("/client/barber-shop/**").hasAnyRole("BARBERSHOP", "EMPLOYEE", "ADMIN")
                                .requestMatchers("/client/**").hasAnyRole("CLIENT", "ADMIN")
                                .requestMatchers("/barbershop/new").permitAll()
                                .requestMatchers("/barbershop/all").permitAll()
                                .requestMatchers("/barbershop/client/**").hasAnyRole("CLIENT", "ADMIN")
                                .requestMatchers("/barbershop/insert-client/**").authenticated()
                                .requestMatchers("/barbershop/remove-client/**").authenticated()
                                .requestMatchers("/barbershop/{barberShopId}").permitAll()
                                .requestMatchers("/barbershop/**").hasAnyRole("BARBERSHOP", "ADMIN")
                                .requestMatchers("/employee/all").hasAnyRole("ADMIN")
                                .requestMatchers("/employee/new").hasAnyRole("BARBERSHOP", "ADMIN")
                                .requestMatchers("/employee/delete/**").hasAnyRole("BARBERSHOP", "EMPLOYEE", "ADMIN")
                                .requestMatchers("/employee/active-employee/**").hasAnyRole("BARBERSHOP", "EMPLOYEE", "ADMIN")
                                .requestMatchers("/employee/update/**").hasAnyRole("BARBERSHOP", "EMPLOYEE", "ADMIN")
                                .requestMatchers("/employee/{employeeId}").permitAll()
                                .requestMatchers("/employee/barbershop/**").permitAll()
                                .requestMatchers("/item/new").hasAnyRole("BARBERSHOP", "EMPLOYEE", "ADMIN")
                                .requestMatchers("/item/{itemId}").permitAll()
                                .requestMatchers("/item/**").hasAnyRole("BARBERSHOP", "EMPLOYEE", "ADMIN")
                                .requestMatchers("/scheduling/barbershop/**").hasAnyRole("BARBERSHOP", "EMPLOYEE", "ADMIN")
                                .requestMatchers("/scheduling/client/**").hasAnyRole("CLIENT", "ADMIN")
                                .requestMatchers("/scheduling/employee/**").hasAnyRole("BARBERSHOP", "EMPLOYEE", "ADMIN")
                                .requestMatchers("/scheduling/item/**").hasAnyRole("BARBERSHOP", "EMPLOYEE", "ADMIN")
                                .requestMatchers("/scheduling/finish/**").hasAnyRole("BARBERSHOP", "EMPLOYEE", "ADMIN")
                                .requestMatchers("/scheduling/all").hasRole("ADMIN")
                                .requestMatchers("/scheduling/**").authenticated()
                                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
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
