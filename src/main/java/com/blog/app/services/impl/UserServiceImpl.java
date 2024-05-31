package com.blog.app.services.impl;

import com.blog.app.entities.User;
import com.blog.app.exceptions.ResourceNotFoundException;
import com.blog.app.payloads.UserDto;
import com.blog.app.repositories.UserRepository;
import com.blog.app.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public UserDto createUser(UserDto userDto) {

        User user = dtoToUser(userDto);   // Converting DtoUser to User ==>save the user ==> then converting back user to dtoUser and returns to expose
        User saveduser = this.userRepository.save(user);
        UserDto userToDto = userToDto(saveduser);

        return userToDto;
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {

        User dbUser = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        dbUser.setName(userDto.getName());
        dbUser.setEmail(userDto.getEmail());
        dbUser.setPassword(userDto.getPassword());
        dbUser.setAbout(userDto.getAbout());

        User updateduser = userRepository.save(dbUser);
        UserDto updatedUserDto = userToDto(updateduser);

        return updatedUserDto;
    }

    @Override
    public UserDto getUserById(Integer userId) {

        User dbUser = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        UserDto userDto = userToDto(dbUser);

        return userDto;
    }

    @Override
    public List<UserDto> getAllUsers() {

        List<User> allDbUser = userRepository.findAll();
        List<UserDto> allDtoUser  = allDbUser.stream().map(user -> userToDto(user)).collect(Collectors.toList());

        return allDtoUser;
    }

    @Override
    public void deleteUser(Integer userId) {

        //userRepository.deleteById(userId);
        User dbUser = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        userRepository.delete(dbUser);
    }

    // Model Converter (User To DTO) and (DTO to User)
    public UserDto userToDto(User user ){

        UserDto userDto = this.modelMapper.map(user, UserDto.class);
        /*
        UserDto userDto= new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setAbout(user.getAbout());
        userDto.setPassword(user.getPassword());
        */
        return userDto;
    }
    public User dtoToUser(UserDto userDto){

        User user= this.modelMapper.map(userDto,User.class);
//        User user = new User();
//
//        user.setId(userDto.getId());
//        user.setName(userDto.getName());
//        user.setEmail(userDto.getEmail());
//        user.setAbout(userDto.getAbout());
//        user.setPassword(userDto.getPassword());
        return user;
    }
}
