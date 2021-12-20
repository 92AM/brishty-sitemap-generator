package net.brishty.sitemap.generator.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j;
import net.brishty.sitemap.generator.service.domain.City;
import net.brishty.sitemap.generator.service.domain.Sitemap;
import net.brishty.sitemap.generator.service.domain.SupportedCities;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Log4j
@Service
public class SitemapGeneratorService {

    private static final String CITY_LIST_FILE_NAME = "city.list.min.json";
    private static final String BRISHTY_WEATHER_PATH = "https://www.brishty.net/weather/";

    private final ObjectMapper objectMapper;
    private final Sitemap sitemap;

    public SitemapGeneratorService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.sitemap = this.loadSitemap();
    }

    public Sitemap getSiteMap() {
        return this.sitemap;
    }

    private Sitemap loadSitemap() {
        try {
            ClassPathResource resource = new ClassPathResource(CITY_LIST_FILE_NAME);
            String mappedJsonAsString = IOUtils.toString(resource.getInputStream(), StandardCharsets.UTF_8);

            SupportedCities supportedCities = objectMapper.readValue(
                    mappedJsonAsString,
                    SupportedCities.class
            );

            List<String> links = mapToLinks(supportedCities);

            return Sitemap.builder()
                    .links(links)
                    .build();

        } catch (Exception e) {
            log.error(String.format(
                    "Error occurred while loading %s file, empty list of sitemap links will be returned. " +
                            "Here is the error message : %s",
                    CITY_LIST_FILE_NAME,
                    e.getMessage()
            ));
            return Sitemap.builder().build();
        }
    }

    private List<String> mapToLinks(SupportedCities supportedCities) {
        return supportedCities.getCities()
                .parallelStream()
                .map(this::generateSitemapLink)
                .collect(Collectors.toSet())
                .stream()
                .sorted()
                .collect(Collectors.toList());
    }

    private String generateSitemapLink(City city) {
        String sanitisedCity = StringUtils
                .stripAccents(city.getName().trim())
                .replace("‘", "")
                .replace("'", "")
                .replace("’", "")
                .replace("`", "")
                .replace(" ", "%20");

        return String.format(BRISHTY_WEATHER_PATH + "%s", sanitisedCity);
    }
}
