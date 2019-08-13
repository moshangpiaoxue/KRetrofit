package com.mo.kretrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import mo.lib.http.KHttpObserver;
import mo.lib.kr;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        kr.Ext.setBaseUrl("http://192.168.5.228:20101/");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Map<String, String> map = new HashMap<>();
        map.put("username", "fanfan");
        map.put("password", "1");
        kr.http().post("inspec/sys/login", map, new KHttpObserver<String>() {

            @Override
            protected void kOnNext(String body) {
                Log.i(kr.TAG(), body);
            }

        });


    }
}


