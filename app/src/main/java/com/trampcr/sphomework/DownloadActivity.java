package com.trampcr.sphomework;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by zxm on 2016/9/5.
 */
public class DownloadActivity extends AppCompatActivity implements View.OnClickListener{

    //    public static final String DOWNLOAD_URL = "http://psoft.33lc.com:801/small/rootexplorer_33lc.apk";
    private Button mBtnStartDownload;
    private EditText mEtUrl;
    private String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        mEtUrl = (EditText) findViewById(R.id.et_url);
        mBtnStartDownload = (Button) findViewById(R.id.btn_start_download);

        mBtnStartDownload.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mUrl = mEtUrl.getText().toString().trim();
        DownloadAsyncTask downloadAsyncTask = new DownloadAsyncTask(DownloadActivity.this);
        try {
//            downloadAsyncTask.execute(new URL(DOWNLOAD_URL));
            downloadAsyncTask.execute(new URL(mUrl));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
