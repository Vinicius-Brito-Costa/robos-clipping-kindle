package com.lionfish.robo_clipping_kindle.service;

import com.lionfish.robo_clipping_kindle.util.EnvironmentUtil;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailService  implements UserDetailsService {

    public static final String APPLICATION_LOGIN = "ROBO_CLIPPING_USER";
    public static final String APPLICATION_PASSWORD = "ROBO_CLIPPING_PASSWORD";

    /**
     * Loads a user by its username, the current method is only instantiating a new User object with default values
     * but, it can be extended to load users from a database ou anywhere you want.
     * AuthenticationManager uses this method to compare user provided credentials with the existing ones.
     * @param username user's username
     * @return new User containing it username and password
     * @throws UsernameNotFoundException if the username was not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        String envUser = EnvironmentUtil.getEnvVariable(APPLICATION_LOGIN);
        String envPassword = EnvironmentUtil.getEnvVariable(APPLICATION_PASSWORD);
        if(envUser.equals(username)){
            return new User(envUser, envPassword, new ArrayList<>());
        }
        throw new UsernameNotFoundException("Username was not found");
    }
}
