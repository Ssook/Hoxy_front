package org.techtown.hoxy;

import android.content.ContentValues;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class RequestHttpURLConnection extends Thread{
    private String server_ip = "172.16.46.22";
    private String server_port = "8000";

    private String str_URL;
    private String data;
    private String res;

    //생성자
    public RequestHttpURLConnection(String s, String d) {
        this.str_URL = s;
        this.data = d;
    }

    //get/set
    public String getStr_URL() {
        return str_URL;
    }

    public void setStr_URL(String str_URL) {
        this.str_URL = str_URL;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getRes() {
        res = res.replaceAll("&#39;", "\'");
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }



    @Override
    public void run() {
        try {
            String str;
            str_URL = "http://" + server_ip + ":" + server_port + "/" + str_URL;
            URL url = new URL(str_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //--------------------------
            //   전송 모드 설정 - 기본적인 설정이다
            //--------------------------
            conn.setDefaultUseCaches(false);
            conn.setDoInput(true);                         // 서버에서 읽기 모드 지정
            conn.setDoOutput(true);                       // 서버로 쓰기 모드 지정
            conn.setRequestMethod("POST");         // 전송 방식은 POST

            // 서버에게 웹에서 <Form>으로 값이 넘어온 것과 같은 방식으로 처리하라는 걸 알려준다
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");            //--------------------------
            //   서버로 값 전송
            //--------------------------
            StringBuffer buffer = new StringBuffer();
            String data = "data=" + "\"sample\"";

            buffer.append(data);

            OutputStreamWriter outStream = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            PrintWriter writer = new PrintWriter(outStream);
            writer.write(buffer.toString());
            writer.flush();

            //--------------------------
            //   서버에서 전송받기
            //--------------------------
            InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
            BufferedReader reader = new BufferedReader(tmp);
            StringBuilder builder = new StringBuilder();
            while ((str = reader.readLine()) != null) {       // 서버에서 라인단위로 보내줄 것이므로 라인단위로 읽는다
                builder.append(str + "\n");                     // View에 표시하기 위해 라인 구분자 추가
            }
            res = builder.toString();
            } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
/*
    public String requestConnection(String str_URL, String data) throws JSONException {

        String server_ip = "172.16.46.22";
        String server_port = "8000";
        String result = "";

        System.out.println("hjy : connection");
        try {
            //--------------------------
            //   URL 설정하고 접속하기
            //--------------------------
            String url_address = "http://" + server_ip + ":" + server_port + "/" + str_URL;
            System.out.println("url_address : " + url_address);
            URL url = new URL(url_address);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();   // 접속
            //--------------------------
            //   전송 모드 설정 - 기본적인 설정이다
            //--------------------------
            http.setDefaultUseCaches(false);
            http.setDoInput(true);                         // 서버에서 읽기 모드 지정
            http.setDoOutput(true);                       // 서버로 쓰기 모드 지정
            http.setRequestMethod("POST");         // 전송 방식은 POST


            // 서버에게 웹에서 <Form>으로 값이 넘어온 것과 같은 방식으로 처리하라는 걸 알려준다
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");            //--------------------------
            System.out.println("왜 안돼 시발2");

            //--------------------------
            //   서버에서 전송받기
            //--------------------------
            InputStreamReader tmp = new InputStreamReader(http.getInputStream(), "UTF-8");
            System.out.println("왜 안돼 시발");
            BufferedReader reader = new BufferedReader(tmp);
            StringBuilder builder = new StringBuilder();
            String str;

            while ((str = reader.readLine()) != null) {       // 서버에서 라인단위로 보내줄 것이므로 라인단위로 읽는다
                builder.append(str + "\n");                     // View에 표시하기 위해 라인 구분자 추가
            }
            System.out.println("100");
            result = builder.toString();
        } catch (MalformedURLException e) {
            System.out.println(e.getCause() + e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getCause() + e.getMessage());
        }
        System.out.println("reusult_value : " + result);
        return result; //onPostExcute()로 전달
    } // HttpPostDat
*/
}