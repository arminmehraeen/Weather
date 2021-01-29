package com.armin.mehraein.weather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.armin.mehraein.weather.Model.City;
import com.armin.mehraein.weather.Presenter.GetData;
import com.armin.mehraein.weather.Presenter.IGetData;
import com.armin.mehraein.weather.Presenter.ISendName;
import com.armin.mehraein.weather.Presenter.RecycleAdapter;
import com.armin.mehraein.weather.View.IViewHandler;

import java.util.ArrayList;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity implements IViewHandler, ISendName {

    private IGetData getData;
    private TextView Country , Wind , Temp , TempMin , TempMax
            , Main , Description , CountryCode , empty;
    private RecyclerView recyclerView;
    private ArrayList<String> arrayList = new ArrayList<>();
    private RecycleAdapter recycleAdapter;
    private ImageView imageAdd , imageRefresh;
    private LinearLayout noEmpty;
    private int index = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        Paper.init(getApplicationContext());

        arrayList = Paper.book().read("list",new ArrayList<String>());

        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);

        getData = new GetData(getApplicationContext(),this);

        recycleAdapter = new RecycleAdapter(arrayList,getApplicationContext(),this);
        recyclerView.setAdapter(recycleAdapter);

        imageRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arrayList.size() != 0) {
                    imageRefresh.animate().rotationBy(360).setDuration(2000);
                    getData.connect(arrayList.get(index));
                }else {
                    imageRefresh.animate().rotationBy(360).setDuration(2000);
                    empty.setText("No city exit for update");
                }
            }
        });
        imageAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddActivity.class);
                startActivity(intent);
               //startActivity(new Intent(MainActivity.this,AddActivity.class));
               finish();
            }
        });

        if (arrayList.size() != 0){
            empty.setVisibility(View.GONE);
            noEmpty.setVisibility(View.VISIBLE);
            getData.connect(arrayList.get(0));
        }else{
            empty.setVisibility(View.VISIBLE);
            noEmpty.setVisibility(View.GONE);
            empty.setText("No city exit \n Please add a city");
        }

    }

    private void initView(){
        empty = findViewById(R.id.empty);
        noEmpty = findViewById(R.id.noEmpty);
        recyclerView = findViewById(R.id.home_recycle);
        Country = findViewById(R.id.Country);
        CountryCode = findViewById(R.id.CountryCode);
        Temp = findViewById(R.id.Temp);
        TempMax = findViewById(R.id.TempMax);
        TempMin = findViewById(R.id.TempMin);
        Wind = findViewById(R.id.Wind);
        Main = findViewById(R.id.Main);
        Description = findViewById(R.id.Description);

        imageAdd = findViewById(R.id.imageAdd);
        imageRefresh = findViewById(R.id.imageRefresh);
    }

    @Override
    public void weatherMain(String main) {
        Main.setText(main);
    }

    @Override
    public void weatherDescription(String description) {
        Description.setText(description);
    }

    @Override
    public void weatherTemp(double temp) {
        String tempString = temp+"";
        Temp.setText(tempString + " ℃");
    }

    @Override
    public void weatherTempMax(double max) {
        max = ((int) max);
        TempMax.setText(max + " ↥");
    }

    @Override
    public void weatherTempMin(double min) {
        min = ((int) min);
        TempMin.setText("↧ " + min);
    }

    @Override
    public void weatherSpeed(float speed) {
        Wind.setText(speed + " Km/s");
    }

    @Override
    public void weatherCityCode(String code) {
        CountryCode.setText(code);
    }

    @Override
    public void weatherCityName(String name) {
        Country.setText("☉ " + name.toUpperCase());
    }

    @Override
    public void responeError(String message) {
        //txt.setText(message);
    }

    @Override
    public void sendName(String name , int position) {
        getData.connect(name);
        index = position;

    }
}
