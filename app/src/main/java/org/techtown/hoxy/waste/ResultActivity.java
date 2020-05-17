package org.techtown.hoxy.waste;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.techtown.hoxy.MainActivity;

import org.techtown.hoxy.R;
import org.techtown.hoxy.RequestHttpURLConnection;
import org.techtown.hoxy.TrashName;
import org.techtown.hoxy.community.PostItem;
import org.techtown.hoxy.waste.WasteInfoActivity;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static android.util.Base64.DEFAULT;
import static android.util.Base64.NO_WRAP;
import static android.util.Base64.encodeToString;

import java.util.ArrayList;
import java.util.Objects;

public class ResultActivity extends AppCompatActivity {
    private final static int TAKE_PICTURE = 1;
    private static final int REQUEST_CODE = 0;

    private String intent_text;
    private ImageView waste_ImageView;
    private TextView waste_textView;
    private Button again_button, next_button;

    private Bitmap waste_bitmap;        //사진이 저장되는 변수
    private String trashName;
    private String send_data;
    private String files;
    private String deep_learning_answer;

    ////추가
    private String wasteInfoItems;
    private int position = 0;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        //인텐트 받아오기
        Intent intent_get = getIntent();
        intent_text = Objects.requireNonNull(intent_get.getExtras()).getString("intent_text");
        wasteInfoItems = (String) intent_get.getSerializableExtra("wasteInfoItems");
        position = intent_get.getExtras().getInt("position");
        System.out.println(intent_text);
        waste_textView = findViewById(R.id.textView);
        waste_textView.setText("이미지 검색중..");

        again_button = findViewById(R.id.button);
        next_button = findViewById(R.id.button2);
        next_button.setVisibility(View.INVISIBLE);
        //갤러리로 이동
        if(intent_text.equals("image")) {

            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, REQUEST_CODE);
        }
    //카메라로 이동
        else if(intent_text.equals("camera")) {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, TAKE_PICTURE);
        }


        waste_ImageView = (ImageView) findViewById(R.id.imageView);


        //// 다시
        again_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

            finish();
            Intent intent = new Intent(ResultActivity.this, ResultActivity.class);
            intent.putExtra("intent_text",intent_text);
            intent.putExtra("position", position);
            intent.putExtra("wasteInfoItems", deep_learning_answer);
            startActivity(intent);
        }
    });

    //// 다음
        next_button.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            //if(wasteInfoItems == null)
           //    wasteInfoItems = new ArrayList<WasteInfoItem>();
            //wasteInfoItems.add(wasteInfoItem);
            finish();
            Intent intent = new Intent(ResultActivity.this, WasteInfoActivity.class);
            intent.putExtra("intent_text",intent_text);
            intent.putExtra("wasteInfoItems", deep_learning_answer);
            intent.putExtra("position", position);
            startActivity(intent);
        }
    });

    }
    public static String encodeTobase64(Bitmap image)
    {
        Bitmap immagex=image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = encodeToString(b, NO_WRAP);
        return imageEncoded;
    }

    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(intent_text.equals("image")) {
            // Check which request we're responding to
            if (requestCode == REQUEST_CODE) {
                // Make sure the request was successful
                if (resultCode == RESULT_OK) {
                    try {
                        // 선택한 이미지에서 비트맵 생성
                        InputStream in = getContentResolver().openInputStream(data.getData());
                        waste_bitmap = BitmapFactory.decodeStream(in);
                        image_send(waste_bitmap);

                        in.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }// try, catch
                }// if result_ok
            }// if requestcode
        }// if image

        else if(intent_text.equals("camera"))
        {
            if(requestCode == TAKE_PICTURE)
                if (resultCode == RESULT_OK && data.hasExtra("data")) {
                    waste_bitmap = (Bitmap) data.getExtras().get("data");
                    if (waste_bitmap != null) {
                        image_send(waste_bitmap);
                    }

                }// if result code

        }
    }
    public void image_send(Bitmap waste_bitmap){
        files = encodeTobase64(waste_bitmap);

        int area_no = 1;
        JSONObject jo_data = new JSONObject();
        try {
            jo_data.put("area_no",area_no);
            jo_data.put("files",files);
            send_data = jo_data.toString();

            send_data = "{\"area_no\":1, \"files\": \""+files+"\"}";
        } catch (JSONException e) {
            e.printStackTrace();
        }
        http_task http_task = new http_task("select_waste_type");
        http_task.execute();

        // 이미지 표시
        Glide.with(this).load(waste_bitmap).into(waste_ImageView);
    }

    public class http_task extends AsyncTask<String, String, String> {
        String sub_url = "";
        http_task(String sub_url){
            this.sub_url = sub_url;
        }
        @Override
        protected String doInBackground(String... params) {
            String res = "";
            try {
                String str = "";
                String str_URL = "http://" + RequestHttpURLConnection.server_ip + ":" + RequestHttpURLConnection.server_port + "/" + sub_url + "/";
                System.out.println("str_URL : " + str_URL);
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
                String data = "data=" + send_data;

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
                res = res.replace("&#39;","\"");
                System.out.println("res : " + res);
                deep_learning_answer = res;
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return res;
        }
        @Override
        protected void onPostExecute(String result) {
            JSONArray jsonArray = null;
            try {
                jsonArray = new JSONArray(deep_learning_answer);
                String waste_name = "";
                waste_name = jsonArray.getJSONObject(0).getString("waste_type_kor_name");
                System.out.println("waste_name : " + waste_name);
                waste_textView.setText(waste_name);
                next_button.setVisibility(View.VISIBLE);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            }
    }
}
