package com.se.sample.rest;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.se.sample.WeatherData;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/weather")
public class WeatherRestController {

    private WeatherService weatherService;

    @PostMapping("/")
    public  void postWeatherData(@RequestBody WeatherData weatherData) throws JsonProcessingException {
        weatherService.postWeatherData(weatherData);
    }
}
