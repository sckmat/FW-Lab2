package com.yegorov.demo;

import com.yegorov.weather.WeatherData;
import com.yegorov.weather.WeatherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/weather")
    public WeatherData getWeather(@RequestParam String city) {
        return weatherService.currentByCity(city);
    }
}
