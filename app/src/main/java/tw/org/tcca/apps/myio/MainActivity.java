package tw.org.tcca.apps.myio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private TextView tv;
    private String name;
    private int counter;
    private boolean sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.tv);

        sp = getSharedPreferences("config", MODE_PRIVATE);
        editor = sp.edit();

        name = sp.getString("name", "nobody");
        counter = sp.getInt("counter", 1);
        sound = sp.getBoolean("sound", true);

        tv.setText(name +":" + counter + ":" + sound);
        counter++;
        editor.putInt("counter", counter);
        editor.commit();

    }

    public void test1(View view) {
        name = "Brad";
        editor.putString("name", name);
        editor.putBoolean("sound", false);
        editor.commit();

    }
    public void test2(View view) {
        try {
            FileOutputStream fout =  openFileOutput("brad.txt", MODE_PRIVATE);
            fout.write("Hello, World".getBytes());
            fout.flush();
            fout.close();
            Toast.makeText(this, "Save OK", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}