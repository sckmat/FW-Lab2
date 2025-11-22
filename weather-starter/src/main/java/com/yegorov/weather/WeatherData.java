package com.yegorov.weather;

public record WeatherData(
        String city,
        double latitude,
        double longitude,
        String timezone,
        double temperatureC,
        double windSpeed,
        String source
) {}
