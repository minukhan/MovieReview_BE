package com.example.BE.user;

import com.example.BE.user.dto.EditedUserRequestDto;
import com.example.BE.user.dto.EditedUserResponseDto;
import com.example.BE.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cinewall/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/edit")
    public ResponseEntity editUser(@ModelAttribute EditedUserRequestDto requestDto) {

        System.out.println("########################## user edit ####################################");
        System.out.println(requestDto.getUserId());

        EditedUserResponseDto responseDto = userService.editUser(requestDto);

        return ResponseEntity.ok(responseDto);
    }
}
