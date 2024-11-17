package com.autoever.cinewall.recommend;

import com.autoever.cinewall.recommend.dto.response.RecommendResponseDto;
import com.autoever.cinewall.recommend.service.RecommendService;
import com.autoever.cinewall.user.UserEntity;
import com.autoever.cinewall.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cinewall/recommend")
@RequiredArgsConstructor
public class RecommendController {
    private final RecommendService recommendService;
    private final UserService userService;

    @GetMapping("/hashtag")
    public ResponseEntity<RecommendResponseDto> getHashtag() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        UserEntity user = userService.findById(userName);

        return recommendService.getHashtag(user);
    }
}
