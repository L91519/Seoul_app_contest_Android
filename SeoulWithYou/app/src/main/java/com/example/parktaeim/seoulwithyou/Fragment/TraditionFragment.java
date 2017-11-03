package com.example.parktaeim.seoulwithyou.Fragment;
import android.Manifest;
import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.parktaeim.seoulwithyou.Activity.MainActivity;
import com.example.parktaeim.seoulwithyou.Activity.SearchCompanionActivity;
import com.example.parktaeim.seoulwithyou.Adapter.CourseDetailRecycerViewAdapter;
import com.example.parktaeim.seoulwithyou.Model.CourseDetailItem;
import com.example.parktaeim.seoulwithyou.Model.CourseItem;
import com.example.parktaeim.seoulwithyou.MyLocation;
import com.example.parktaeim.seoulwithyou.MyScrollView;
import com.example.parktaeim.seoulwithyou.Network.Service;
import com.example.parktaeim.seoulwithyou.R;
import com.example.parktaeim.seoulwithyou.ScrollViewListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.skp.Tmap.TMapData;
import com.skp.Tmap.TMapGpsManager;
import com.skp.Tmap.TMapPoint;
import com.skp.Tmap.TMapPolyLine;
import com.skp.Tmap.TMapView;
import com.stone.pile.libs.PileLayout;

import java.io.IOException;
import java.lang.reflect.Array;
import java.security.Permission;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.example.parktaeim.seoulwithyou.util.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by user on 2017-10-11.
 */

@RequiresApi(api = Build.VERSION_CODES.M)
public class TraditionFragment extends Fragment implements RecyclerView.OnScrollChangeListener, ScrollViewListener {

    static final LatLng SEOUL = new LatLng(37.56, 126.97);

    private RecyclerView detailRecyclerView;
    private RecyclerView.Adapter detailAdapter;
    private RecyclerView.LayoutManager detailManger;
    private ObjectAnimator transitionAnimator;
    private ImageButton companionBtn;
    private Animator.AnimatorListener animatorListener;
    private PileLayout pileLayout;
    //    private MapView mapView;
    private GoogleMap myMap;
    private int currentPosition;
    private ArrayList<CourseDetailItem> detailItems;
    private ArrayList<CourseItem> courseItems;

    private double currentLat;
    private double currentLon;

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;

