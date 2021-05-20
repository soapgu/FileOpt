package com.soapgu.fileopt;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;

import com.blankj.utilcode.util.FileIOUtils;
import com.google.gson.Gson;

import java.io.File;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = this.findViewById(R.id.tv_message);
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            //find file from /sdcard/Android/data/com.soapgu.fileopt/files
            File configFile = new File(this.getExternalFilesDir(null), "test.json");
            Gson gson = new Gson();
            if( !configFile.exists() ){
                MyConfig config = new MyConfig();
                config.setUrl( "http://www.baidu.com" );
                config.setDeviceCode(UUID.randomUUID().toString());
                FileIOUtils.writeFileFromString( configFile, gson.toJson( config ) );
                textView.setText( "---Write OK---" );
            }
            else {
                 MyConfig config = gson.fromJson(FileIOUtils.readFile2String(configFile),MyConfig.class );
                textView.setText(String.format( "---Exist,url:%s,deviceCode:%s--", config.getUrl() , config.getDeviceCode()) );
            }

        }

    }
}