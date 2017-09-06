# Holidays

How to start the Holidays application
---

1. Run `mvn clean install` to build your application
1. Start application with `java -jar target/holidays-1.0-SNAPSHOT.jar server config.yml`
1. To check that your application is running enter url `http://localhost:8080`

How to change api key
---
Application starts with default api key to `https://holidayapi.com/` provided in `config.yml` on startup. 
In case if api key update is needed in runtime, send `POST` request to `http://localhost:8080/v1/key` with JSON body
```json
{
	"key" : "$newApiKey"
}
```

Assumptions
---

- if multiple holidays occur in same day in country, only one of them is returned
- lookup is performed max 100 years ahead
