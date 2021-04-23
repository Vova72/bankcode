package com.vovarusskih72.bankcode.services;

import com.vovarusskih72.bankcode.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class AccountDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Account account = accountService.findByLogin(login);
        if(account == null) {
            throw new UsernameNotFoundException(login + "not found");
        }
        List<GrantedAuthority> roles = Arrays.asList(new SimpleGrantedAuthority(account.getRole().toString()));
        return new User(account.getLogin(), account.getPassword(), roles);
    }

}
