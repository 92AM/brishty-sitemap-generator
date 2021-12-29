# Brishty Sitemap Generator

This is a simple Springboot application that processes over 200k cities from a given json file (provided
by https://openweathermap.org/) and generates a list of SEO friendly sitemap URL's for Brishty.net.

## Instructions

### Build project 

`./gradlew clean build`

### Start project

`./gradlew bootRun`

Note : the Springboot server should start on port `8080`.

## Endpoints

### GET /generate-sitemap/{pageNumber}

Gets list of SEO friendly URLs for brishty.net, supplied page number can be 0 - 19

Example response :

```json
{
  "links": [
    "https://www.brishty.net/weather/Wankyi",
    "https://www.brishty.net/weather/Jinxi",
    "https://www.brishty.net/weather/Copalillo",
    "https://www.brishty.net/weather/Wawern",
    "https://www.brishty.net/weather/Nenetskiy%20Avtonomnyy%20Okrug",
    "https://www.brishty.net/weather/Nedre%20Eiker",
    "https://www.brishty.net/weather/Glidden",
    "https://www.brishty.net/weather/Manguito",
    "https://www.brishty.net/weather/Albarreal%20de%20Tajo",
    "https://www.brishty.net/weather/Veshkayma",
    "https://www.brishty.net/weather/Pawonkow"
  ]
}
```

### GET /heart-beat

Sample "Heart beat" endpoint ...
