package com.harsha.spring.services;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

import com.harsha.spring.models.Role;
import com.harsha.spring.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;
    
	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<com.harsha.spring.models.User> user = userRepository.findById(username);

        if (user.isPresent()) {
            com.harsha.spring.models.User userDetails = user.get();
            String uname = userDetails.getUsername();
            String password = userDetails.getPassword();
            Role role = userDetails.getRole();
            String authority = role.getRole();
            Collection<GrantedAuthority> authorities =  new HashSet<>();
            authorities.add(new SimpleGrantedAuthority(authority));
            return new User(uname, password,  authorities);
            
        }else{
            throw new UsernameNotFoundException("No username found with given username");
        }
        
    }
    
    

}
