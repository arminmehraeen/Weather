package com.armin.mehraein.weather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.armin.mehraein.weather.Presenter.GetData;
import com.armin.mehraein.weather.Presenter.IGetData;
import com.armin.mehraein.weather.Presenter.ISendName;
import com.armin.mehraein.weather.Presenter.RecycleAdapter;
import com.armin.mehraein.weather.Presenter.RecycleAdapter1;
import com.armin.mehraein.weather.View.IViewHandler;

import java.util.ArrayList;

import io.paperdb.Paper;

public class AddActivity extends AppCompatActivity implements IViewHandler, ISendName {

    private IGetData getData;
    private ImageView imageBack;
    private EditText inputAdd;
    private Button btnAdd;
    private RecyclerView add_recycle;
    private RecycleAdapter1 recycleAdapter1;
    private ArrayList<String> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        initView();
        Paper.init(getApplicationContext());

        arrayList = Paper.book().read("list",new ArrayList<String>());

        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        horizontalLayoutManager.setReverseLayout(true);
        horizontalLayoutManager.setStackFromEnd(true);
        add_recycle.setHasFixedSize(true);
        add_recycle.setLayoutManager(horizontalLayoutManager);

        getData = new GetData(getApplicationContext(),this);

        recycleAdapter1 = new RecycleAdapter1(arrayList,getApplicationContext(),this);
        add_recycle.setAdapter(recycleAdapter1);

        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddActivity.this,MainActivity.class));
                finish();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = inputAdd.getText().toString().trim();
                if (TextUtils.isEmpty(city)){
                    inputAdd.setError("Please enter city name");
                }else {
                    getData.connect(city);
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (false){
            super.onBackPressed();
        }
    }

    private void initView() {
        imageBack = findViewById(R.id.imageBack);
        inputAdd = findViewById(R.id.inputAdd);
        btnAdd = findViewById(R.id.btnAdd);
        add_recycle = findViewById(R.id.add_recycle);
    }

    @Override
    public void weatherMain(String main) {

    }

    @Override
    public void weatherDescription(String description) {

    }

    @Override
    public void weatherTemp(double temp) {

    }

    @Override
    public void weatherTempMax(double max) {

    }

    @Override
    public void weatherTempMin(double min) {

    }

    @Override
    public void weatherSpeed(float speed) {

    }

    @Override
    public void weatherCityCode(String code) {

    }

    @Override
    public void weatherCityName(String name) {
        arrayList.add(name);
        inputAdd.setText("");
        recycleAdapter1.notifyDataSetChanged();
        Paper.book().write("list",arrayList);
    }

    @Override
    public void responeError(String message) {
        inputAdd.setError("city not support");
    }

    @Override
    public void sendName(String name, int position) {
        arrayList.remove(position);
        recycleAdapter1.notifyDataSetChanged();
        Paper.book().write("list",arrayList);
    }
}
