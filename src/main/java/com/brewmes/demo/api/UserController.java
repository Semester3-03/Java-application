package com.brewmes.demo.api;


import com.brewmes.demo.Persistence.UserRepository;
import com.brewmes.demo.security.UserModel;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@CrossOrigin(origins = "*")
public class UserController
{
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder)
    {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/signup")
    public void signUp(@RequestBody UserModel userModel)
    {
        if (userRepository.findByUsername(userModel.getUsername()) == null) {
            userModel.setPassword(bCryptPasswordEncoder.encode(userModel.getPassword()));
            userRepository.save(userModel);
        } else {
            System.out.println("user already created...");
        }

    }

}