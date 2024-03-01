package org.library_gabs.service.impl;

import org.library_gabs.domain.entity.Users;
import org.library_gabs.domain.repository.UsersRepository;
import org.library_gabs.exception.InvalidPasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UsersRepository repository;

    @Transactional
    public Users save(Users users) {
        return repository.save(users);
    }

    public UserDetails authenticate (Users users) {
        UserDetails userDetails = loadUserByUsername(users.getLogin());
        boolean passwordCorrect = encoder.matches(users.getPassword(), userDetails.getPassword());
        if(passwordCorrect) {
            return userDetails;
        } throw new InvalidPasswordException();
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Users users = repository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("Estudante não encontrado!"));

        String[] roles = users.isAdmin() ?
                new String[]{"ADMIN", "USER"} : new String[]{"USER"};

        return User
                .builder()
                .username(users.getLogin())
                .password(users.getPassword())
                .roles(roles)
                .build();
    }
}


