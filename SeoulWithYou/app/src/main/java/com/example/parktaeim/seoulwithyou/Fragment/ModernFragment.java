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
import com.example.parktaeim.seoulwithyou.Network.Service;
import com.example.parktaeim.seoulwithyou.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.skp.Tmap.TMapData;
import com.skp.Tmap.TMapGpsManager;
import com.skp.Tmap.TMapPoint;
import com.skp.Tmap.TMapPolyLine;
import com.skp.Tmap.TMapView;
import com.stone.pile.libs.PileLayout;

import java.security.Permission;
import java.util.ArrayList;
import java.util.Map;

import com.example.parktaeim.seoulwithyou.util.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by user on 2017-10-11.
 */

@RequiresApi(api = Build.VERSION_CODES.M)
public class ModernFragment extends Fragment implements RecyclerView.OnScrollChangeListener, TMapGpsManager.onLocationChangedCallback {

    static final LatLng SEOUL = new LatLng(37.56, 126.97);


    private RecyclerView detailRecyclerView;
    private RecyclerView.Adapter detailAdapter;
    private RecyclerView.LayoutManager detailManger;
    private ArrayList<CourseItem> courseItems;
    private ObjectAnimator transitionAnimator;
    private ImageButton companionBtn;
    private Animator.AnimatorListener animatorListener;
    private PileLayout pileLayout;
    //    private MapView mapView;
    private GoogleMap myMap;
    private int currentPosition;

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

    @Override
    public void onLocationChange(Location location) {
        Log.d("start", "onlocation change!!");
        tMapView.setLocationPoint(location.getLongitude(), location.getLatitude());
        tMapView.setCenterPoint(location.getLongitude(), location.getLatitude());
        Log.d(String.valueOf(location.getLongitude()), String.valueOf(location.getLatitude()));

        currentLat = location.getLatitude();
        currentLon = location.getLongitude();
    }

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if (location != null) ;
            tMapView.setLocationPoint(location.getLongitude(), location.getLatitude());
            tMapView.setCenterPoint(location.getLongitude(), location.getLatitude());
            Log.d(String.valueOf(location.getLongitude()), String.valueOf(location.getLatitude()));

            currentLat = location.getLatitude();
            currentLon = location.getLongitude();

            final TMapPoint startPoint = new TMapPoint(currentLat, currentLon);   // 현재 위치
            final TMapPoint destPoint = new TMapPoint(36.316889, 127.158272);  // 도착 위치

            if(currentLat != 0){
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

        }


        companionBtn = (ImageButton) view.findViewById(R.id.companionBtn);
        companionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchCompanionActivity.class);
                intent.putExtra("picture", courseItems.get(currentPosition).getPicUrl());
                intent.putExtra("title", courseItems.get(currentPosition).getPlaceName());
                intent.putExtra("distance", courseItems.get(currentPosition).getPlaceDistance());
                intent.putExtra("id", courseItems.get(currentPosition).getId());
                startActivity(intent);
            }
        });

        detailRecyclerView = (RecyclerView) view.findViewById(R.id.detailRecyclerView);
        detailManger = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        detailRecyclerView.hasFixedSize();
        detailRecyclerView.setLayoutManager(detailManger);
        pileLayout = (PileLayout) view.findViewById(R.id.pileLayout);

        detailRecyclerView.setOnScrollChangeListener(this);

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

        initDtalist();

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
                    viewHolder.distance = (TextView) view.findViewById(R.id.courseDistanceText);
                    view.setTag(viewHolder);
                }
                Glide.with(getContext()).load(courseItems.get(position).getPicUrl()).into(viewHolder.imageView);
                viewHolder.courseName.setText(courseItems.get(position).getPlaceName());
                viewHolder.distance.setText(courseItems.get(position).getPlaceDistance());
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

        return view;
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

        tMapGps = new TMapGpsManager(getActivity());
        tMapGps.setMinTime(1000);
        tMapGps.setMinDistance(5);
        tMapGps.setProvider(tMapGps.NETWORK_PROVIDER);  // 인터넷 이용 (실내일때 유용)
