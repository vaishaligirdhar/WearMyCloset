package com.allandroidprojects.ecomsample.startup;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.allandroidprojects.ecomsample.R;
import com.allandroidprojects.ecomsample.utility.ImageUrlUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

public class ServerConnectActivityActivity extends AppCompatActivity {

    ImageUrlUtils iuu = new ImageUrlUtils();

    String men_name = "";
    String men_price = "";
    String men_desc = "";
    String men_image = "";
    String men_contact = "";

    String women_name = "";
    String women_price = "";
    String women_desc = "";
    String women_image = "";
    String women_contact = "";

    String clothing_name = "";
    String clothing_price = "";
    String clothing_desc = "";
    String clothing_image = "";
    String clothing_contact = "";

    String acc_name = "";
    String acc_price = "";
    String acc_desc = "";
    String acc_image = "";
    String acc_contact = "";

    String shoes_name = "";
    String shoes_price = "";
    String shoes_desc = "";
    String shoes_image = "";
    String shoes_contact = "";

    String results[] = new String[]{};
    public static Integer any = new Integer(0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.server_connect_activity);
        connect();
    }

    public String ReadBufferedHTML(BufferedReader reader, char [] htmlBuffer, int bufSz) throws java.io.IOException
    {
        htmlBuffer[0] = '\0';
        int offset = 0;
        do {
            int cnt = reader.read(htmlBuffer, offset, bufSz - offset);
            if (cnt > 0) {
                offset += cnt;
            } else {
                break;
            }
        } while (true);
        return new String(htmlBuffer);
    }

    public String getJsonPage(String url) {
        HttpURLConnection conn_object = null;
        final int HTML_BUFFER_SIZE = 2*1024*1024;
        char htmlBuffer[] = new char[HTML_BUFFER_SIZE];

        try {
            URL url_object = new URL(url);
            conn_object = (HttpURLConnection) url_object.openConnection();
            conn_object.setInstanceFollowRedirects(true);

            BufferedReader reader_list = new BufferedReader(new InputStreamReader(conn_object.getInputStream()));
            String HTMLSource = ReadBufferedHTML(reader_list, htmlBuffer, HTML_BUFFER_SIZE);
            reader_list.close();
            return HTMLSource;
        } catch (Exception e) {
            return "Fail to login";
        } finally {
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            if (conn_object != null) {
                conn_object.disconnect();
            }
        }
    }


    public String[] connect(){

        final String url = "https://i.cs.hku.hk/~h3517511/dbHandler.php?request=01&param=name";
        final String url1 = "https://i.cs.hku.hk/~h3517511/dbHandler.php?request=01&param=price";
        final String url2 = "https://i.cs.hku.hk/~h3517511/dbHandler.php?request=01&param=description";
        final String url3 = "https://i.cs.hku.hk/~h3517511/dbHandler.php?request=01&param=image";
        final String urlc = "https://i.cs.hku.hk/~h3517511/dbHandler.php?request=01&param=contact";

        final String url4 = "https://i.cs.hku.hk/~h3517511/dbHandler.php?request=00&param=name";
        final String url5 = "https://i.cs.hku.hk/~h3517511/dbHandler.php?request=00&param=price";
        final String url6 = "https://i.cs.hku.hk/~h3517511/dbHandler.php?request=00&param=description";
        final String url7 = "https://i.cs.hku.hk/~h3517511/dbHandler.php?request=00&param=image";
        final String url1c = "https://i.cs.hku.hk/~h3517511/dbHandler.php?request=00&param=contact";

        final String url8 = "https://i.cs.hku.hk/~h3517511/dbHandler.php?request=11&param=name";
        final String url9 = "https://i.cs.hku.hk/~h3517511/dbHandler.php?request=11&param=price";
        final String url10 = "https://i.cs.hku.hk/~h3517511/dbHandler.php?request=11&param=description";
        final String url11 = "https://i.cs.hku.hk/~h3517511/dbHandler.php?request=11&param=image";
        final String url2c = "https://i.cs.hku.hk/~h3517511/dbHandler.php?request=11&param=contact";

        final String url12 = "https://i.cs.hku.hk/~h3517511/dbHandler.php?request=10&param=name";
        final String url13 = "https://i.cs.hku.hk/~h3517511/dbHandler.php?request=10&param=price";
        final String url14 = "https://i.cs.hku.hk/~h3517511/dbHandler.php?request=10&param=description";
        final String url15 = "https://i.cs.hku.hk/~h3517511/dbHandler.php?request=10&param=image";
        final String url3c = "https://i.cs.hku.hk/~h3517511/dbHandler.php?request=10&param=contact";

        final String url16 = "https://i.cs.hku.hk/~h3517511/dbHandler.php?request=12&param=name";
        final String url17 = "https://i.cs.hku.hk/~h3517511/dbHandler.php?request=12&param=price";
        final String url18 = "https://i.cs.hku.hk/~h3517511/dbHandler.php?request=12&param=description";
        final String url19 = "https://i.cs.hku.hk/~h3517511/dbHandler.php?request=12&param=image";
        final String url4c = "https://i.cs.hku.hk/~h3517511/dbHandler.php?request=12&param=contact";

        AsyncTask<String, Void, String> task = new AsyncTask<String, Void, String>() {
            boolean success;

            @Override
            protected String doInBackground(String... arg0) {
                // TODO Auto-generated method stub
                success = true;
                men_name = getJsonPage(url);
                men_price = getJsonPage(url1);
                men_desc = getJsonPage(url2);
                men_image = getJsonPage(url3);
                men_contact = getJsonPage(urlc);

                women_name = getJsonPage(url4);
                women_price = getJsonPage(url5);
                women_desc = getJsonPage(url6);
                women_image = getJsonPage(url7);
                women_contact = getJsonPage(url1c);

                acc_name = getJsonPage(url8);
                acc_price = getJsonPage(url9);
                acc_desc = getJsonPage(url10);
                acc_image = getJsonPage(url11);
                acc_contact = getJsonPage(url2c);

                clothing_name = getJsonPage(url12);
                clothing_price = getJsonPage(url13);
                clothing_desc = getJsonPage(url14);
                clothing_image = getJsonPage(url15);
                clothing_contact = getJsonPage(url3c);

                shoes_name = getJsonPage(url16);
                shoes_price = getJsonPage(url17);
                shoes_desc = getJsonPage(url18);
                shoes_image = getJsonPage(url19);
                shoes_contact = getJsonPage(url4c);

                if (men_name.equals("Fail to login"))
                    success = false;
                return null;

            }

            @Override
            protected void onPostExecute(String result) {
                ImageUrlUtils.men_name = Arrays.copyOf(men_name.split(";", 0), men_name.split(";", 0).length-1);
                ImageUrlUtils.men_price = Arrays.copyOf(men_price.split(";", 0), men_price.split(";", 0).length-1);
                ImageUrlUtils.men_desc = Arrays.copyOf(men_desc.split(";", 0), men_desc.split(";", 0).length-1);
                ImageUrlUtils.men_image = Arrays.copyOf(men_image.split(";", 0), men_image.split(";", 0).length-1);
                ImageUrlUtils.men_contact = Arrays.copyOf(men_contact.split(";", 0), men_contact.split(";", 0).length-1);

                ImageUrlUtils.women_name = Arrays.copyOf(women_name.split(";", 0), women_name.split(";", 0).length-1);
                ImageUrlUtils.women_price = Arrays.copyOf(women_price.split(";", 0), women_price.split(";", 0).length-1);
                ImageUrlUtils.women_desc = Arrays.copyOf(women_desc.split(";", 0), women_desc.split(";", 0).length-1);
                ImageUrlUtils.women_image = Arrays.copyOf(women_image.split(";", 0), women_image.split(";", 0).length-1);
                ImageUrlUtils.women_contact = Arrays.copyOf(women_contact.split(";", 0), women_contact.split(";", 0).length-1);

                ImageUrlUtils.acc_name = Arrays.copyOf(acc_name.split(";", 0), acc_name.split(";", 0).length-1);
                ImageUrlUtils.acc_price = Arrays.copyOf(acc_price.split(";", 0), acc_price.split(";", 0).length-1);
                ImageUrlUtils.acc_desc = Arrays.copyOf(acc_desc.split(";", 0), acc_desc.split(";", 0).length-1);
                ImageUrlUtils.acc_image = Arrays.copyOf(acc_image.split(";", 0), acc_image.split(";", 0).length-1);
                ImageUrlUtils.acc_contact = Arrays.copyOf(acc_contact.split(";", 0), acc_contact.split(";", 0).length-1);

                ImageUrlUtils.clothing_name = Arrays.copyOf(clothing_name.split(";", 0), clothing_name.split(";", 0).length-1);
                ImageUrlUtils.clothing_price = Arrays.copyOf(clothing_price.split(";", 0), clothing_price.split(";", 0).length-1);
                ImageUrlUtils.clothing_desc = Arrays.copyOf(clothing_desc.split(";", 0), clothing_desc.split(";", 0).length-1);
                ImageUrlUtils.clothing_image = Arrays.copyOf(clothing_image.split(";", 0), clothing_image.split(";", 0).length-1);
                ImageUrlUtils.clothing_contact = Arrays.copyOf(clothing_contact.split(";", 0), clothing_contact.split(";", 0).length-1);

                ImageUrlUtils.shoes_name = Arrays.copyOf(shoes_name.split(";", 0), shoes_name.split(";", 0).length-1);
                ImageUrlUtils.shoes_price = Arrays.copyOf(shoes_price.split(";", 0), shoes_price.split(";", 0).length-1);
                ImageUrlUtils.shoes_desc = Arrays.copyOf(shoes_desc.split(";", 0), shoes_desc.split(";", 0).length-1);
                ImageUrlUtils.shoes_image = Arrays.copyOf(shoes_image.split(";", 0), shoes_image.split(";", 0).length-1);
                ImageUrlUtils.shoes_contact = Arrays.copyOf(shoes_contact.split(";", 0), shoes_contact.split(";", 0).length-1);

                startActivity(new Intent(ServerConnectActivityActivity.this, MainActivity.class));
                finishActivity(1);
            }
        }.execute("");

        return results;
    }

}
