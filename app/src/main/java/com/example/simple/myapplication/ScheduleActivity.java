package com.example.simple.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ScheduleActivity extends AppCompatActivity {

    ArrayList<String> schedule = MainActivity.course;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        try {
            Log.i("wangyuqiguai",MainActivity.course.size()+"");
            TextView textView;
            textView = findViewById(R.id.t0);
            Log.i("wangyu",schedule.get(0));
            textView.setText(schedule.get(0));
            textView = findViewById(R.id.t1);
            textView.setText(schedule.get(1));
            textView = findViewById(R.id.t2);
            textView.setText(schedule.get(2));
            textView = findViewById(R.id.t3);
            textView.setText(schedule.get(3));
            textView = findViewById(R.id.t4);
            textView.setText(schedule.get(4));
            textView = findViewById(R.id.t5);
            textView.setText(schedule.get(5));
            textView = findViewById(R.id.t6);
            textView.setText(schedule.get(6));
            textView = findViewById(R.id.t7);
            textView.setText(schedule.get(7));
            textView = findViewById(R.id.t8);
            textView.setText(schedule.get(8));
            textView = findViewById(R.id.t9);
            textView.setText(schedule.get(9));
            textView = findViewById(R.id.t10);
            textView.setText(schedule.get(10));
            textView = findViewById(R.id.t11);
            textView.setText(schedule.get(11));
            textView = findViewById(R.id.t12);
            textView.setText(schedule.get(12));
            textView = findViewById(R.id.t13);
            textView.setText(schedule.get(13));
            textView = findViewById(R.id.t14);
            textView.setText(schedule.get(14));
            textView = findViewById(R.id.t15);
            textView.setText(schedule.get(15));
            textView = findViewById(R.id.t15);
            textView.setText(schedule.get(15));
            textView = findViewById(R.id.t16);
            textView.setText(schedule.get(16));
            textView = findViewById(R.id.t17);
            textView.setText(schedule.get(17));
            textView = findViewById(R.id.t18);
            textView.setText(schedule.get(18));
            textView = findViewById(R.id.t19);
            textView.setText(schedule.get(19));
            textView = findViewById(R.id.t20);
            textView.setText(schedule.get(20));
            textView = findViewById(R.id.t21);
            textView.setText(schedule.get(21));
            textView = findViewById(R.id.t22);
            textView.setText(schedule.get(22));
            textView = findViewById(R.id.t23);
            textView.setText(schedule.get(23));
            textView = findViewById(R.id.t24);
            textView.setText(schedule.get(24));
            textView = findViewById(R.id.t25);
            textView.setText(schedule.get(25));
            textView = findViewById(R.id.t26);
            textView.setText(schedule.get(26));
            textView = findViewById(R.id.t27);
            textView.setText(schedule.get(27));
            textView = findViewById(R.id.t28);
            textView.setText(schedule.get(28));
            textView = findViewById(R.id.t29);
            textView.setText(schedule.get(29));
            textView = findViewById(R.id.t30);
            textView.setText(schedule.get(30));
            textView = findViewById(R.id.t31);
            textView.setText(schedule.get(31));
            textView = findViewById(R.id.t32);
            textView.setText(schedule.get(32));
            textView = findViewById(R.id.t33);
            textView.setText(schedule.get(33));
            textView = findViewById(R.id.t34);
            textView.setText(schedule.get(34));
        } catch (Exception e) {
            for (String s: schedule)
                Log.i("wangyu",s + "hello");
        }
    }
}
