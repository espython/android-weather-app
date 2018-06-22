package io.farida.espython.jsonhandling;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        final TextView showJson = findViewById(R.id.json_view);





        try {
            String url = "https://api.openweathermap.org/data/2.5/forecast?q=cairo&lang={ar}&units=metric&APPID=e80e9daf057623a9298452e8e8809a81";
            GetDataAsync getdataAsync = (GetDataAsync) new GetDataAsync(new GetDataAsync.AsyncResponse(){

                @Override
                public void processFinish(String output){
                    //Here you will receive the result fired from async class
                    //of onPostExecute(result) method.
                    showJson.setText(output);

                }
            }).execute(url);

        }catch (Exception ex){

        }
    }

}
