package br.com.beblue.infrastructure.exception;

import br.com.beblue.infrastructure.enumeration.MessageTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Message
 *
 * @author Andryev Lemes - 25/02/2019
 */
@Getter
@AllArgsConstructor
public class Message {

    private String description;
    private MessageTypeEnum type;

    @Override
    public String toString() {
        return type.getDescription() + " - " + description;
    }
}
