package com.sgallalucas.gym.controllers.mappers;

import com.sgallalucas.gym.controllers.DTOs.UserLoginDTO;
import com.sgallalucas.gym.controllers.DTOs.UserRegisterDTO;
import com.sgallalucas.gym.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserRegisterDTO userDTO);

    User loginDTOtoEntity(UserLoginDTO userLoginDTO);
}
