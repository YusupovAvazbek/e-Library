package com.example.elibrary.service.serviceImpl;

import com.example.elibrary.dto.LoginDto;
import com.example.elibrary.dto.ResponseDto;
import com.example.elibrary.dto.UsersDto;
import com.example.elibrary.model.Users;
import com.example.elibrary.repository.UsersRepository;
import com.example.elibrary.security.JwtService;
import com.example.elibrary.service.UserService;
import com.example.elibrary.service.mapper.UserMapper;
import static com.example.elibrary.service.validator.AppStatusCode.*;
import static com.example.elibrary.service.validator.AppStatusMessages.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UsersRepository usersRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    @Override
    public ResponseDto<UsersDto> getUser(String email) {
        return usersRepository.findByEmailAndIsActive(email, (short) 1)
                        .map(u ->ResponseDto.<UsersDto>builder()
                                .code(OK_CODE)
                                .message(OK)
                                .success(true)
                                .data(userMapper.toDto(u))
                                .build()
                        ).orElse(ResponseDto.<UsersDto>builder()
                                .message(NOT_FOUND)
                                .code(NOT_FOUND_ERROR_CODE)
                                .build());
        }
    @Override
    public ResponseDto<UsersDto> addUser(UsersDto usersDto) {
        if(getUser(usersDto.getEmail()).getData()==null){
            Users user = userMapper.toEntity(usersDto);
            usersRepository.save(user);
            return ResponseDto.<UsersDto>builder()
                    .code(OK_CODE)
                    .message(OK)
                    .success(true)
                    .data(userMapper.toDto(user))
                    .build();
        }
        return ResponseDto.<UsersDto>builder()
                .message(DUPLICATE_ERROR)
                .code(VALIDATION_ERROR_CODE)
                .build();
    }
    @Override
    public ResponseDto<UsersDto> editUser(UsersDto usersDto) {
        if(usersDto.getId() == null){
            return ResponseDto.<UsersDto>builder()
                    .code(VALIDATION_ERROR_CODE)
                    .message(NULL_VALUE)
                    .build();
        }
        Optional<Users> optional = usersRepository.findFirstByIdAndIsActive(usersDto.getId(), (short) 1);
        if(optional.isEmpty()){
            return ResponseDto.<UsersDto>builder()
                    .code(NOT_FOUND_ERROR_CODE)
                    .message(NOT_FOUND)
                    .build();
        }
        try{
            Users user = optional.get();
            usersRepository.save(editHelper(user,usersDto));
            return ResponseDto.<UsersDto>builder()
                    .message(OK)
                    .code(OK_CODE)
                    .success(true)
                    .data(userMapper.toDto(user))
                    .build();
        }catch (Exception e){
            return ResponseDto.<UsersDto>builder()
                    .code(DATABASE_ERROR_CODE)
                    .message(DATABASE_ERROR + e.getMessage())
                    .success(false)
                    .build();
        }
    }
    @Override
    public ResponseDto<UsersDto> delete(Integer id) {
        Optional<Users> user=usersRepository.findFirstByIdAndIsActive(id,(short)1);
        if(user.isEmpty()) {
            return (ResponseDto.<UsersDto>builder()
                    .message(NOT_FOUND)
                    .code(NOT_FOUND_ERROR_CODE)
                    .build());
        }
        Users delUser= user.get();
        delUser.setIsActive((short) 0);
        try {
            usersRepository.save(delUser);
            return ResponseDto.<UsersDto>builder()
                    .success(true)
                    .message(OK)
                    .data(userMapper.toDto(delUser))
                    .build();

        }catch (Exception e){
            return ResponseDto.<UsersDto>builder()
                    .success(false)
                    .message(e.getMessage())
                    .code(OK_CODE)
                    .build();
        }
    }

    @Override
    public ResponseDto<String> login(LoginDto loginDto) {
        UsersDto users = loadUserByUsername(loginDto.getUsername());
        if(!passwordEncoder.matches(loginDto.getPassword(), users.getPassword())){
            return ResponseDto.<String>builder()
                    .message("Password is not correct")
                    .code(VALIDATION_ERROR_CODE)
                    .build();
        }

        return ResponseDto.<String>builder()
                .code(OK_CODE)
                .message(OK)
                .data(jwtService.generateToken(users))
                .success(true)
                .build();
    }


    public Users editHelper(Users user, UsersDto usersDto){
        if(usersDto.getEmail() != null){
            user.setEmail(usersDto.getEmail());
        }
        if(usersDto.getName() != null){
            user.setName(usersDto.getName());
        }
        if(usersDto.getPassword() != null){
            user.setPassword(usersDto.getPassword());
        }
        if(usersDto.getSurname() != null){
            user.setSurname(usersDto.getSurname());
        }
        if(usersDto.getPhoneNumber() != null){
            user.setPhoneNumber(usersDto.getPhoneNumber());
        }

        return user;
    }

    @Override
    public UsersDto loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> users = usersRepository.findFirstByEmail(username);
        if(users.isEmpty()) throw new UsernameNotFoundException("user is not found");

        return userMapper.toDto(users.get());
    }
}