    private static final String[] INITIAL_PERMS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_CONTACTS
    };
    private static final String[] LOCATION_PERMS = {
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    private static final int REQUEST_CODE_LOCATION = 2;


    private TMapView tMapView;
    private TMapGpsManager tMapGps;
    private LinearLayout mapLayout;
    final TMapData tmapData = new TMapData();

    private View view;

    LocationManager locationManager;
    String gpsProvider = LocationManager.GPS_PROVIDER;
    String networkProvider = LocationManager.NETWORK_PROVIDER;

    private int lastDisplay = -1;
    private float transitionValue;

//    @Override
//    public void onLocationChange(Location location) {
//        Log.d("start", "onlocation change!!");
//        tMapView.setLocationPoint(location.getLongitude(), location.getLatitude());
//        tMapView.setCenterPoint(location.getLongitude(), location.getLatitude());
//        Log.d(String.valueOf(location.getLongitude()), String.valueOf(location.getLatitude()));
//
//        currentLat = location.getLatitude();
//        currentLon = location.getLongitude();
//
//        if (location != null) ;
//        tMapView.setLocationPoint(location.getLongitude(), location.getLatitude());
//        tMapView.setCenterPoint(location.getLongitude(), location.getLatitude());
//        Log.d(String.valueOf(location.getLongitude()), String.valueOf(location.getLatitude()));
//
//        currentLat = location.getLatitude();
//        currentLon = location.getLongitude();
//
//        final TMapPoint startPoint = new TMapPoint(currentLat, currentLon);   // 현재 위치
//        final TMapPoint destPoint = new TMapPoint(36.316889, 127.158272);  // 도착 위치
//
//        if (currentLat != 0) {
//            tmapData.findPathDataWithType(TMapData.TMapPathType.CAR_PATH, startPoint, destPoint, new TMapData.FindPathDataListenerCallback() {
//                @Override
//                public void onFindPathData(TMapPolyLine tMapPolyLine) {
//                    Log.d("path start======" + String.valueOf(startPoint.getLatitude()), String.valueOf(startPoint.getLongitude()));
//                    Log.d("path dest======" + String.valueOf(destPoint.getLatitude()), String.valueOf(destPoint.getLongitude()));
//                    tMapView.setLocationPoint(startPoint.getLongitude(), startPoint.getLatitude());
//                    tMapView.addTMapPath(tMapPolyLine);
//                    Log.d("path poly", "finish=========");
//
//                }
//            });
//        }
//    }

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

            Log.d("start", "onlocation change!!");
            tMapView.setLocationPoint(location.getLongitude(), location.getLatitude());
            tMapView.setCenterPoint(location.getLongitude(), location.getLatitude());
            Log.d(String.valueOf(location.getLongitude()), String.valueOf(location.getLatitude()));

            currentLat = location.getLatitude();
            currentLon = location.getLongitude();

            if (location != null) ;
            tMapView.setLocationPoint(location.getLongitude(), location.getLatitude());
            tMapView.setCenterPoint(location.getLongitude(), location.getLatitude());
            Log.d(String.valueOf(location.getLongitude()), String.valueOf(location.getLatitude()));

            currentLat = location.getLatitude();
            currentLon = location.getLongitude();

            final TMapPoint startPoint = new TMapPoint(currentLat, currentLon);   // 현재 위치
            final TMapPoint destPoint = new TMapPoint(36.316889, 127.158272);  // 도착 위치

            if (currentLat != 0) {
                tmapData.findPathDataWithType(TMapData.TMapPathType.CAR_PATH, startPoint, destPoint, new TMapData.FindPathDataListenerCallback() {
                    @Override
                    public void onFindPathData(TMapPolyLine tMapPolyLine) {
                        Log.d("path start======" + String.valueOf(startPoint.getLatitude()), String.valueOf(startPoint.getLongitude()));
                        Log.d("path dest======" + String.valueOf(destPoint.getLatitude()), String.valueOf(destPoint.getLongitude()));
                        tMapView.setLocationPoint(startPoint.getLongitude(), startPoint.getLatitude());
                        tMapView.addTMapPath(tMapPolyLine);
                        Log.d("path poly", "finish=========");

                    }
                });
            }


        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void setGps() {
        final LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, // 등록할 위치제공자
                1000, // 통지사이의 최소 시간간격 (miliSecond)
                1, // 통지사이의 최소 변경거리 (m)
                mLocationListener);

        Log.d("setGps ==","ㅠㅠ");
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_modern, container, false);

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), LOCATION_PERMS, REQUEST_CODE_LOCATION);
        } else {
            setMap();
            setGps();

        }


        companionBtn = (ImageButton) view.findViewById(R.id.companionBtn);
        companionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchCompanionActivity.class);
                intent.putExtra("picture", courseItems.get(currentPosition).getPicUrl());
                intent.putExtra("title", courseItems.get(currentPosition).getPlaceName());
                intent.putExtra("distance", courseItems.get(currentPosition).getPlaceDistance());
                intent.putExtra("id", courseItems.get(currentPosition).getNo());
                startActivity(intent);
            }
        });

        detailRecyclerView = (RecyclerView) view.findViewById(R.id.detailRecyclerView);
        detailManger = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        detailRecyclerView.hasFixedSize();
        detailRecyclerView.setLayoutManager(detailManger);
        pileLayout = (PileLayout) view.findViewById(R.id.pileLayout);
        detailRecyclerView.setNestedScrollingEnabled(false);
        detailRecyclerView.setOnScrollChangeListener(this);


        ViewGroup.LayoutParams params = detailRecyclerView.getLayoutParams();
//        int height = (int) ((float) MainActivity.screenHeight * 1.1);
//        params.height = height;
//        detailRecyclerView.setLayoutParams(params);
//        detailRecyclerView.setNestedScrollingEnabled(false);

        animatorListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        };
        getCourse();
