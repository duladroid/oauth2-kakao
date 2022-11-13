package yhwang15.kakaologin;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class KakaoApi {
    public String getAccessToken(String code) {
        return null;
    }

    public HashMap<String, Object> getUserInfo(String accessToken) {
        HashMap<String, Object> userInfo = new HashMap<String, Object>();
        String reqURL = "https://kapi.kakao.com/v2/user/me"; //request, Access Token 사용 url

        try{
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + accessToken);

        } catch (Exception e){

        }

        return null;
    }
}
