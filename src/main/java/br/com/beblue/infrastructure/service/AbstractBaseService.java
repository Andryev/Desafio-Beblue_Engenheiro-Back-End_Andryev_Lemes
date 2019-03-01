package br.com.beblue.infrastructure.service;

import br.com.beblue.infrastructure.constants.MenssageKeyConstants;
import br.com.beblue.infrastructure.enumeration.MessageTypeEnum;
import br.com.beblue.infrastructure.exception.BeblueSystemException;
import br.com.beblue.infrastructure.exception.Message;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Abstract service. common features
 *
 * @author Andryev Lemes - 28/02/2019
 */
public abstract class AbstractBaseService<E, ID extends Serializable> {

    @Autowired
    private MessageSource messageSource;

    protected Message addMessage(String messageKey, MessageTypeEnum type, String... args) {
        String message = messageSource.getMessage(messageKey, args, LocaleContextHolder.getLocale());
        return new Message(message, type);
    }

    protected String getMessageSource(String messageKey, String... args) {
        return messageSource.getMessage(messageKey, args, LocaleContextHolder.getLocale());
    }

    protected void addErrorMessage(String messageKey, List<Message> messages, String... args) {
        messages.add(addMessage(messageKey, MessageTypeEnum.ERROR, args));
    }

    protected void addRequiredFieldMessage(List<Message> messages, String fieldLabel) {
        addRequiredFieldMessage(messages, fieldLabel, MenssageKeyConstants.ERROR_REQUIRED_FIELD);
    }

    protected void addRequiredFieldMessage(List<Message> messages, String fieldLabel, String menssageKeyConstants) {
        addErrorMessage(menssageKeyConstants, messages, fieldLabel);
    }


    protected void throwBeblueException(String messageKey, MessageTypeEnum type) throws BeblueSystemException {
        String message = messageSource.getMessage(messageKey, null, LocaleContextHolder.getLocale());
        throw new BeblueSystemException(message, type);
    }

    protected void throwBeblueException(List<Message> messages) throws BeblueSystemException {
        if (CollectionUtils.isNotEmpty(messages)) {
            throw new BeblueSystemException(messages);
        }
    }

    @SuppressWarnings("rawtypes")
    protected void validateRequiredField(Object value, String fieldLabel, List<Message> messages, String menssageKeyConstants) {
        if (value == null) {
            addRequiredFieldMessage(messages, fieldLabel, menssageKeyConstants);

        } else if (value instanceof String && StringUtils.isBlank((String) value)) {
            addRequiredFieldMessage(messages, fieldLabel);

        } else if (value instanceof Collection && CollectionUtils.isEmpty((Collection) value)) {
            addRequiredFieldMessage(messages, fieldLabel);
        }
    }

    @SuppressWarnings("rawtypes")
    protected void validateRequiredField(Object value, String fieldLabel, List<Message> messages) {
        validateRequiredField(value, fieldLabel, messages, MenssageKeyConstants.ERROR_REQUIRED_FIELD);
    }

    public Optional<E> findById(ID id) {
        return getRepository().findById(id);
    }

    public List<E> findAll() {
        return getRepository().findAll();
    }

    public Page<E> findAll(Pageable pageable) {
        return getRepository().findAll(pageable);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveAll(List<E> list) {
        getRepository().saveAll(list);
    }

    abstract protected JpaRepository<E, ID> getRepository();


}
