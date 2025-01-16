package br.com.barbermanager.barbershopmanagement.api.controller;

import br.com.barbermanager.barbershopmanagement.domain.model.user.AuthenticationDTO;
import br.com.barbermanager.barbershopmanagement.domain.model.user.RegisterDTO;
import br.com.barbermanager.barbershopmanagement.domain.model.user.User;
import br.com.barbermanager.barbershopmanagement.domain.repository.UserRepository;
import br.com.barbermanager.barbershopmanagement.domain.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){
        this.userService.login(data);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data){
        boolean auth = this.userService.register(data);
        if(auth) return ResponseEntity.ok().build();
        else return ResponseEntity.badRequest().build();
    }
}
