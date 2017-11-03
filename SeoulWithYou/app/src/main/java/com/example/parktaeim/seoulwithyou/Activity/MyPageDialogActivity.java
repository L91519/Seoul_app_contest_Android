package com.example.parktaeim.seoulwithyou.Activity;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.parktaeim.seoulwithyou.Adapter.MyPagePostRecyclerViewAdapter;
import com.example.parktaeim.seoulwithyou.Model.MyPagePostItem;
import com.example.parktaeim.seoulwithyou.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.bumptech.glide.request.target.DrawableImageViewTarget;


/**
 * Created by parktaeim on 2017. 10. 26..
 */

public class MyPageDialogActivity extends Activity  {
    private TextView goChatBtn;

    private Context context;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private CircleImageView changeProfileIcon;
    private CircleImageView mypage_profileImg;
    private String myPage_id;
    String name;
    String birth;
    String image;
    String sex;
    String age;

    private int CAMERA_CODE =0;
    private int GALLERY_CODE =1;
    private int CROP_IMAGE_CODE = 2;
    private String absolutePath;
    private Uri mImageCaptureUri;

    ArrayList<MyPagePostItem> arrayList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_mypage);

        Intent intent = getIntent();
        myPage_id = intent.getStringExtra("mypage_id");
        Log.d("mypage id get =======", myPage_id);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView mypage_backImg = (ImageView) findViewById(R.id.mypage_backImg);
        Glide.with(this).load(R.drawable.img_login_background).into(mypage_backImg);
        LinearLayout clipLayout = (LinearLayout) findViewById(R.id.clipLayout);
        clipLayout.setClipToOutline(true);

        ImageView gifView = (ImageView) findViewById(R.id.gif_view) ;
        DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(gifView);
        Glide.with(this).load(R.raw.pizza).apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.RESOURCE)).into(imageViewTarget);
