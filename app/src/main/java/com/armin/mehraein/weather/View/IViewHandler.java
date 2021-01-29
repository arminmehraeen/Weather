package com.armin.mehraein.weather.View;

public interface IViewHandler {

    void weatherMain(String main);
    void weatherDescription(String description);
    void weatherTemp(double temp);
    void weatherTempMax(double max);
    void weatherTempMin(double min);
    void weatherSpeed(float speed);
    void weatherCityCode(String code);
    void weatherCityName(String code);
    void responeError(String message);

}
