package ru.nexgen.oapi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RqTaskRegistration {
    private String title;
    private String text;
}
