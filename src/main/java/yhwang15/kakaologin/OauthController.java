package yhwang15.kakaologin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@RestController
public class OauthController {

    KakaoApi kakaoApi = new KakaoApi();

    @RequestMapping(value="/login")
    public ModelAndView login(@RequestParam("code") String code, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();

        //1번 인증코드 요청 전달
        String accessToken = kakaoApi.getAccessToken(code);

        //2번 인증코드로 토큰 전달
        HashMap<String, Object> userInfo = kakaoApi.getUserInfo(accessToken);

        System.out.println("login info: " + userInfo.toString());

        if(userInfo.get("email") != null){
            session.setAttribute("userId", userInfo.get("email"));
            session.setAttribute("access_token", accessToken);
        }
        modelAndView.addObject("userId",userInfo.get("email"));
        modelAndView.setViewName("index");

        return modelAndView;

    }

}
