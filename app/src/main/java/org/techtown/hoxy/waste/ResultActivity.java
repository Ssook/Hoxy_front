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
    private ArrayList<WasteInfoItem> wasteInfoItems;
    private WasteInfoItem wasteInfoItem;
    private int position = 0;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        //인텐트 받아오기
        Intent intent_get = getIntent();
        intent_text = Objects.requireNonNull(intent_get.getExtras()).getString("intent_text");
        wasteInfoItems = (ArrayList<WasteInfoItem>) intent_get.getSerializableExtra("wasteInfoItems");
        position = intent_get.getExtras().getInt("position");
        System.out.println(intent_text);


     //갤러리로 이동
        if(intent_text.equals("image")) {

            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, REQUEST_CODE);
        }
    //카메라로 이동
        else if(intent_text.equals("camera")) {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, TAKE_PICTURE);
        }
        waste_textView = findViewById(R.id.textView);


        waste_ImageView = (ImageView) findViewById(R.id.imageView);

        again_button = findViewById(R.id.button);
        next_button = findViewById(R.id.button2);

        //// 다시
        again_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

            finish();
            Intent intent = new Intent(ResultActivity.this, ResultActivity.class);
            intent.putExtra("intent_text",intent_text);
            intent.putExtra("position", position);
            intent.putExtra("wasteInfoItems", wasteInfoItems);
            startActivity(intent);
        }
    });

    //// 다음
        next_button.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            if(wasteInfoItems == null)
               wasteInfoItems = new ArrayList<WasteInfoItem>();
            wasteInfoItems.add(wasteInfoItem);
            finish();
            Intent intent = new Intent(ResultActivity.this, WasteInfoActivity.class);
            intent.putExtra("intent_text",intent_text);
            intent.putExtra("wasteInfoItems", wasteInfoItems);
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
                System.out.println("1");
                // Make sure the request was successful
                if (resultCode == RESULT_OK) {
                    try {
                        System.out.println("2");
                        // 선택한 이미지에서 비트맵 생성
                        InputStream in = getContentResolver().openInputStream(data.getData());
                        waste_bitmap = BitmapFactory.decodeStream(in);
                        files = encodeTobase64(waste_bitmap);
                        System.out.println("시발 : " + files);

                        int area_no = 1;
                        JSONObject jo_data = new JSONObject();
                        try {
                            jo_data.put("area_no",area_no);
                            jo_data.put("files",files);
                            send_data = jo_data.toString();
                            //files = "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";
                            send_data = "{\"area_no\":1, \"files\": \""+files+"\"}";
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        http_task http_task = new http_task("select_waste_type");
                        http_task.execute();

                        in.close();

                        // 이미지 표시
                        Glide.with(this).load(waste_bitmap).into(waste_ImageView);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }// try, catch
                }// if result_ok
            }// if requestcode
        }// if image

        else if(intent_text.equals("camera"))
        {
            switch (requestCode) {
                case TAKE_PICTURE:
                    if (resultCode == RESULT_OK && data.hasExtra("data")) {
                        waste_bitmap = (Bitmap) data.getExtras().get("data");
                        if (waste_bitmap != null) {
                            TrashName.setTrash("쇼파아님");
                            trashName = TrashName.getTrash();
                            waste_textView.setText(trashName);
                            Glide.with(this).load(waste_bitmap).into(waste_ImageView);
                        }

                    }// if result code

            }
        }
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
                TrashName.setTrash("쇼파일껄?");
                trashName = TrashName.getTrash();
                waste_textView.setText(waste_name);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            }
    }
}