//        Glide.with(getApplicationContext()).load(R.drawable.pizza).into(gifView);

        CircleImageView profileImg = (CircleImageView) findViewById(R.id.mypage_profileImg);
        Glide.with(getApplicationContext()).load(R.drawable.icon_profile).into(profileImg);

        setProfile();
        setOnClick();
        setRecyclerView();
        setChangeProfile();
    }

    private void setChangeProfile() {
        changeProfileIcon = (CircleImageView) findViewById(R.id.mypage_changeProfileIcon);

        // 사진 변경 아이콘 눌렀을때
        changeProfileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence photoOptions[] = new CharSequence[]{"앨범","카메라"};   // 다이얼로그 내용들

                //사진, 카메라 선택 다이얼로그
                AlertDialog.Builder builder = new AlertDialog.Builder(MyPageDialogActivity.this,R.style.DialogTheme);
                builder.setTitle("사진 선택");  //다이얼로그 제목
                builder.setItems(photoOptions, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0: //앨범
                                Intent galleryIntent = new Intent(Intent.ACTION_PICK);
                                galleryIntent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                galleryIntent.setType("image/*");
                                startActivityForResult(Intent.createChooser(galleryIntent, "Select Picture"),GALLERY_CODE);
                                break;

                            case 1: //카메라
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(intent, CAMERA_CODE);
                                break;
                        }
                    }
                });

                builder.show();
            }
        });


    }


    private void setProfile() {
        SharedPreferences tokenPref = getSharedPreferences("tokenPref", MODE_PRIVATE);
        com.example.parktaeim.seoulwithyou.Network.Service.getRetrofit(getApplicationContext()).mypage_info(tokenPref.getString("token", "null"), myPage_id).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d("mypage info retrofit===", String.valueOf(response.code()));

                if (response.code() == 200) {
                    JsonObject jsonObject = response.body();
                    Log.d("info object ==",response.body().toString());
                    name = jsonObject.getAsJsonPrimitive("name").getAsString();
                    birth = jsonObject.getAsJsonPrimitive("birth").getAsString();
                    age = jsonObject.getAsJsonPrimitive("age").getAsString();
//                    image = jsonObject.getAsJsonPrimitive("image").getAsString();
                    sex = jsonObject.getAsJsonPrimitive("sex").getAsString();


                    // mypage info setting
                    TextView mypage_name = (TextView) findViewById(R.id.mypage_nameTextView);
                    CircleImageView mypage_profile = (CircleImageView) findViewById(R.id.mypage_profileImg);
                    TextView mypage_age = (TextView) findViewById(R.id.mypage_ageTextView);
                    TextView mypage_sex = (TextView) findViewById(R.id.mypage_genderTextView);

                    mypage_name.setText(name);
                    mypage_age.setText(age+"세");
                    if(sex.equals("true")){
                        mypage_sex.setText("남성");
                    }else if(sex.equals("false")){
                        mypage_sex.setText("여성");

                    }
//                    Glide.with(context).load(image).into(mypage_profile);

                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });




    }

    private void setRecyclerView() {
        context = getApplicationContext();

        recyclerView = (RecyclerView) findViewById(R.id.myPagePostRecyclerView);
        recyclerView.setHasFixedSize(true);

        ArrayList items = new ArrayList<>();
        SharedPreferences sharedPreferences = getSharedPreferences("myId", MODE_PRIVATE);
        Log.d("mypage pref === ", sharedPreferences.getString("myId", "null"));

        // 다이얼로그에 작성 글 세팅
        SharedPreferences tokenPref = getSharedPreferences("tokenPref", MODE_PRIVATE);

        Log.d("get post ==========","start!!!!!!");
        com.example.parktaeim.seoulwithyou.Network.Service.getRetrofit(getApplicationContext()).
                mypage_post(tokenPref.getString("token", "null"), myPage_id).
                enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        Log.d("post id",myPage_id);
                        Log.d("mypage response code ==", String.valueOf(response.code()));
                        if (response.code() == 200) {
                            JsonArray infoArray = response.body().getAsJsonArray("data");
                            Log.d("mypage infoArray ==", infoArray.toString());
                            JsonArray jsonElements = infoArray.getAsJsonArray();
//                            arrayList = new getPostList(jsonElements);



                        }else if(response.code() == 400){
                            RelativeLayout noPostLayout = (RelativeLayout) findViewById(R.id.noPostLayout);
                            noPostLayout.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Log.d("get post=====","failure"+t.toString());

                    }
                });

        items.add(new MyPagePostItem("http://img.hb.aicdn.com/03d474bbe20efb7df9aed4541ace70b53b53c70bdfe3-8djYVv_fw658", "경복궁과 함께라면", "2017-09-22", "나랑 같이 놀러갈래?", "아이러브유"));

        layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

        RequestManager requestManager = Glide.with(getApplicationContext());
        adapter = new MyPagePostRecyclerViewAdapter(getApplicationContext(), items, requestManager);
        recyclerView.setAdapter(adapter);

    }

    public ArrayList<MyPagePostItem> getArrayList(JsonArray jsonElements) {
        ArrayList<MyPagePostItem> arrayList = new ArrayList<>();
        for (int i = 0; i < jsonElements.size(); i++) {
            JsonObject jsonObject = (JsonObject) jsonElements.get(i);
            String title = jsonObject.getAsJsonPrimitive("title").getAsString();
            String singer = jsonObject.getAsJsonPrimitive("createdAt").getAsString();
            String url = jsonObject.getAsJsonPrimitive("musicURL").getAsString();
            String imaurl = jsonObject.getAsJsonPrimitive("imgURL").getAsString();

        }
        return arrayList;
    }



    private void setOnClick() {
        ImageView cancelIcon = (ImageView) findViewById(R.id.cancel_Btn);
        cancelIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

//        goChatBtn = (TextView) findViewById(R.id.goChatButton);
//        goChatBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MyPageDialogActivity.this,ChattingActivity.class);
//                startActivity(intent);
//            }
//        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if(requestCode==GALLERY_CODE){
                SendPicture(data);
            }else if(requestCode == CAMERA_CODE){
                SendPicture(data);
            }
        }
    }

    private void SendPicture(Intent data){
        Uri imgUri = data.getData();
        Log.d("dialog URI!!!!",String.valueOf(imgUri));
        String imagePath = getRealPathFromURI(imgUri);

        Intent intent = new Intent(MyPageDialogActivity.this,ShowChangeImgActivity.class);
        intent.putExtra("real uri",imagePath);
        startActivity(intent);

    }

    public Bitmap rotate(Bitmap src, float degree) {

        // Matrix 객체 생성
        Matrix matrix = new Matrix();
        // 회전 각도 셋팅
        matrix.postRotate(degree);
        // 이미지와 Matrix 를 셋팅해서 Bitmap 객체 생성
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(),
                src.getHeight(), matrix, true);
    }



    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);

    }

    public int exifOrientationToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    // crop한 사진 저장
    private void storeCropImage(Bitmap bitmap,String filePath){
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/SeoulWithYou";
        File dir_seoul = new File(dirPath);

        if(!dir_seoul.exists()){
            dir_seoul.mkdir();
        }

        File copyFile = new File(filePath);
        BufferedOutputStream out = null;

        try{
            copyFile.createNewFile();
            out = new BufferedOutputStream(new FileOutputStream(copyFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,out);

            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE));

            out.flush();
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }



}
