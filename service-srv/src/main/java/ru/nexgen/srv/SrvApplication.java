package ru.nexgen.srv;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Slf4j
@EnableRabbit
@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class SrvApplication {

    public static void main(String[] args) {
        SpringApplication.run(SrvApplication.class, args);
    }
}
