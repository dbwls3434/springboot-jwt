package org.yujin.myproc.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yujin.myproc.dto.UserDTO;
import org.yujin.myproc.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Slf4j
public class UserAPIController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<?> joinProc(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult) throws Exception {

        UserDTO savedUserDTO = userService.saveUser(userDTO);

        return ResponseEntity.ok().body(savedUserDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginProc(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult) throws Exception {

        UserDTO loginUserDTO = userService.loginUser(userDTO);

        return ResponseEntity.ok().body(loginUserDTO);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logoutProc() {

        //

        return ResponseEntity.ok().body("success");
    }

}
