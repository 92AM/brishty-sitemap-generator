package net.brishty.sitemap.generator.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import lombok.extern.log4j.Log4j2;
import net.brishty.sitemap.generator.service.domain.City;
import net.brishty.sitemap.generator.service.domain.Sitemap;
import net.brishty.sitemap.generator.service.domain.SupportedCities;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Log4j2
@Service
public class SitemapGeneratorService {

    private static final String CITY_LIST_FILE_NAME = "city.list.min.json";
    private static final String BRISHTY_WEATHER_PATH = "https://www.brishty.net/weather/";
    private static final int CHUNK_SIZE = 20;

    private final ObjectMapper objectMapper;
    private final Map<Integer, Sitemap> sitemaps;

    public SitemapGeneratorService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.sitemaps = this.loadSitemaps();
    }

    public Sitemap getSiteMap(int key) {
        return this.sitemaps.get(key);
    }

    private Map<Integer, Sitemap> loadSitemaps() {
        try {
            ClassPathResource resource = new ClassPathResource(CITY_LIST_FILE_NAME);
            String mappedJsonAsString = IOUtils.toString(resource.getInputStream(), StandardCharsets.UTF_8);

            SupportedCities supportedCities = objectMapper.readValue(
                    mappedJsonAsString,
                    SupportedCities.class
            );

            List<String> links = mapToLinks(supportedCities);
            return collectSitemapsToMaps(mapToSitemaps(links));
        } catch (Exception ex) {
            log.error(String.format(
                    "Error occurred while loading %s file, empty map of sitemaps will be returned. " +
                            "Here is the error message : %s",
                    CITY_LIST_FILE_NAME,
                    ex.getMessage()
            ));
            return Collections.emptyMap();
        }
    }

    private List<Sitemap> mapToSitemaps(List<String> links) {
        return Lists.partition(links, links.size() / CHUNK_SIZE)
                .parallelStream()
                .map(partitionedLinks -> Sitemap.builder().links(partitionedLinks).build())
                .collect(Collectors.toList());
    }

    private Map<Integer, Sitemap> collectSitemapsToMaps(List<Sitemap> sitemaps) {
        return IntStream.range(0, sitemaps.size()).boxed()
                .collect(Collectors.toMap(index -> index, sitemaps::get));
    }

    private List<String> mapToLinks(SupportedCities supportedCities) {
        Set<String> deDupedLinks = supportedCities.getCities()
                .parallelStream()
                .map(this::generateSitemapLink)
                .collect(Collectors.toSet());

        return deDupedLinks.parallelStream()
                .sorted(Comparator.naturalOrder())
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
