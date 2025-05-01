package com.hotel.booking.user_services.serviceImpl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.hotel.booking.user_services.dto.BaseResponseDto;
import com.hotel.booking.user_services.dto.CreateUserDto;
import com.hotel.booking.user_services.dto.ResponseModel;
import com.hotel.booking.user_services.dto.SignInDto;
import com.hotel.booking.user_services.dto.UserInfoResponseDto;
import com.hotel.booking.user_services.entity.Role;
import com.hotel.booking.user_services.entity.User;
import com.hotel.booking.user_services.exception.Message;
import com.hotel.booking.user_services.repository.RoleRepository;
import com.hotel.booking.user_services.repository.UserRepository;
import com.hotel.booking.user_services.service.UserService;
import com.hotel.booking.user_services.utils.JwtUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;


    @Override
    public ResponseModel createClientUser(CreateUserDto dto) {
        try {
            Optional<User> clientExists = userRepository.findByEmail(dto.getEmail());

            if(clientExists.isPresent()){
                return new ResponseModel(HttpStatus.BAD_REQUEST.value(), String.format(Message.ALREADY_EXISTS,"Email"), dto.getEmail());
            }

            ModelMapper modelMapper = new ModelMapper();
            User newClient = modelMapper.map(dto, User.class);
            newClient.setUserCode("CLIENT-" + UUID.randomUUID().toString().replace("-", "").substring(0, 8));
            newClient.setEmail(dto.getEmail());
            newClient.setName(dto.getName());
            newClient.setPassword(encoder.encode(dto.getPassword()));


            /* CHECK IF ROLE EXISTS AND ASSIGN TO CLIENT */
            Optional<Role> clientRole = this.roleRepository.findById(1L);
            if(clientRole.isPresent()){
                Role role = clientRole.get();
                Set<Role> roles = new HashSet<>();
                roles.add(role);
                newClient.setRoles(roles);
                /* newClient.setRoles(role.getRoleName());
                List<Role> roles = new ArrayList<>();
                roles.add(clientRole.get());
                newClient.setRoles(roles); */
            }else{
                return new ResponseModel(HttpStatus.BAD_REQUEST.value(), String.format(Message.INVALID_ID,"Role"), null);
            }
            
            userRepository.save(newClient);
            return new ResponseModel(HttpStatus.CREATED.value(), String.format(Message.SUCCESS_CREATE, "User"), newClient);
            // modelMapper.getConfiguration().setSkipNullEnabled(true);
            
        } catch (Exception e) {
            log.error("Unexpected error creating user: {}", e.getMessage(), e);
            return new ResponseModel(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Unexpected error creating User", e.getMessage());
        }
    }

    @Override
    public ResponseModel createAdminUser(CreateUserDto dto) {
        try {
            Optional<User> adminExists = userRepository.findByEmail(dto.getEmail());

            if(adminExists.isPresent()){
                return new ResponseModel(HttpStatus.BAD_REQUEST.value(), String.format(Message.ALREADY_EXISTS,"Email"), dto.getEmail());
            }

            ModelMapper modelMapper = new ModelMapper();
            User newAdmin = modelMapper.map(dto, User.class);
            newAdmin.setUserCode("ADMIN-" + UUID.randomUUID().toString().replace("-", "").substring(0, 8));
            newAdmin.setEmail(dto.getEmail());
            newAdmin.setName(dto.getName());
            newAdmin.setPassword(encoder.encode(dto.getPassword()));


            /* CHECK IF ROLE EXISTS AND ASSIGN TO CLIENT */
            Optional<Role> adminRole = this.roleRepository.findById(2L);
            if(adminRole.isPresent()){
                Role role = adminRole.get();
                Set<Role> roles = new HashSet<>();
                roles.add(role);
                newAdmin.setRoles(roles);
            }else{
                return new ResponseModel(HttpStatus.BAD_REQUEST.value(), String.format(Message.INVALID_ID,"Role"), null);
            }
            
            userRepository.save(newAdmin);
            return new ResponseModel(HttpStatus.CREATED.value(), String.format(Message.SUCCESS_CREATE, "User"), newAdmin);
            // modelMapper.getConfiguration().setSkipNullEnabled(true);
            
        } catch (Exception e) {
            log.error("Unexpected error creating user: {}", e.getMessage(), e);
            return new ResponseModel(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Unexpected error creating User", e.getMessage());
        }
    }

    @Override
    public BaseResponseDto signIn(SignInDto dto) {
            Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(),
                    dto.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            CustomUserDetailImpl userDetails = (CustomUserDetailImpl) authentication.getPrincipal();

            String jwt = jwtUtils.generateTokenFromEmail(userDetails.getEmail());

            User user = getUserByEmail(userDetails.getEmail());
            UserInfoResponseDto userInfo = new UserInfoResponseDto(jwt, user);

            return new BaseResponseDto(HttpStatus.OK, String.format(Message.SUCCESS_VALIDATE, "User"), userInfo);
    }


    public  User getUserByUserCode(String userCode) {
        Optional<User> userFromDb = userRepository.findByUserCode(userCode);
        if (!userFromDb.isPresent()) {
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
              String.format("User with id %s not present", userCode));
        }
    
        return userFromDb.get();
    }

    public User getUserByEmail(String userEmail) {
        Optional<User> userFromDb = userRepository.findByEmail(userEmail);
        if (!userFromDb.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
            String.format("User with email %s not present", userEmail));
        }
    
        return userFromDb.get();
      }
}
