package kr.easw.lesson06.controller;

import jakarta.servlet.http.HttpServletResponse;
import kr.easw.lesson06.model.dto.ExceptionalResultDto;
import kr.easw.lesson06.model.dto.UserDataEntity;
import kr.easw.lesson06.service.UserDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Console;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class UserAuthEndpoint {
    private final UserDataService userDataService;


    // JWT 인증을 위해 사용되는 엔드포인트입니다.
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UserDataEntity entity) {
        try {
            // 로그인을 시도합니다.
            return ResponseEntity.ok(userDataService.createTokenWith(entity));
        } catch (Exception ex) {
            // 만약 로그인에 실패했다면, 400 Bad Request를 반환합니다.
            return ResponseEntity.badRequest().body(new ExceptionalResultDto(ex.getMessage()));
        }
    }

    @PostMapping("/register")
    public void register(@RequestBody UserDataEntity entity, HttpServletResponse response) throws IOException {
        // 유저 회원가입을 구현하십시오.
        // 해당 메서드를 작성하기 위해서는, UserDataService와 admin_dashboard.html을 참고하십시오.
        // 해당 메서드는 register.html에서 사용됩니다.
        String inputID = entity.getUserId();
        String inputPassword = entity.getPassword();

        System.out.println(inputID +" / "+inputPassword);
        if(inputID.isBlank() || inputID.isEmpty() || inputPassword.isEmpty() || inputPassword.isBlank())
        {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        }
        else if(userDataService.isUserExists(entity.getUserId())) {
            response.setStatus(HttpStatus.CONFLICT.value()); //이미 가입된 사용자
        }
        else
        {
            userDataService.createUser(entity);
            response.sendRedirect("/");
        }
    }
}
