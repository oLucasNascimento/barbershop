package br.com.barbermanager.barbershopmanagement.domain.service.user;

import br.com.barbermanager.barbershopmanagement.domain.model.user.AuthenticationDTO;
import br.com.barbermanager.barbershopmanagement.domain.model.user.RegisterDTO;
import br.com.barbermanager.barbershopmanagement.domain.model.user.User;
import br.com.barbermanager.barbershopmanagement.domain.repository.UserRepository;
import br.com.barbermanager.barbershopmanagement.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    public Authentication login(AuthenticationDTO data){
        var userNamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        try{
          return this.authenticationManager.authenticate(userNamePassword);
        } catch (Exception ex){
            throw new BadRequestException(ex.getMessage());
        }
    }

    public boolean register(RegisterDTO data){
        if(this.userRepository.findByLogin(data.login()) != null) return false;
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.login(), encryptedPassword, data.role());
        this.userRepository.save(newUser);
        return true;
    }

}
