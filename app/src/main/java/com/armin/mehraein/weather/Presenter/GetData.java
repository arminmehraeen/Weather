package com.armin.mehraein.weather.Presenter;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.armin.mehraein.weather.Model.Data;
import com.armin.mehraein.weather.View.IViewHandler;

import org.json.JSONException;
import org.json.JSONObject;

public class GetData implements IGetData{

    private static final String API_KEY =
            "8b77a1e86426e3da6062eedc00556559";
    private static final String URL_FORMAT_BY_CITY_NAME =
            "http://api.openweathermap.org/data/2.5/weather?q=%s&units=metric&APPID=" + API_KEY;
    private Context context;
    private int status;
    private IViewHandler handler;

    public GetData(Context context,IViewHandler handler) {
        this.context = context;
        this.handler = handler;
    }


    @Override
    public void connect(final String cityName) {
        String URL = String.format(URL_FORMAT_BY_CITY_NAME,cityName);

        final Data data = new Data();

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String name = response.getString("name");
                    data.setCountryName(name);
                    handler.weatherCityName(data.getCountryName());

                    double temp = response.getJSONObject("main").getDouble("temp");
                    data.setTemp(temp);
                    handler.weatherTemp(data.getTemp());

                    String main = response.getJSONArray("weather").getJSONObject(0).getString("main");
                    data.setMain(main);
                    handler.weatherMain(data.getMain());

                    String description = response.getJSONArray("weather").getJSONObject(0).getString("description");
                    data.setDescription(description);
                    handler.weatherDescription(data.getDescription());

                    double temp_min = response.getJSONObject("main").getDouble("temp_min");
                    data.setTemp_min(temp_min);
                    handler.weatherTempMin(data.getTemp_min());

                    double temp_max = response.getJSONObject("main").getDouble("temp_max");
                    data.setTemp_max(temp_max);
                    handler.weatherTempMax(data.getTemp_max());

                    float speed = (float) response.getJSONObject("wind").getDouble("speed");
                    data.setSpeed(speed);
                    handler.weatherSpeed(data.getSpeed());

                    String code = response.getJSONObject("sys").getString("country");
                    data.setCountryCode(code);
                    handler.weatherCityCode(data.getCountryCode());

                    status = response.getInt("cod");

                } catch (JSONException e) {
                    e.printStackTrace();
                    handler.responeError(e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                handler.responeError(error.toString());
            }
        });

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }
}
