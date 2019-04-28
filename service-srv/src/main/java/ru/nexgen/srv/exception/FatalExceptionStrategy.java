package ru.nexgen.srv.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;
import org.springframework.amqp.rabbit.listener.exception.ListenerExecutionFailedException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FatalExceptionStrategy extends ConditionalRejectingErrorHandler.DefaultExceptionStrategy {

    @Override
    public boolean isFatal(Throwable t) {
        if (t instanceof ListenerExecutionFailedException) {
            ListenerExecutionFailedException lefe = (ListenerExecutionFailedException) t;

            logger.error("Failed to process inbound message from queue; Failed message: " +
                    lefe.getLocalizedMessage(), t);
        } else {
            logger.error("Fatal exception ", t);
        }
        return super.isFatal(t);
    }
}
