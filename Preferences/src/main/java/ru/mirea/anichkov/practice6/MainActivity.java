package ru.mirea.anichkov.practice6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btnSave, btnLoad;
    private TextView loadedText;
    private EditText editText;
    private SharedPreferences preferences;
    final String SAVED_TEXT = "saved_text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLoad = findViewById(R.id.btnLoad);
        btnSave = findViewById(R.id.btnSave);

        loadedText = findViewById(R.id.LoadedText);
        editText = findViewById(R.id.TextEdir);
        preferences = getPreferences(MODE_PRIVATE);

        View.OnClickListener ClickOnSave = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(SAVED_TEXT, editText.getText().toString());
                editor.apply();

                Toast.makeText(MainActivity.this, "Text saved", Toast.LENGTH_SHORT).show();
            }
        };
        View.OnClickListener ClickOnLoad = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = preferences.getString(SAVED_TEXT, "Empty");
                loadedText.setText(text);
            }
        };

        btnSave.setOnClickListener(ClickOnSave);
        btnLoad.setOnClickListener(ClickOnLoad);


    }
}