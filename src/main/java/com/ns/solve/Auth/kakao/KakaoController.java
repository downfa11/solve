package com.ns.solve.Auth.kakao;

import com.ns.solve.Auth.naver.NaverService;
import com.ns.solve.Domain.dto.MessageEntity;
import com.ns.solve.Service.MembershipService;
import com.ns.solve.Utils.JwtToken;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("kakao")
public class KakaoController {

    private final KakaoService kakaoService;
    private final NaverService naverService;
    private final MembershipService membershipService;

    @RequestMapping(value="/", method= RequestMethod.GET)
    public String login(Model model) {
        model.addAttribute("kakaoUrl", kakaoService.getKakaoLogin());
        model.addAttribute("naverUrl", naverService.getNaverLogin());
        return "index";
    }

    @GetMapping("/login")
    public RedirectView login(){
        return new RedirectView(kakaoService.getKakaoLogin());
    }

    @GetMapping("/callback")
    public ResponseEntity<MessageEntity> callback(HttpServletRequest request) throws Exception {
        KakaoDTO kakaoInfo = kakaoService.getKakaoInfo(request.getParameter("code"));

        Long id = kakaoInfo.getId();
        JwtToken token = membershipService.LoginMembership(id);

        if (token!=null)
            return ResponseEntity.ok()
                    .body(new MessageEntity("Success ", token));
        else return ResponseEntity.ok()
                .body(new MessageEntity("Fail ","token is empty.")); // empty시 register시켜야함.
    }



}
