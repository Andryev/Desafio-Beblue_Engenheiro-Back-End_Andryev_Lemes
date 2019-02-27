package br.com.beblue.infrastructure.enumeration;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * MessageTypeEnum Enum
 *
 * @author Andryev Lemes - 23/02/2019
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public enum  MessageTypeEnum {

    INFO("I", "info"),
    ERROR("E", "error"),
    ALERT("A", "alert");

    private String code;
    private String description;
}
