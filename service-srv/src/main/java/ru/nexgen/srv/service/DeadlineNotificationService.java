package ru.nexgen.srv.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DeadlineNotificationService {

    public void notifyUser() {
        log.info("Send notification ...");
    }
}
