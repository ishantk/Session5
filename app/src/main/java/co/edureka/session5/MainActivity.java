package co.edureka.session5;

import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v4.util.LogWriter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,SharedPreferences.OnSharedPreferenceChangeListener{

    EditText eTxt;
    Button btn;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    String prefs_name = "quotes"; // it can be any name

    void initViews(){
        eTxt = (EditText)findViewById(R.id.editText);
        btn = (Button)findViewById(R.id.button);
        btn.setOnClickListener(this);

        btn.setText("Read");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        readFromPreferences();
    }

    void writeInternal(){

        try {
            String quote = eTxt.getText().toString().trim();

            FileOutputStream fos = openFileOutput("quotes.txt", MODE_PRIVATE);
            //FileOutputStream fos = openFileOutput("quotes.txt", MODE_APPEND); // For Appending the Data...

            fos.write(quote.getBytes());

            fos.close();

            eTxt.setText("");
            Toast.makeText(this,"Contents Written", Toast.LENGTH_LONG).show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    void readInternal(){

        try {

            FileInputStream fis = openFileInput("quotes.txt");
            InputStreamReader reader = new InputStreamReader(fis);
            BufferedReader buffer = new BufferedReader(reader);

            String data = buffer.readLine();

            buffer.close();
            reader.close();
            fis.close();

            eTxt.setText(data);
            Toast.makeText(this,"Contents Read", Toast.LENGTH_LONG).show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    void writeExternal(){
        try {

            String quote = eTxt.getText().toString().trim();

            String path = Environment.getExternalStorageDirectory().getAbsolutePath(); // Path of SD Card | External

            //File file = new File(path+"/myFolder/quotes.txt");

            File file = new File(path,"quotes.txt");

            FileOutputStream fos = new FileOutputStream(file);

            fos.write(quote.getBytes());

            fos.close();

            eTxt.setText("");
            Toast.makeText(this,"Contents Written", Toast.LENGTH_LONG).show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    void readExternal(){

        try {

            String path = Environment.getExternalStorageDirectory().getAbsolutePath(); // Path of SD Card | External

            //File file = new File(path+"/myFolder/quotes.txt");

            File file = new File(path,"quotes.txt");

            FileInputStream fis = new FileInputStream(file);
            InputStreamReader reader = new InputStreamReader(fis);
            BufferedReader buffer = new BufferedReader(reader);

            String data = buffer.readLine();

            buffer.close();
            reader.close();
            fis.close();

            eTxt.setText(data);
            Toast.makeText(this,"Contents Read", Toast.LENGTH_LONG).show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    void writeInPreferences(){

        preferences = getSharedPreferences(prefs_name,MODE_PRIVATE);
        preferences.registerOnSharedPreferenceChangeListener(this);

        editor = preferences.edit(); // you wish to write the data in preferences | preferences -> xml file with data as key value pair

        String quote = eTxt.getText().toString().trim();

        editor.putString("keyQuote",quote);
        editor.putInt("keyRating",5);

        //editor.commit(); // is to save data in preferences
        editor.apply();    // is to save data in preferences in background | Thread shall save the data in bground

        eTxt.setText("");
        Toast.makeText(this,"Contents Written", Toast.LENGTH_LONG).show();

    }


    void readFromPreferences(){

        preferences = getSharedPreferences(prefs_name,MODE_PRIVATE);

        if(preferences.contains("keyQuote") && preferences.contains("keyRating")){
            String quote = preferences.getString("keyQuote","NA");
            int rating = preferences.getInt("keyRating",0);

            eTxt.setText(quote+" | "+rating);
        }

    }

    @Override
    public void onClick(View view) {
        //writeInternal();
        //readInternal();
        //writeExternal();
        //readExternal();
        //writeInPreferences();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {

    }
}
