package br.com.beblue.infrastructure.exception;

import br.com.beblue.infrastructure.enumeration.MessageTypeEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
/**
 * Message
 * @author Andryev Lemes - 25/02/2019
 */
@Data
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class Message {

    private String description;
    private MessageTypeEnum type;

    @Override
    public String toString() {
        return type.getDescription() + " - " + description;
    }
}
