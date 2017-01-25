package com.example.tacademy.dbdownload;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 네트워크 체크를 했다 (정상)
        // 버전체크를 했다 (정상)
        // 디비버전 체크를 했다 (최신 버전 디비가 존재할 경우, 앱을 설치하고 최초)
        handler.sendEmptyMessage(1);
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 1){
                dbFileDownLoad("http://52.78.30.74:3000/db/log_201701251625.db");
            }
        }
    };

    public void dbFileDownLoad(final String dbUrl){
        new Thread(new Runnable(){
            @Override
            public void run(){
                // 1. 다운 받을 파일 경로 및 파일명
                String APP_CACHE_DIR = "/data/data/" + "com.example.tacademy.dbdownload" + "/db";
                // http://52.78.30.74:3000/db/log_201701251625.db
                // => log_201701251625.db추출
                String DB_FILENAME =
                        dbUrl.substring(dbUrl.lastIndexOf("/")+1, dbUrl.length());
                // 디렉토리 체크
                File file = new File(APP_CACHE_DIR);
                if(!file.isDirectory()){
                    file.mkdir();   // 폴더가 없을 경우 폴더 생성
                }

                try {
                    // 2. 서버 접속
                    URL url = new URL(dbUrl);
                    // 3. 입출력 정의
                    // 원격 리소스로부터 데이터를 읽어오는 채널
                    InputStream is
                            = new BufferedInputStream(url.openStream(), 1024*1024);
                    // 데이터를 파일까지 기록하는 채널
                    OutputStream os = new FileOutputStream(APP_CACHE_DIR + "/" + DB_FILENAME);
                    // 4. 수신 -> 다읽을때까지 -> EOF -> 스트림의 끝 => -1
                    byte[] buffer = new byte[1024];
                    int ch = 0;
                    while((ch = is.read(buffer)) != -1){
                        // 4-1. 수신 데이터를 파일에 저장
                        os.write(buffer, 0, ch);    // 실제로 읽은 양만 파일에 기록한다.
                    }
                    // 5. 파일 닫기
                    os.flush();
                    os.close();
                    // 6. 스트림 닫기
                    is.close();
                    Log.i("U", "파일저장완료:"+APP_CACHE_DIR+"/"+DB_FILENAME);
                    StorageHelper.getInstance().setString(MainActivity.this, "DBNAME", DB_FILENAME);

                    // 데이터 조회
                    ArrayList<LastCallModel> array = U.getInstance().getLocalDB(MainActivity.this).selectLog();
                    for(LastCallModel m : array){
                        U.getInstance().log("전화번호 : " + m.getTel());
                    }
                } catch (Exception e) {
                    Log.i("U", "오류 발생"+e.getMessage());
                }
            }
        }).start();
    }
}