//        tMapGps.setProvider(tMapGps.GPS_PROVIDER);    // 현위치 gps 이용
        tMapGps.OpenGps();

        tMapView.setTrackingMode(true);   //트래킹모드
        tMapView.setSightVisible(true);


        try{
            Log.d("setmap ====","location updates");
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 1, mLocationListener);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,100,1,mLocationListener);

        }catch (SecurityException e){
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

    private void initSecene(int position) {

        Log.d("---position&item", String.valueOf(position) + String.valueOf(courseItems.get(position).getId()));

        dataSet1();
    }

    private void transitionSecene(int position) {
        Log.d("---position&item", String.valueOf(position) + String.valueOf(courseItems.get(position).getId()));
        currentPosition = position;
        if (transitionAnimator != null) {
            transitionAnimator.cancel();
        }

        int id = courseItems.get(position).getId();

        if (id == 0) {
            dataSet1();
        } else if (id == 1) {
            dataSet2();
        } else if (id == 2) {
            dataSet3();
        }

        transitionAnimator = ObjectAnimator.ofFloat(this, "transitionValue", 0.0f, 1.0f);
        transitionAnimator.setDuration(300);
        transitionAnimator.start();
        transitionAnimator.addListener(animatorListener);
    }

    private void initDtalist() {

        Service.getRetrofit(getContext()).
                getModernCourseList().
                enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.code() == 200) {
                            JsonObject jsonObject = response.body();
                        } else {
                            Log.d("--codeTag", String.valueOf(response.code()));
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Log.d("error", t.toString());
                    }
                });
        courseItems = new ArrayList<>();
        CourseItem item1 = new CourseItem("http://img.hb.aicdn.com/10dd7b6eb9ca02a55e915a068924058e72f7b3353a40d-ZkO3ko_fw658", "palace", "far", 0);
        courseItems.add(item1);
        CourseItem item2 = new CourseItem("http://img.hb.aicdn.com/a3a995b26bd7d58ccc164eafc6ab902601984728a3101-S2H0lQ_fw658", "dessert", "as well", 1);
        courseItems.add(item2);
        CourseItem item3 = new CourseItem("http://pic4.nipic.com/20091124/3789537_153149003980_2.jpg", "kingdom", "near", 2);
        courseItems.add(item3);
    }


    public void setTransitionValue(float transitionValue) {
        this.transitionValue = transitionValue;
    }

    public float getTransitionValue() {
        return transitionValue;
    }


    //    view holder, dataset, fragment life cycle
    class ViewHolder {
        ImageView imageView;
        TextView courseName;
        TextView distance;
    }


    public void dataSet1() {
        ArrayList<CourseDetailItem> items = new ArrayList<>();

        CourseDetailItem item1 = new CourseDetailItem(
                "http://img.hb.aicdn.com/4ba573e93c6fe178db6730ba05f0176466056dbe14905-ly0Z43_fw658",
                "name 1st",
                "1",
                "Hello from the other side");
        items.add(item1);
        CourseDetailItem item2 = new CourseDetailItem(
                "http://img.hb.aicdn.com/4bc60d00aa3184f1f98e418df6fb6abc447dc814226ef-ZtS8hB_fw658",
                "name 1st",
                "2",
                "details");
        items.add(item2);
        CourseDetailItem item3 = new CourseDetailItem(
                "http://img.hb.aicdn.com/d9a48c272914c5253eceac26c51a56a26f4e50d048ba7-IJsbou_fw658",
                "name 1st",
                "3",
                "details");
        items.add(item3);

        detailAdapter = new CourseDetailRecycerViewAdapter(getContext(), items);
        detailRecyclerView.setAdapter(detailAdapter);
    }

    public void dataSet2() {
        ArrayList<CourseDetailItem> items = new ArrayList<>();

        CourseDetailItem item1 = new CourseDetailItem("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSYSnfkSMtgS2IAk9xRPwr99OOwoL-lJeNztczPD58wXVYCrKZM",
                "name 1st",
                "1",
                "Meaningless mock-up, mock turtle soup spilled on a mock turtle neck. Mach I Convertible copy. To kill a mockingbird, you need only force it to read this copy. This is Meaningless filler. (Elvis movies.) It is not meant to be a forum for value judgments nor a scholarly diatribe on how virtue should be measured. The whole point here (if such a claim can be made in an admittedly pointless paragraph) is that this is dummy copy.  Real bullets explode with destructive intensity. Such is not the case with dummy bullets. In fact, they don't explode at all. Duds. Dull thuds. Dudley do-wrongs. And do-wrongs don't make a right. Why on earth are you still reading this? Haven't you realized it's just dummy copy? How many times must you be reminded that it's really not meant to be read? You're only wasting precious time. But be that as it may, you've got to throw in a short paragraph from time to time. Here's a short paragraph.\n");
        items.add(item1);
        CourseDetailItem item2 = new CourseDetailItem("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSYSnfkSMtgS2IAk9xRPwr99OOwoL-lJeNztczPD58wXVYCrKZM",
                "name 1st",
                "2",
                "details");
        items.add(item2);
        CourseDetailItem item3 = new CourseDetailItem("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSYSnfkSMtgS2IAk9xRPwr99OOwoL-lJeNztczPD58wXVYCrKZM",
                "name 1st",
                "3",
                "details");
        items.add(item3);

        detailAdapter = new CourseDetailRecycerViewAdapter(getContext(), items);
        detailRecyclerView.setAdapter(detailAdapter);
    }

    public void dataSet3() {
        ArrayList<CourseDetailItem> items = new ArrayList<>();

        CourseDetailItem item1 = new CourseDetailItem(
                "http://img.hb.aicdn.com/03d474bbe20efb7df9aed4541ace70b53b53c70bdfe3-8djYVv_fw658",
                "name 1st",
                "1",
                "Meaningless mock-up, mock turtle soup spilled on a mock turtle neck. Mach I Convertible copy. To kill a mockingbird, you need only force it to read this copy. This is Meaningless filler. (Elvis movies.) It is not meant to be a forum for value judgments nor a scholarly diatribe on how virtue should be measured. The whole point here (if such a claim can be made in an admittedly pointless paragraph) is that this is dummy copy.  Real bullets explode with destructive intensity. Such is not the case with dummy bullets. In fact, they don't explode at all. Duds. Dull thuds. Dudley do-wrongs. And do-wrongs don't make a right. Why on earth are you still reading this? Haven't you realized it's just dummy copy? How many times must you be reminded that it's really not meant to be read? You're only wasting precious time. But be that as it may, you've got to throw in a short paragraph from time to time. Here's a short paragraph.\n");
        items.add(item1);
        CourseDetailItem item2 = new CourseDetailItem(
                "http://img.hb.aicdn.com/004cddd40519846281526b4b25fbdea36b31d01e190dd-7zlmuG_fw658",
                "name 1st",
                "2",
                "details");
        items.add(item2);
        CourseDetailItem item3 = new CourseDetailItem(
                "http://img.hb.aicdn.com/a58eda8a9a2a3f30f0a694c2702e1aba71d97d616d34f-rqv6FA_fw658",
                "name 1st",
                "3",
                "details");
        items.add(item3);

        detailAdapter = new CourseDetailRecycerViewAdapter(getContext(), items);
        detailRecyclerView.setAdapter(detailAdapter);
    }


}
