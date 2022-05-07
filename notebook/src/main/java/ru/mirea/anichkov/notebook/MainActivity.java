package ru.mirea.anichkov.notebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    private EditText fileText, fileName;
    private static final String TAG = "Notebook";
    private SharedPreferences preferences;
    private final String TAG_NAME = "Notebook name";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fileName = findViewById(R.id.FileNameEditText);
        fileText = findViewById(R.id.FileTextEditText);
        preferences = getPreferences(MODE_PRIVATE);

        new Thread(new Runnable() {
            @Override
            public void run() {
                loadPreferences();
                loadFile();
            }
        }).start();
    }

    public void loadPreferences(){
        String text = preferences.getString(TAG_NAME, "new File");
        fileName.setText(text);
    }
    public void savePreferences(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(TAG_NAME, fileName.getText().toString());
        editor.apply();
        Toast.makeText(this, "Text saved", Toast.LENGTH_SHORT).show();
    }
    public void loadFile(){
        fileText.setText(getTextFromFile());
    }
    public void saveFile(){
        FileOutputStream outputStream = null;
        try {
            outputStream = openFileOutput(fileName.getText().toString(), Context.MODE_PRIVATE);
            outputStream.write(fileText.getText().toString().getBytes(StandardCharsets.UTF_8));
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String getTextFromFile(){
        FileInputStream inputStream = null;
        try {
            inputStream = openFileInput(fileName.getText().toString() + ".txt");
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            String text = new String(bytes);
            Log.d(TAG, text);
            return  text;
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveFile();
        savePreferences();
    }
}