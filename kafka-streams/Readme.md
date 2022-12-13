
run confluent 

```
confluent local services start 
```

run streams application 

http://localhost:8082/topics/weather-data
content-type | application/vnd.kafka.json.v2+json
```json
{
    "records": [
        {
            "key":5,
            "value": {
                "temperatureCelsius": 35.0,
                "humidity":310
            }
        }
    ]
}
```



logging.level.com.example.streams=INFO

