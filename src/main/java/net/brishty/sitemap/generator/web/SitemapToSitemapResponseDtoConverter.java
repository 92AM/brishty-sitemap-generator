package net.brishty.sitemap.generator.web;

import net.brishty.sitemap.generator.service.domain.Sitemap;
import net.brishty.sitemap.generator.web.domain.SitemapResponseDto;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class SitemapToSitemapResponseDtoConverter {

    public SitemapResponseDto convert(Sitemap sitemap) {

        List<String> links = Optional.of(sitemap)
                .map(Sitemap::getLinks)
                .orElse(Collections.emptyList());

        return SitemapResponseDto.builder()
                .links(links)
                .build();
    }
}
