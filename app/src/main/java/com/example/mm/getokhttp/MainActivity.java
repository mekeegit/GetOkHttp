package com.example.mm.getokhttp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public String url = "http://192.168.100.145/api/products";
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.textV);


        FetchTask asyncTask = new FetchTask();
        asyncTask.execute();

    }

    public class FetchTask extends AsyncTask<Void, Void, String>
    {

        String asyncRes;

        protected String doInBackground(Void... voids)
        {



            try {
                asyncRes =  WebUtils.fetch(url, "AKMNA6WDAWQN5KVF15HLUIDVIVGVJEWG", null).body().string();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return new String();
        }

        protected void onPostExecute(String result)
        {
            super.onPostExecute(result);
            tv.setText(asyncRes);
        }
    }

}
