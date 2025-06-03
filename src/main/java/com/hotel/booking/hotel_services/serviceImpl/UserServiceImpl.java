package com.hotel.booking.hotel_services.serviceImpl;

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

import com.hotel.booking.hotel_services.dto.BaseResponseDto;
import com.hotel.booking.hotel_services.dto.CreateUserDto;
import com.hotel.booking.hotel_services.dto.ResponseModel;
import com.hotel.booking.hotel_services.dto.SignInDto;
import com.hotel.booking.hotel_services.dto.UserInfoResponseDto;
import com.hotel.booking.hotel_services.dto.otp.OtpTokenValidatorDto;
import com.hotel.booking.hotel_services.dto.otp.OtpValidationResult;
import com.hotel.booking.hotel_services.dto.password.UpdatePasswordDto;
import com.hotel.booking.hotel_services.email.EmailService;
import com.hotel.booking.hotel_services.email.dto.EmailMessageDto;
import com.hotel.booking.hotel_services.entity.Role;
import com.hotel.booking.hotel_services.entity.User;
import com.hotel.booking.hotel_services.exception.GlobalRequestException;
import com.hotel.booking.hotel_services.exception.Message;
import com.hotel.booking.hotel_services.repository.RoleRepository;
import com.hotel.booking.hotel_services.repository.UserRepository;
import com.hotel.booking.hotel_services.service.OtpService;
import com.hotel.booking.hotel_services.service.UserService;
import com.hotel.booking.hotel_services.utils.JwtUtils;

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

    private final OtpService otpService;
    private final EmailService emailService;


    @Override
    public ResponseModel createClientUser(CreateUserDto dto) {
        try {
            Optional<User> clientExists = userRepository.findByEmail(dto.getEmail());

            if (clientExists.isPresent()) {
                return new ResponseModel(HttpStatus.CONFLICT.value(),String.format(Message.ALREADY_EXISTS, "User"),null);
            }
            ModelMapper modelMapper = new ModelMapper();
            User newClient = modelMapper.map(dto, User.class);
            newClient.setUserCode("USER-" + UUID.randomUUID().toString().replace("-", "").substring(0, 8));
            newClient.setEmail(dto.getEmail());
            newClient.setName(dto.getName());
            newClient.setPassword(encoder.encode(dto.getPassword()));

            Optional<Role> clientRole = this.roleRepository.findById(1L);
            if (clientRole.isEmpty()) {
                return new ResponseModel(
                    HttpStatus.BAD_REQUEST.value(),
                    String.format(Message.INVALID_ID, "Role"),
                    null
                );
            }
    
            Set<Role> roles = new HashSet<>();
            roles.add(clientRole.get());
            newClient.setRoles(roles);
            
            userRepository.save(newClient);

            String OTPToken = otpService.generateOtp();
            otpService.saveOtp(newClient.getId(), OTPToken);

            String subject = "Your OTP Code";
            String body = String.format("Hello %s,\n\nYour OTP code is: %s\n\nThanks,\nTeam", dto.getName(), OTPToken);

            // Send email via RabbitMQ
            emailService.sendEmail(new EmailMessageDto(newClient.getEmail(), subject, body));

            return new ResponseModel(HttpStatus.CREATED.value(), String.format(Message.SUCCESS_CREATE, "User"), newClient);
            // modelMapper.getConfiguration().setSkipNullEnabled(true);
            
        } catch(GlobalRequestException e) {
            return new ResponseModel(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
        
        } catch (Exception e) {
            log.error("Unexpected error creating user: {}", e.getMessage(), e);
            return new ResponseModel(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Unexpected error creating User", e.getMessage());
        }
    }

    /* @Override
    public ResponseModel createAdminUser(CreateUserDto dto) {
        try {
            Optional<User> adminExists = userRepository.findByEmail(dto.getEmail());

            if (adminExists.isPresent()) {
                return new ResponseModel(HttpStatus.CONFLICT.value(),String.format(Message.ALREADY_EXISTS, "User"),null);
            }
            ModelMapper modelMapper = new ModelMapper();
            User newAdmin = modelMapper.map(dto, User.class);
            newAdmin.setUserCode("ADMIN-" + UUID.randomUUID().toString().replace("-", "").substring(0, 8));
            newAdmin.setEmail(dto.getEmail());
            newAdmin.setName(dto.getName());
            newAdmin.setPassword(encoder.encode(dto.getPassword()));

            Optional<Role> adminRole = this.roleRepository.findById(2L);
            if (adminRole.isEmpty()) {
                return new ResponseModel(
                    HttpStatus.BAD_REQUEST.value(),
                    String.format(Message.INVALID_ID, "Role"),
                    null
                );
            }
    
            Set<Role> roles = new HashSet<>();
            roles.add(adminRole.get());
            newAdmin.setRoles(roles);
            
            userRepository.save(newAdmin);

            String OTPToken = otpService.generateOtp();
            otpService.saveOtp(newAdmin.getId(), OTPToken);

            String subject = "Your OTP Code";
            String body = String.format("Hello %s,\n\nYour OTP code is: %s\n\nThanks,\nTeam", dto.getName(), OTPToken);

            // Send email via RabbitMQ
            emailService.sendEmailAsync(newAdmin.getEmail(), subject, body);

            return new ResponseModel(HttpStatus.CREATED.value(), String.format(Message.SUCCESS_CREATE, "User"), newAdmin);
        } catch(GlobalRequestException e) {
            return new ResponseModel(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
        
        } catch (Exception e) {
            log.error("Unexpected error creating user: {}", e.getMessage(), e);
            return new ResponseModel(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Unexpected error creating User", e.getMessage());
        }
    }
 */
    @Override
    public BaseResponseDto signIn(SignInDto dto) {
        try {
            Authentication authentication = authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(),
                dto.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            CustomUserDetailImpl userDetails = (CustomUserDetailImpl) authentication.getPrincipal();

            String jwt = jwtUtils.generateTokenFromEmail(userDetails.getEmail());

            Optional<User> user = userRepository.findByEmail(userDetails.getEmail());

            UserInfoResponseDto userInfo = new UserInfoResponseDto(jwt, user.get());

            return new BaseResponseDto(HttpStatus.OK, String.format(Message.SUCCESS_VALIDATE, "User"), userInfo);
        } catch(GlobalRequestException e) {
            return new BaseResponseDto(HttpStatus.BAD_REQUEST, e.getMessage(), null);
        
        } catch (Exception e) {
            log.error("Unexpected error signing in: {}", e.getMessage(), e);
            return new BaseResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error signing in", e.getMessage());
        }
        
    }

    @Override
    public BaseResponseDto verifyOtpCode(OtpTokenValidatorDto dto) {
        try {
            User user = userRepository.findByEmail(dto.getEmail()).orElseThrow( () -> new GlobalRequestException(String.format(Message.NOT_FOUND, "User"), HttpStatus.NOT_FOUND));
            OtpValidationResult result = otpService.validateOtp(user.getId(), dto.getOtpCode());
          
            if(result.isSuccess()){
                user.setValidated(true);
                userRepository.save(user);

                String subject = "Your Account has been verified";
                String body = String.format("Hello %s,\n\nYour Account has been verified.\n\nThanks,\nTeam", user.getName());

                emailService.sendEmail(new EmailMessageDto(user.getEmail(), subject, body));

                return new BaseResponseDto(HttpStatus.OK, String.format(Message.SUCCESS_VALIDATE, "User"), null);
            }

            return new BaseResponseDto(HttpStatus.BAD_REQUEST, result.getMessage(), null);
        } catch(GlobalRequestException e) {
            return new BaseResponseDto(HttpStatus.BAD_REQUEST, e.getMessage(), null);
        
        } catch (Exception e) {
            log.error("Unexpected error signing in: {}", e.getMessage(), e);
            return new BaseResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error verifying otp code", e.getMessage());
        }
    }

    @Override
    public BaseResponseDto resendOtpCode(String email) {
        try {
            Optional<User> user = userRepository.findByEmail(email); 

            if(user.isEmpty()) {
                return new BaseResponseDto(HttpStatus.BAD_REQUEST, String.format(Message.NOT_FOUND, "User"), null);
            }
            User userObj = user.get();
            String OTPToken = otpService.generateOtp();
            otpService.saveOtp(userObj.getId(), OTPToken);

            String subject = "Your OTP Code";
            String body = String.format("Hello %s,\n\nYour OTP code is: %s\n\nThanks,\nTeam", userObj.getName(), OTPToken);

            emailService.sendEmail(new EmailMessageDto(userObj.getEmail(), subject, body));

            return new BaseResponseDto(HttpStatus.OK, String.format(Message.SUCCESS_VALIDATE, "User"), null);
        } catch(GlobalRequestException e) {
            return new BaseResponseDto(HttpStatus.BAD_REQUEST, e.getMessage(), null);
        
        } catch (Exception e) {
            log.error("Unexpected error signing in: {}", e.getMessage(), e);
            return new BaseResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error verifying otp code", e.getMessage());
        }
        
    }

    @Override
    public BaseResponseDto updatePassword(UpdatePasswordDto dto) {
        try {
            Optional<User> user = userRepository.findByEmail(dto.getUserCode());
            if(user.isEmpty()) {
                return new BaseResponseDto(HttpStatus.BAD_REQUEST, String.format(Message.NOT_FOUND, "User"), null);
            }
            User userObj = user.get();
            String oldPassword = dto.getOldPassword();
            String newPassword = dto.getNewPassword();

            if (!encoder.matches(oldPassword, userObj.getPassword())) {
                return new BaseResponseDto(HttpStatus.BAD_REQUEST, String.format(Message.INVALID_PASSWORD, "Password"), null);
            }
            userObj.setPassword(encoder.encode(newPassword));
            userRepository.save(userObj);
            return new BaseResponseDto(HttpStatus.OK, String.format(Message.SUCCESS_UPDATE, "Password"), null);
        } catch(GlobalRequestException e) {
            return new BaseResponseDto(HttpStatus.BAD_REQUEST, e.getMessage(), null);
    
        } catch (Exception e) {
            log.error("Unexpected error updating password: {}", e.getMessage(), e);
            return new BaseResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error updating password", e.getMessage());
        }
    }
    
}
