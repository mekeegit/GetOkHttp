package com.example.mm.getokhttp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.xml.sax.InputSource;

import java.io.IOException;
import java.io.StringReader;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public String url = "http://192.168.100.4/api/products";
    TextView tv;
    private static final MediaType mType = MediaType
            .parse("text/xml; charset=utf-8");
    Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.textV);
        product = new Product();


        FetchTask asyncTask = new FetchTask();
        asyncTask.execute();

    }


    public class FetchTask extends AsyncTask<Void, Void, String> {

        String asyncRes;
        String p6;


        protected String doInBackground(Void... voids) {


            String response = null;

            try {

                p6 = WebUtils.fetch(url, "4XTLVWCG6G48WB7A8A2L99Q21CEALTVQ", null).body().string();
                Log.d("HEREiAM", "before");
                //asyncRes = WebUtils.delete(6,url,"4XTLVWCG6G48WB7A8A2L99Q21CEALTVQ", null).body().string();
                Log.d("HEREiAM", "afterdelete");
                asyncRes = WebUtils.post(6,url, "4XTLVWCG6G48WB7A8A2L99Q21CEALTVQ", null, product).body().string();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            tv.setText(asyncRes);
        }

        // InputSource inputSource = new InputSource(new StringReader(asyncRes));
    }

    public void mDelete(int id) throws Exception {
        OkHttpClient httpclient = new OkHttpClient();
        Request request = new Request.Builder().url(url + "/" + id).delete().build();
        try (Response response = httpclient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new RuntimeException("Failed to delete book with id:" + id);

            }
        }
    }

}
