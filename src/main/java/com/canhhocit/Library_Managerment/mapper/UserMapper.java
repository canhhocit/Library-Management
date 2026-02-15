package com.canhhocit.Library_Managerment.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.canhhocit.Library_Managerment.dto.request.UserCreateRequest;
import com.canhhocit.Library_Managerment.dto.request.UserUpdateRequest;
import com.canhhocit.Library_Managerment.dto.response.UserResponse;
import com.canhhocit.Library_Managerment.entities.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "borrows", ignore = true)
    User toUserCreate(UserCreateRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "borrows", ignore = true)
    void toUserUpdate(@MappingTarget User user, UserUpdateRequest request);

    UserResponse toUserResponse(User u);
}
