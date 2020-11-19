package com.swordmaster.excalibur.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swordmaster.excalibur.dto.AccountDTO;
import com.swordmaster.excalibur.dto.ResponseObject;
import com.swordmaster.excalibur.dto.SignInAccountDTO;
import com.swordmaster.excalibur.dto.SignUpAccountDTO;
import com.swordmaster.excalibur.enumclass.UserRole;
import com.swordmaster.excalibur.service.AccountService;
import com.swordmaster.excalibur.vo.SecurityUser;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/accounts/*")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @ApiOperation(value = "(deprecated) 구글 로그인/회원가입", notes = "구글에서 리다이렉션을 받아 구글 로그인/회원가입을 진행합니다.")
    @GetMapping("/signin/google")
    public ResponseEntity<AccountDTO> googleSignin (@RequestParam(value="code") String authCode, @RequestParam(value="role") String role) throws JsonProcessingException {
        UserRole userRole = UserRole.valueOf(role.toUpperCase());

        return accountService.googleSignin(authCode, userRole);
    }

    @ApiOperation(value="회원가입", notes = "정보를 받아 회원가입을 진행하고 결과에 따른 응답메세지를 반환합니다.")
    @PostMapping("/signup")
    public ResponseEntity<ResponseObject> signUp(@RequestBody SignUpAccountDTO signUpAccountDTO) {
        return accountService.signUp(signUpAccountDTO);
    }

    @ApiOperation(value="로그인", notes="로그인 성공 시 로그인 된 유저 정보와 jwt 토큰을 반환합니다.")
    @PostMapping("/signin")
    public ResponseEntity<ResponseObject> signIn(@RequestBody SignInAccountDTO signInAccountDTO) {

        return accountService.signIn(signInAccountDTO);
    }

    @ApiOperation(value = "(강의자) 개설 강의 목록 확인", notes = "해당 강의자가 개설한 강의 목록을 반환합니다.")
    @PreAuthorize("isAuthenticated() and hasRole('ROLE_TEACHER')")
    @GetMapping("/{accountId}/teacher/courses")
    public ResponseEntity<ResponseObject> teacherCourseList(@PathVariable Integer accountId) {
        return accountService.teacherCourseList(accountId);
    }

    @ApiOperation(value = "내 정보 확인", notes = "로그인한 사용자의 정보를 반환합니다.")
    @GetMapping("/myinfo")
    public ResponseEntity<ResponseObject> myinfo() {
        SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return accountService.myinfo(securityUser.getId());
    }

    @ApiOperation(value = "(수강생) 수강 강의 목록 확인", notes = "수강생이 자신이 수강 중인 강의 목록을 확인합니다.")
    @PreAuthorize("isAuthenticated() and hasRole('ROLE_STUDENT')")
    @GetMapping("/{accountId}/student/courses")
    public ResponseEntity<ResponseObject> studentCourseList(@PathVariable Integer accountId) {
        return accountService.studentCourseList(accountId);
    }

    @ApiOperation(value = "(수강생) 강의 수강 등록", notes = "수강생이 강의 코드를 통해 수강 등록합니다.")
    @PreAuthorize("isAuthenticated() and hasRole('ROLE_STUDENT')")
    @PostMapping("/{accountId}/student/courses")
    public ResponseEntity<ResponseObject> courseRegister(@PathVariable Integer accountId, @RequestBody Map<String, String> data) {
        return accountService.courseRegister(accountId, data.get("code"));
    }

}
