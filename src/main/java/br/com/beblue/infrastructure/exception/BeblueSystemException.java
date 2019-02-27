package br.com.beblue.infrastructure.exception;

import br.com.beblue.infrastructure.enumeration.MessageTypeEnum;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Beblue Exception custom exceptions
 * @author Andryev Lemes - 25/02/2019
 */
public class BeblueSystemException extends Throwable {

    private static final long serialVersionUID = -868456243429452238L;

    private List<Message> messages;

    public BeblueSystemException(List<Message> messages) {
        this.messages = messages == null ? new ArrayList<>() : messages;
    }

    public BeblueSystemException(String message, MessageTypeEnum type) {
        this.messages = new ArrayList<>();
        this.messages.add(new Message(message, type));
    }

    public List<Message> getMessages() {
        return messages;
    }

    @Override
    public String getMessage() {
        StringBuilder message = new StringBuilder();
        if (!CollectionUtils.isEmpty(messages)) {
            for (Message mensagem : messages) {
                message.append("[");
                message.append(mensagem.getType().getCode());
                message.append(":");
                message.append(mensagem.getDescription());
                message.append("]");
            }
        }
        return message.length() > 0 ? message.toString() : super.getMessage();
    }
}
