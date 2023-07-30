package com.contact.list.mappers;

import com.contact.list.api.request.UserRegisterRequest;
import com.contact.list.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    public abstract User toUser(UserRegisterRequest dto);

}
