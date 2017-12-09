package com.mom.momtomom;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private List<String> feedingRoom_kor;
    //private List<Address> addr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        feedingRoom_kor = new ArrayList<>();
        //Geocoder mCoder = new Geocoder(this);

        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    /**
     * OnMapReady 는 map이 사용가능하면 호출되는 콜백 메소드
     * 여기서 marker 나 line, listener, camera 이동 등을 설정.
     * 이 시점에서, 만약 사용자 기기게 Google Play service가 설치되지 않으면
     * 설치하라고 메세지가 뜨게 되고,  설치후에 다시 이 앱으로 제어권이 넘어옴
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        Excel();

        //문제


        /*

        for (int i = 0; i < feedingRoom_kor.size(); i++) {
            try {
                addr = mCoder.getFromLocationName(feedingRoom_kor.get(i), 1);
                if(addr.size()!=0){
                    Double Lat = addr.get(0).getLatitude();
                    Double Lon = addr.get(0).getLongitude();
                    System.out.println("수유실:"+feedingRoom_kor.get(i));
                    System.out.println("좌표:"+Lat+":"+Lon);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        */


        // camera 좌표를 아주대 근처로 옮긴다
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(37.2821251, 127.04635589999998)));  // 위도, 경도
        // 구글지도(지구) 에서의 zoom 레벨은 1~23 까지 가능.
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(13);
        googleMap.animateCamera(zoom);   // moveCamera 는 바로 변경하지만,
        // animateCamera() 는 근거리에선 부드럽게 변경.

        // marker 표시
        // market 의 위치, 타이틀, 짧은설명 추가 가능.

        for (int i = 0; i < feedingRoom_kor.size(); i++) {
            String[] feedingMakerInfo_Token = feedingRoom_kor.get(i).split("#");
            String feedingRoomAdress = feedingMakerInfo_Token[0];
            double feedingRoomLat = Double.parseDouble(feedingMakerInfo_Token[1]);
            double feedingRoomLon = Double.parseDouble(feedingMakerInfo_Token[2]);

            MarkerOptions marker = new MarkerOptions();
            marker.position(new LatLng(feedingRoomLat, feedingRoomLon))
                    .title(feedingRoomAdress + " 수유실")
                    .snippet("수유실")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.googlemap_marker_icon));
            googleMap.addMarker(marker).showInfoWindow(); // 마커추가,화면에출력
        }

        // 마커클릭 이벤트 처리
        // GoogleMap 에 마커클릭 이벤트 설정 가능.
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                // 마커 클릭시 호출되는 콜백 메서드
                Intent intent = new Intent(getApplicationContext(), FeedingRoomActivity.class);
                intent.putExtra("latitude", marker.getPosition().latitude);
                intent.putExtra("longitude", marker.getPosition().longitude);
                intent.putExtra("feedingRoomTitle", marker.getTitle());
                startActivity(intent);
                return false;
            }
        });
    }

    public void Excel() {
        Workbook workbook;
        Sheet sheet;
        try {
            InputStream inputStream = getBaseContext().getResources().getAssets().open("2017_FeedingRoom_Parsing.xls");
            workbook = Workbook.getWorkbook(inputStream);
            sheet = workbook.getSheet(0);
            int MaxColumn = 2, RowStart = 3, RowEnd = sheet.getColumn(MaxColumn - 1).length - 1,
                    ColumnStart = 5,
                    ColumnStart1 = 6,
                    ColumnStart2 = 7;
            for (int row = RowStart; row <= RowEnd; row++) {
                String feedingAdress = sheet.getCell(ColumnStart, row).getContents();
                String feedingLat = sheet.getCell(ColumnStart1, row).getContents();
                String feedingLon = sheet.getCell(ColumnStart2, row).getContents();
                feedingRoom_kor.add(feedingAdress + "#" + feedingLat + "#" + feedingLon);
            }
            workbook.close();

        } catch (IOException | BiffException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}