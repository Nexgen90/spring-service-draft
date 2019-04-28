package ru.nexgen.oapi.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.nexgen.oapi.model.dto.RqTaskRegistration;
import ru.nexgen.oapi.model.dto.RsTaskRegistrationInfo;
import ru.nexgen.oapi.service.TaskRegistrationService;
import ru.nexgen.oapi.validator.RqTaskRegistrationValidator;

@Slf4j
@RestController
@AllArgsConstructor
public class TaskRegistrationController {
    private final TaskRegistrationService registrationService;
    private final RqTaskRegistrationValidator taskRegistrationValidator;

    @PostMapping(value = "/task/register")
    @ResponseBody
    public RsTaskRegistrationInfo registerTask(@RequestBody RqTaskRegistration request) {
        taskRegistrationValidator.validate(request);
        Long taskId = registrationService.createTask(request);
        return new RsTaskRegistrationInfo(taskId);
    }
}
