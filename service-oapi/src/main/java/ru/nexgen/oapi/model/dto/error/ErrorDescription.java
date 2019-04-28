package ru.nexgen.oapi.model.dto.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "errorDescription")
public class ErrorDescription {
    private String errorMessage;
    private String service;
}