//        initDtalist();
        Log.d("ch", "kdj");

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (courseItems.size() != 0) {
                        pileLayout.setAdapter(new PileLayout.Adapter() {
                            @Override
                            public int getLayoutId() {
                                return R.layout.item_layout;
                            }

                            @Override
                            public int getItemCount() {
                                return courseItems.size();
                            }

                            @Override
                            public void bindView(View view, int position) {
                                ViewHolder viewHolder = (ViewHolder) view.getTag();
                                if (viewHolder == null) {
                                    viewHolder = new ViewHolder();
                                    viewHolder.imageView = (ImageView) view.findViewById(R.id.imageView);
                                    viewHolder.courseName = (TextView) view.findViewById(R.id.courseNameText);
//                                    viewHolder.distance = (TextView) view.findViewById(R.id.courseDistanceText);
                                    view.setTag(viewHolder);
                                }
                                Glide.with(getContext()).load(courseItems.get(position).getPicUrl()).into(viewHolder.imageView);
                                viewHolder.courseName.setText(courseItems.get(position).getPlaceName());
//                                viewHolder.distance.setText(courseItems.get(position).getPlaceDistance());
                            }

                            @Override
                            public void displaying(int position) {
                                Log.d("---positionLog", String.valueOf(position));
                                if (lastDisplay < 0) {
                                    initSecene(position);
                                    lastDisplay = 0;
                                } else if (lastDisplay != position) {
                                    transitionSecene(position);
                                    lastDisplay = position;
                                }
                            }

                            @Override
                            public void onItemClick(View view, int position) {
                                super.onItemClick(view, position);
                            }
                        });
                        break;
                    } else {
                        continue;
                    }
                }
            }
        }, 5000);

        return view;
    }

    public void setPileLayoutAdapter() {

    }

    public void getCourse() {

        courseItems = new ArrayList<>();

        Service.getRetrofit(getContext()).
                getHistoryCourseList().
                enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        Log.d("--checkOnResponse", "check");
                        if (response.code() == 200) {
                            JsonArray jsonArray = response.body().getAsJsonArray("data");
                            JsonArray jsonElements = jsonArray.getAsJsonArray();

                            for (int i = 0; i < jsonElements.size(); i++) {
                                JsonObject jsonObject = (JsonObject) jsonElements.get(i);
                                String pic = jsonObject.getAsJsonPrimitive("image1").getAsString();
                                if(pic == null) {
                                    pic = "https://i5.walmartimages.com/asr/f752abb3-1b49-4f99-b68a-7c4d77b45b40_1.39d6c524f6033c7c58bd073db1b99786.jpeg?odnHeight=450&odnWidth=450&odnBg=FFFFFF";
                                }
                                String title = jsonObject.getAsJsonPrimitive("title").getAsString();
                                int no = jsonObject.getAsJsonPrimitive("itemNo").getAsInt();
                                double x = jsonObject.getAsJsonPrimitive("mapX").getAsDouble();
                                double y = jsonObject.getAsJsonPrimitive("mapY").getAsDouble();

                                courseItems.add(new CourseItem(x, y, pic, no, title));
                            }
                            Log.d("checkLogCourseItem", courseItems.toString());
                        } else {
                            Log.d("--codeTag", String.valueOf(response.code()));
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    public void getDetail(int position) {
        Service.getRetrofit(getContext()).
                getDetail(courseItems.get(position).getNo()).
                enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        JsonArray jsonObject = response.body().getAsJsonArray("data");
                        JsonArray jsonElements = jsonObject.getAsJsonArray();
                        detailItems = getDetailArray(jsonElements);
                        detailAdapter = new CourseDetailRecycerViewAdapter(getContext(), detailItems);
                        detailRecyclerView.setAdapter(detailAdapter);
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    public ArrayList<CourseDetailItem> getDetailArray(JsonArray jsonElements) {
        ArrayList<CourseDetailItem> items = new ArrayList<>();

        for (int i = 0; i < jsonElements.size(); i++) {
            JsonObject jsonObject = (JsonObject) jsonElements.get(i);

            String title = jsonObject.getAsJsonPrimitive("title").getAsString();
            String content = jsonObject.getAsJsonPrimitive("content").getAsString();
            int number = jsonObject.getAsJsonPrimitive("no").getAsInt();
            String picture = jsonObject.getAsJsonPrimitive("image").getAsString();

            items.add(new CourseDetailItem(number, title, content, picture));
        }
        return items;
    }

    private void setMap() {
        Log.d("!@#!@##!@", "setMap: ");

        LinearLayout relativeLayout = (LinearLayout) view.findViewById(R.id.tmap_view);
        tMapView = new TMapView(getActivity());

        relativeLayout.addView(tMapView);

        tMapView.setSKPMapApiKey(getString(R.string.tmap_app_key));

        tMapView.setCompassMode(true);    // 현재 보는 방향
        tMapView.setIconVisibility(true);   // 아이콘 표시
        tMapView.setZoomLevel(13);   // 줌레벨
        tMapView.setMapType(TMapView.MAPTYPE_STANDARD);
        tMapView.setLanguage(TMapView.LANGUAGE_KOREAN);

//        tMapGps = new TMapGpsManager(getActivity());
//        tMapGps.setMinTime(1000);
//        tMapGps.setMinDistance(5);
//        tMapGps.setProvider(tMapGps.NETWORK_PROVIDER);  // 인터넷 이용 (실내일때 유용)
//        tMapGps.setProvider(tMapGps.GPS_PROVIDER);    // 현위치 gps 이용
//        tMapGps.OpenGps();

        tMapView.setTrackingMode(true);   //트래킹모드
        tMapView.setSightVisible(true);



        try {

            final LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, // 등록할 위치제공자
                    1000, // 통지사이의 최소 시간간격 (miliSecond)
                    1, // 통지사이의 최소 변경거리 (m)
                    mLocationListener);

            Log.d("setmap ====", "location updates");
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, mLocationListener);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, mLocationListener);

        } catch (SecurityException e) {
            e.printStackTrace();
        }


    }
    @Override
    public void onResume() {
        super.onResume();

        try {

            final LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, // 등록할 위치제공자
                    1000, // 통지사이의 최소 시간간격 (miliSecond)
                    1, // 통지사이의 최소 변경거리 (m)
                    mLocationListener);
            Log.d("setmap ====", "location updates");
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, mLocationListener);
//            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, mLocationListener);

        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }
    private boolean canAccessLocation() {
        return (hasPermission(Manifest.permission.ACCESS_FINE_LOCATION));
    }

    private boolean hasPermission(String perm) {
        return (PackageManager.PERMISSION_GRANTED == getActivity().checkSelfPermission(perm));
    }

    private boolean canAccessContacts() {
        return (hasPermission(Manifest.permission.READ_CONTACTS));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        setMap();
    }

    private void initSecene(int position) {

        Log.d("---position&item", String.valueOf(position) + String.valueOf(courseItems.get(position).getId()));

        getDetail(position);
    }

    private void transitionSecene(int position) {
        Log.d("---position&item", String.valueOf(position) + String.valueOf(courseItems.get(position).getId()));
        currentPosition = position;
        if (transitionAnimator != null) {
            transitionAnimator.cancel();
        }

        getDetail(position);

        transitionAnimator = ObjectAnimator.ofFloat(this, "transitionValue", 0.0f, 1.0f);
        transitionAnimator.setDuration(300);
        transitionAnimator.start();
        transitionAnimator.addListener(animatorListener);
    }

    public void setTransitionValue(float transitionValue) {
        this.transitionValue = transitionValue;
    }

    public float getTransitionValue() {
        return transitionValue;
    }

    @Override
    public void onScrollChanged(MyScrollView scrollView, int x, int y, int oldx, int oldy) {
        View view = (View) scrollView.getChildAt(scrollView.getChildCount() - 1);
        int diff = (view.getBottom() - (scrollView.getHeight() + scrollView.getScrollY()));

        // if diff is zero, then the bottom has been reached
        if (diff == 0) {
            detailRecyclerView.setNestedScrollingEnabled(true);
        } else {
            detailRecyclerView.setNestedScrollingEnabled(false);
        }
    }

    class ViewHolder {
        ImageView imageView;
        TextView courseName;
        TextView distance;
    }

    @Override
    public void onScrollChange(View view, int i, int i1, int i2, int i3) {
        detailRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == 0) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            companionBtn.setVisibility(View.VISIBLE);
                            companionBtn.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.show));
                        }
                    }, 1000);

                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if ((dy > 0) || (dy < 0)) {
                    companionBtn.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.hide));
                    companionBtn.setVisibility(View.INVISIBLE);
                }
            }

        });
    }

}
