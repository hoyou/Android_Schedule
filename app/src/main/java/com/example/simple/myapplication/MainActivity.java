package com.example.simple.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.security.auth.login.LoginException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    static Map<String,String> teacherMap = new HashMap<>();
    static Map<String,String> termMap = new HashMap<>();
    private String cookie;
    private EditText editText_code;
    private EditText editText_teacher;
    private Button button;
    private Spinner sp;
    private ImageView imageView;
    private ImageButton refesh;
    private String term_text;
    private Handler handler;
    private byte[] imageBuf;
    public static ArrayList<String> course = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getValidate();
        try {
            parseTerm();
            parseTeacherList();
        } catch (Exception e) {
            e.printStackTrace();
        }


        editText_code = findViewById(R.id.code);
        editText_teacher = findViewById(R.id.teacher);
        sp = findViewById(R.id.term);
        imageView = findViewById(R.id.v_image);
        refesh=findViewById(R.id.refresh);
        button = findViewById(R.id.query);
        button.setOnClickListener(this);
        refesh.setOnClickListener(this);



        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.arg1){
//                    case 1:
//                        Intent intent = new Intent(MainActivity.this, ScheduleActivity.class);
//                        startActivity(intent);
//                        break;
                    case 10:
                        imageView.setImageBitmap(BitmapFactory.decodeByteArray(imageBuf,0,imageBuf.length));
                        break;

                }
            }
        };

        term_text = (String) sp.getSelectedItem();
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                term_text = (String) sp.getSelectedItem();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.refresh:
                getValidate();
            case R.id.query:
                getSchedule();
                Intent intent = new Intent(MainActivity.this, ScheduleActivity.class);
                startActivity(intent);

        }
    }




    public void getValidate() {
        new Thread(){
            @Override
            public void run() {
                try {
                    String address="http://121.248.70.120/jwweb/sys/ValidateCode.aspx?t=812";
                    URL url=new URL(address);
                    URLConnection con=url.openConnection();
                    //con.setRequestProperty("Cookie", "ASP.NET_SessionId=tsczmgeydfuqrz3bces5fh45");
                    con.setRequestProperty("Referer", "http://121.248.70.120/jwweb/ZNPK/KBFB_ClassSel.aspx");
                    con.setDoInput(true);
                    con.connect();
                    cookie=con.getHeaderField("Set-Cookie");
                    System.out.println(cookie);
                    byte[] buf=new byte[512];
                    InputStream input=con.getInputStream();
                    ByteArrayOutputStream out=new ByteArrayOutputStream();
                    int len=0;
                    while((len=input.read(buf))!=-1) {
                        out.write(buf,0,len);
                    }
                    imageBuf=out.toByteArray();
                    input.close();
                    out.close();
                    Message msg = new Message();
                    msg.arg1 = 10;
                    handler.sendMessage(msg);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void getSchedule() {
        new Thread(){
            @Override
            public void run() {
                try {
                    String yzm = editText_code.getText().toString();
                    String referer="http://121.248.70.120/jwweb/ZNPK/TeacherKBFB.aspx";
                    String address="http://121.248.70.120/jwweb/ZNPK/TeacherKBFB_rpt.aspx";

                    URL url=new URL(address);
                    HttpURLConnection con=(HttpURLConnection)url.openConnection();
                    con.setDoOutput(true);
                    con.setDoInput(true);
                    con.setRequestProperty("Cookie", cookie);
                    con.setRequestProperty("Referer", referer);
                    con.setRequestMethod("POST");
                    StringBuilder builder=new StringBuilder();
//                    builder.append("Sel_XNXQ=20180&Sel_JS=0000315&type=1&txt_yzm="+yzm);
                    String teacherNumber = teacherMap.get(editText_teacher.getText().toString());
                    String termNumber = termMap.get(term_text);
                    builder.append("Sel_XNXQ="+termNumber+"&Sel_JS="+teacherNumber+"&type=1&txt_yzm="+yzm);
                    OutputStream netOut=con.getOutputStream();
                    netOut.write(builder.toString().getBytes());
                    int len=con.getContentLength();
                    byte[] buf=new byte[512];
                    InputStream input=con.getInputStream();
                    StringBuilder html = new StringBuilder();
                    while((len=input.read(buf))!=-1) {
                        html.append(new String(buf,"GB2312"));
                    }
                    input.close();
                    netOut.close();
                    Document doc = Jsoup.parse(html.toString());
                    Elements options = doc.getElementsByTag("table");
                    Elements tds = options.get(3).select("td");
                    System.out.println(tds.size());
                    int count = 0;
                    for (Element td: tds){
                        count++;
                        if (10 < count && count < 51 && count != 18 && count != 26 && count != 27 && count != 35 && count != 43) {
                            course.add(td.text());
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    public void save(String fileName,String inputText) {
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            out = openFileOutput(fileName, Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(inputText);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String load(String fileName) {
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try {
            in = openFileInput(fileName);
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }
    public static void parseTerm() throws Exception{
        String html = Constant.termList;
        Document doc = Jsoup.parse(html);
        Elements options = doc.select("option");
        for (Element option : options) {
            String value = option.attr("value");
            String text = option.text();
            termMap.put(text,value);
            Log.i("wangyu",value+text);
        }
    }
    public static void parseTeacherList()throws Exception{
        String html = Constant.teacherList;
        Document doc = Jsoup.parse(html);
        Elements options = doc.select("option");
        for (Element option : options) {
            String value = option.attr("value");
            String text = option.text();
            teacherMap.put(text,value);
            Log.i("wangyu",text);
        }
    }
}
