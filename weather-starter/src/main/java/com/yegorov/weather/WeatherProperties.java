package com.yegorov.weather;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "weather")
public class WeatherProperties {

    private boolean enabled = true;

    private String apiBase = "https://api.open-meteo.com/v1/forecast";

    private String geocodeBase = "https://geocoding-api.open-meteo.com/v1/search";

    private boolean useCurrentWeather = true;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getApiBase() {
        return apiBase;
    }

    public void setApiBase(String apiBase) {
        this.apiBase = apiBase;
    }

    public String getGeocodeBase() {
        return geocodeBase;
    }

    public void setGeocodeBase(String geocodeBase) {
        this.geocodeBase = geocodeBase;
    }

    public boolean isUseCurrentWeather() {
        return useCurrentWeather;
    }

    public void setUseCurrentWeather(boolean useCurrentWeather) {
        this.useCurrentWeather = useCurrentWeather;
    }
}
