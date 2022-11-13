package yhwang15.kakaologin;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class KakaoApi {
    public String getAccessToken(String code) {
        String accessToken = "";
        String refreshToken = "";
        String reqURL = "https://kapi.kakao.com/oauth/token";

        try{
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("grant_type = authorization_code");
            stringBuilder.append("&client_id=50229b49e1874f7ccd32a3470eab23a3");
            stringBuilder.append("&redirect_uri=http://localhost:8000/login");
            stringBuilder.append("&code="+code);

            bufferedWriter.write(stringBuilder.toString());
            bufferedWriter.flush();

            int responseCode = conn.getResponseCode();
            System.out.println("response code = " + responseCode);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";
            String result = "";
            while((line = bufferedReader.readLine())!= null){
                result += line;
            }
            System.out.println("response body = " + result);

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            accessToken = element.getAsJsonObject().get("access_Token").getAsString();
            refreshToken = element.getAsJsonObject().get("refresh_Token").getAsString();

            bufferedReader.close();
            bufferedWriter.close();


        }catch (Exception e){
            e.printStackTrace();

        }

        return accessToken;
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
            String email = kakaoAcount.getAsJsonObject().get("email").getAsString();

            userInfo.put("nickname", nickname);
            userInfo.put("email", email);

        } catch (Exception e){
            e.printStackTrace();
        }
        return userInfo;

    }


}
