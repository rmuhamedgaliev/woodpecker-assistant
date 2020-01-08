package io.github.rmuhamedgaliev.woodpecker.service.impl;

import io.github.rmuhamedgaliev.woodpecker.model.User;
import io.github.rmuhamedgaliev.woodpecker.repository.UserRepository;
import io.github.rmuhamedgaliev.woodpecker.repository.YoutrackUserRepository;
import io.github.rmuhamedgaliev.woodpecker.security.UserAuthentication;
import io.github.rmuhamedgaliev.woodpecker.service.YoutrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class YoutrackServiceImpl implements YoutrackService {

    @Autowired
    private YoutrackUserRepository youtrackUserRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void checkTelegramUserHasYoutrackAccount(Integer telegramUserId) {

        Long id = Long.valueOf(telegramUserId);
        Optional<User> userFromDB = userRepository.findById(id);

        if (userFromDB.isPresent()) {
            UserAuthentication userAuthentication = new UserAuthentication(userFromDB.get());

            SecurityContext sc = SecurityContextHolder.getContext();
            sc.setAuthentication(userAuthentication);
        }
    }
}
