package com.shoppingcenter.app.event;

import org.springframework.context.ApplicationEvent;

import com.shoppingcenter.domain.category.Category;

import lombok.Getter;

@Getter
public class CategoryUpdateEvent extends ApplicationEvent {

    private Category category;

    public CategoryUpdateEvent(Object source, Category category) {
        super(source);
        this.category = category;
    }

}
