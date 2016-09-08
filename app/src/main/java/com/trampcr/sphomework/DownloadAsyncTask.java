package com.trampcr.sphomework;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by zxm on 2016/9/5.
 */
public class DownloadAsyncTask extends AsyncTask<URL, Integer, String> {

    private ProgressDialog progressDialog;
    private int hasRead = 0;
    private Context context;

    public DownloadAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(URL... params) {
        try {
            URLConnection urlConnection = params[0].openConnection();
            InputStream inputStream= urlConnection.getInputStream();
            String downloadFolderName = Environment.getExternalStorageDirectory() + File.separator + "trampcr" + File.separator;
            File file = new File(downloadFolderName);
            if (!file.exists()){
                file.mkdir();
            }
            String fileName = downloadFolderName + "zxm.apk";
            File apkFile = new File(fileName);
            if (apkFile.exists()) {
                apkFile.delete();
            }
            byte[] buff = new byte[1024];
            int length = 0;
            OutputStream outputStream = new FileOutputStream(fileName);
            while ((length = inputStream.read(buff)) != -1) {
                outputStream.write(buff, 0, length);
                hasRead++;
                publishProgress(hasRead);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progressDialog.dismiss();
        Toast.makeText(context, "下载完成", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        //设置对话框标题
        progressDialog.setTitle("任务正在进行中");
        //设置对话框显示的内容
        progressDialog.setMessage("正在下载，请稍等...");
        //设置对话框的取消按钮
        progressDialog.setCancelable(true);
        //设置进度条的最大值
        progressDialog.setMax(2000);
        //设置进度条风格
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        //设置对话框的进度条是否显示进度
        progressDialog.setIndeterminate(false);
        progressDialog.show();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        //更新进度
        progressDialog.setProgress(values[0]);
    }
}
