package com.yegorov.weather;

import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

class DefaultWeatherService implements WeatherService {

    private final RestClient rest;
    private final WeatherProperties props;

    DefaultWeatherService(RestClient.Builder builder, WeatherProperties props) {
        this.rest = builder.build();
        this.props = props;
    }

    @Override
    @SuppressWarnings("unchecked")
    public WeatherData currentByCity(String city) {
        var geoUri = UriComponentsBuilder
                .fromHttpUrl(props.getGeocodeBase())
                .queryParam("name", city)
                .queryParam("count", 1)
                .queryParam("language", "en")
                .build()
                .toUri();

        Map<String, Object> geo =
                rest.get().uri(geoUri).retrieve().body(Map.class);

        if (geo == null || !geo.containsKey("results")) {
            throw new IllegalArgumentException("City not found: " + city);
        }

        var results = (List<Map<String, Object>>) geo.get("results");
        if (results == null || results.isEmpty()) {
            throw new IllegalArgumentException("City not found: " + city);
        }

        Map<String, Object> first = results.get(0);
        double lat = ((Number) first.get("latitude")).doubleValue();
        double lon = ((Number) first.get("longitude")).doubleValue();
        String tz = (String) first.getOrDefault("timezone", "auto");

        var forecastUri = UriComponentsBuilder
                .fromHttpUrl(props.getApiBase())
                .queryParam("latitude", lat)
                .queryParam("longitude", lon)
                .queryParam("current_weather", props.isUseCurrentWeather())
                .queryParam("timezone", tz)
                .build()
                .toUri();

        Map<String, Object> forecast =
                rest.get().uri(forecastUri).retrieve().body(Map.class);

        if (forecast == null || !forecast.containsKey("current_weather")) {
            throw new IllegalStateException("No current weather for city: " + city);
        }

        Map<String, Object> current =
                (Map<String, Object>) forecast.get("current_weather");

        double temp = ((Number) current.get("temperature")).doubleValue();
        double wind = ((Number) current.get("windspeed")).doubleValue();

        return new WeatherData(city, lat, lon, tz, temp, wind, "open-meteo");
    }
}
