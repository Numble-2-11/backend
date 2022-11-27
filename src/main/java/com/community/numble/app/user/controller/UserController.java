package com.community.numble.app.user.controller;

import com.community.numble.app.user.dto.*;
import com.community.numble.app.user.service.UserService;
import com.community.numble.common.service.ResponseService;
import com.community.numble.config.jwt.*;
import com.community.numble.system.email.domain.Email;
import com.community.numble.system.email.dto.CheckEmailTokenDto;
import com.community.numble.system.email.dto.SendEmailTokenDto;
import com.community.numble.system.email.service.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User", description = "사용자 관련 api")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    private final EmailService emailService;

    private final ResponseService responseService;


    @Operation(summary = "회원가입 메서드", description = "회원가입 메서드입니다.")
    @PostMapping(value = "/join")
    public ResponseEntity<?> register(final UserCreateDto userCreateDto){

        userService.join(userCreateDto);

        return ResponseEntity.ok(responseService.getSuccessResult());
    }
    @Operation(summary = "이메일 토큰 발급", description = "이메일 토큰 발급")
    @GetMapping("/email-token")
    public ResponseEntity<?> emailToken(SendEmailTokenDto sendEmailTokenDto)  {

        Email email = sendEmailTokenDto.toEntity();
        try{
            emailService.sendEmailToken(email);
        }catch (MessagingException e){
            e.printStackTrace();
        }

        return ResponseEntity.ok(responseService.getSuccessResult());
    }

    @Operation(summary = "발급된 토큰 체크", description = "토큰 체크")
    @GetMapping("/check-email-token")
    public ResponseEntity<?> checkEmailToken(CheckEmailTokenDto checkEmailTokenDto){

        Email checkEmail = checkEmailTokenDto.toEntity();

        boolean isCheck = emailService.checkEmailToken(checkEmail);

        if(isCheck){
            return ResponseEntity.ok(responseService.getSuccessResult());
        }else{
            return ResponseEntity.ok(responseService.getFailResult());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(responseService.getSingleResult(userService.login(loginDto)));
    }

    @PostMapping("/reissue")
    public ResponseEntity<?> reIssue(@RequestBody TokenRequestDto tokenRequestDtoDto){
        return ResponseEntity.ok(responseService.getSingleResult(userService.reissue(tokenRequestDtoDto)));
    }
}
