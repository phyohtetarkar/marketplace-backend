package com.marketplace.api.consumer.user;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserEditDTO {

	@JsonIgnore
    private long userId;

    private String name;

    private String phone;

//    private String email;

}
