package yhwang15.kakaologin;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode " + responseCode);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";
            String result = "";

            while((line = bufferedReader.readLine()) != null){
                result += line;
            }
            System.out.println("response body = " + result);


            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);
            JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
            JsonObject kakaoAcount = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

            String nickname = properties.getAsJsonObject().get("nickname").getAsString();
            String email = properties.getAsJsonObject().get("email").getAsString();


        } catch (Exception e){

        }

        return null;
    }
}
