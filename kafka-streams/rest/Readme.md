# 

## Send data to weather-data topic 

```curl
curl --location --request POST 'http://localhost:8089/api/v1/weather/' \
--header 'Content-Type: application/json' \
--data-raw '{
      "temperatureCelsius": 0.0,
      "humidity": 0.0

}'
```

## To put to kafka rest 
```
curl --location --request POST 'http://localhost:8082/topics/app-weather-data' \
--header 'Content-Type: application/vnd.kafka.json.v2+json' \
--data-raw '{
    "records": [
        {
            "value": {
                "temperatureCelsius": 0.0,
                "humidity": 0.0
            }
        }
    ]
}'
```