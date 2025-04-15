package com.example.ex17;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Main activity for the app.
 * Provides functionality to save input text to a file, reset the input, and exit the app while saving and reading the file.
 */
public class MainActivity extends AppCompatActivity implements View.OnCreateContextMenuListener {

    EditText editText;
    TextView textView;
    Button saveBtn;
    Button resetBtn;
    Button exitBtn;

    FileOutputStream fops;
    OutputStreamWriter opsw;
    Intent si;
    BufferedWriter bw;
    InputStreamReader reader;

    FileInputStream fis;
    StringBuilder sb;
    BufferedReader br;

    String input;
    private final String FILENAME = "exttest.txt";

    /**
     * Initializes the activity and its UI components.
     * @param savedInstanceState Saved state for activity restart.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
        textView = findViewById(R.id.textView);
        saveBtn = findViewById(R.id.saveBtn);
        resetBtn = findViewById(R.id.resetBtn);
        exitBtn = findViewById(R.id.exitBtn);
    }

    /**
     * Resets the input and text view, and clears the contents of the internal file.
     * @param view The view that triggered the method.
     */
    public void resetOnClick(View view) {
        try {
            FileOutputStream fops = openFileOutput(FILENAME, MODE_PRIVATE);
            fops.write("".getBytes());
            fops.close();

            editText.setText("");
            textView.setText("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves the current input to the internal file and updates the display.
     * @param view The view that triggered the method.
     */
    public void saveOnClick(View view) {
        input = editText.getText().toString();
        try {
            fops = openFileOutput(FILENAME, MODE_APPEND);
            opsw = new OutputStreamWriter(fops);
            bw = new BufferedWriter(opsw);

            bw.write(input + "\n");
            bw.close();
            fops.close();
            opsw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        textView.setText(textView.getText().toString() + editText.getText().toString() + "\n");
    }

    /**
     * Saves the current input, reads the full content from the file, updates the display,
     * and closes the activity.
     * @param view The view that triggered the method.
     */
    public void exitOnClick(View view) {
        String inputText = editText.getText().toString();
        try {
            FileOutputStream fops = openFileOutput(FILENAME, MODE_APPEND);
            OutputStreamWriter opsw = new OutputStreamWriter(fops);
            BufferedWriter bw = new BufferedWriter(opsw);

            bw.write(inputText);
            bw.newLine();
            bw.close();
            opsw.close();
            fops.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        StringBuilder sB = new StringBuilder();
        try {
            FileInputStream fis = openFileInput(FILENAME);
            InputStreamReader reader = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(reader);
            String line = br.readLine();
            while (line != null) {
                sB.append(line+'\n');
                line = br.readLine();
            }
            br.close();
            reader.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        textView.setText(sB.toString());
        finish();
    }

    /**
     * Handles selection from the options menu.
     * @param item The selected menu item.
     * @return true if the item was handled.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String id = item.getTitle().toString();
        if (id.equals("Credits")) {
            Intent intent = new Intent(this, creditsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Inflates the options menu from XML.
     * @param menu The options menu.
     * @return true if the menu is created successfully.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
