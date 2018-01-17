package com.example.android.location;

        import org.json.JSONObject;

        import java.io.BufferedInputStream;
        import java.io.BufferedReader;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.net.HttpURLConnection;
        import java.net.URL;
public class HttpGetData {
    JSONObject data = null;

    public HttpGetData() {
    }
    public JSONObject getHttpData(String urlString){
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuffer json = new StringBuffer(1024);
            String tmp = "";

            while((tmp = reader.readLine()) != null)
                json.append(tmp).append("\n");
            reader.close();
            data = new JSONObject(json.toString());
           return data;


        } catch (Exception e) {

           e.printStackTrace();
            return null;
        }

    }
}