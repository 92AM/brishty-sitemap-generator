package net.brishty.sitemap.generator.web;

import net.brishty.sitemap.generator.service.domain.Sitemap;
import net.brishty.sitemap.generator.web.domain.SitemapResponseDto;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static java.util.List.copyOf;

@Component
public class SitemapToSitemapResponseDtoConverter {

    public SitemapResponseDto convert(Sitemap sitemap) {

        Set<String> links = Optional.of(sitemap)
                .map(Sitemap::getLinks)
                .orElse(Collections.emptySet());

        return SitemapResponseDto.builder()
                .links(copyOf(links))
                .build();
    }
}
