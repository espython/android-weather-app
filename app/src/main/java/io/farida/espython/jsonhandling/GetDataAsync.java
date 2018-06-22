package io.farida.espython.jsonhandling;

import android.os.AsyncTask;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class GetDataAsync extends AsyncTask<String, String, String> {
    // you may separate this or combined to caller class.
    public interface AsyncResponse {
        void processFinish(String output);
    }
    public AsyncResponse delegate = null;
    public GetDataAsync(AsyncResponse asyncResponse){
        this.delegate = asyncResponse;
    }

    // onPreExcute Method
    @Override
    protected void onPreExecute(){


    }

    @Override
    protected String doInBackground(String... args) {
        try{
            String weatherData;
            // Define our URL
            URL url = new URL(args[0]);
            //make connect with url and send request
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            //waiting for 7000ms for response
            urlConnection.setConnectTimeout(7000);
            try {
                //getting the response data
                InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                //convert the stream to string
                weatherData = ConvertInputToString(inputStream);
                //send to display data
                publishProgress(weatherData);
                return weatherData;
            }finally {
                //end connection
                urlConnection.disconnect();
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {

        try {
            JSONObject json = new JSONObject(values[0]);
            System.out.println("THIS IS THE RESPONSE FROM WEATHER API :  "+json.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onPostExecute(String result) {
        delegate.processFinish(result);
    }

    private String ConvertInputToString(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        String Text = "";
        try{
            while ((line=bufferedReader.readLine()) != null){
                Text += line;
            }
            inputStream.close();
        }catch (Exception ex){

        }
        return Text;
    }
}

