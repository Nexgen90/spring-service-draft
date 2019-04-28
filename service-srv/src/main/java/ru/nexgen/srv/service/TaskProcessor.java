package ru.nexgen.srv.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nexgen.srv.model.dto.SrvTask;

@Slf4j
@Service
public class TaskProcessor {

    public void process(SrvTask task) {
        log.info("Start processing task {}", task);
    }
}
