package ru.otus.spring_2020_11.hw17.security;

import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.otus.spring_2020_11.hw17.security.dao.UserRepository;

@AllArgsConstructor
@Service
public class UserDetailsDbService implements UserDetailsService {
    private final UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        val user = repo.findByLogin(username);

        return new UserDetailsDb(user.getLogin(), user.getPassword(), user.getRole());
    }
}
