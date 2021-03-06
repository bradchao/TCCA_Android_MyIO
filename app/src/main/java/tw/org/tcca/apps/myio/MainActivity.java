package tw.org.tcca.apps.myio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private TextView tv;
    private String name;
    private int counter;
    private boolean sound;
    private MyDBHelper myDBHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDBHelper = new MyDBHelper(this, "tcca", null, 1);
        db = myDBHelper.getReadableDatabase();

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
    public void test3(View view){
        try {
            FileInputStream fin =  openFileInput("brad.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fin));
            String line = reader.readLine();
            fin.close();
            tv.setText(line);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void test4(View view){
        // SELECT * FROM cust
        Cursor c = db.query("cust", null, null, null,null, null, null);
        while (c.moveToNext()){
            String id = c.getString(c.getColumnIndex("id"));
            String cname = c.getString(c.getColumnIndex("cname"));
            String tel = c.getString(c.getColumnIndex("tel"));
            Log.v("bradlog", id +":" + cname + ":" + tel);
        }
    }

    public void test5(View view){
        ContentValues values = new ContentValues();
        values.put("cname", "brad");
        values.put("tel", "1234567");
        db.insert("cust", null, values);
        test4(null);
    }


    public void test6(View view) {
        // delete from cust where id = 2;
        db.delete("cust", "id = ?", new String[]{"2"});
        test4(null);
    }
    public void test7(View view){
        ContentValues values = new ContentValues();
        values.put("cname", "peter");
        values.put("tel", "7654321");
        db.update("cust",values, "id = ?", new String[]{"3"});
    }

}