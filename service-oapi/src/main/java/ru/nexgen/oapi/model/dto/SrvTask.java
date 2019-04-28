package ru.nexgen.oapi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class SrvTask implements Serializable {
    private static final long serialVersionUID = -3585805547026259805L;

    private Long taskId;
    private String title;
    private String message;
}
