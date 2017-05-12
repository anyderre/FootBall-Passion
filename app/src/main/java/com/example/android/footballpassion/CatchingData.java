package com.example.android.footballpassion;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.view.View;
import android.webkit.HttpAuthHandler;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.CopyOnWriteArraySet;

import static android.R.attr.apiKey;
import static com.example.android.footballpassion.R.id.responseView;

/**
 * Created by Dany on 31/03/2017.
 */

public class CatchingData extends AsyncTask<String, String, String> {


    private Context _context;
    private TextView _textView;
    private String _apiKey;
    private ProgressBar _progressBar;



    public CatchingData( Context context, TextView textView, ProgressBar progressbar) {

        _apiKey = "1656da08597e46fba8b0d7950428cdf7";
        _context = context;
        _textView = textView;
        _progressBar= progressbar;
   


    }
    @Override
    protected void onPreExecute() {

        _progressBar.setVisibility(ProgressBar.VISIBLE);
        _textView.setText("");
    }

    //URL url = new URL(_apiURL +"?q=email="+_email+ "&apiKey="+apiKey)
    @Override
    protected String doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);

            HttpURLConnection urlConnection =  (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Accept","application/json");
            urlConnection.setRequestProperty("X-Auth-Token",_apiKey);
            urlConnection.setRequestProperty("X-Response-Control","minified");
            urlConnection.setConnectTimeout(10000);

            try{
                if(urlConnection.getResponseCode()!=200){
                    throw new RuntimeException("Failed: http Error code: "+ urlConnection.getResponseCode());
                }
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line= bufferedReader.readLine())!= null){
                    stringBuilder.append(line).append("\n");
                    System.out.println(line);
                }
                bufferedReader.close();

                return stringBuilder.toString();

            }
            finally {
                urlConnection.disconnect();
            }
        }
        catch (Exception e){

            e.printStackTrace();
            Log.e("ERROR", e.getMessage());


        }
        return null;
    }


    // Verifying Network connection
    public boolean isConnected(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(MainActivity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isConnected()){
            return true;
        }
        return false;
    }
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        String text = "";
        try{
            JSONArray jsonArray = new JSONArray(result);
            JSONObject  jsonObject = jsonArray.getJSONObject(0);
            String fixtures = jsonObject.getString("fixtures");
            _textView.setText(fixtures);
            System.out.println(fixtures);

        }
        catch (JSONException jse){
            jse.printStackTrace();

        }
    }


    public Context get_context() {
        return _context;
    }

    public void set_context(Context _context) {
        this._context = _context;
    }

    public TextView get_textView() {
        return _textView;
    }

    public void set_textView(TextView _textView) {
        this._textView = _textView;
    }

    public String get_apiKey() {
        return _apiKey;
    }

    public void set_apiKey(String _apiKey) {
        this._apiKey = _apiKey;
    }

}
