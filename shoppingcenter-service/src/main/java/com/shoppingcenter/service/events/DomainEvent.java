package com.shoppingcenter.service.events;

import org.springframework.context.ApplicationEvent;

public class DomainEvent extends ApplicationEvent {

    public DomainEvent(Object source) {
        super(source);
    }

}
