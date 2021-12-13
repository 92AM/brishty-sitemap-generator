package net.brishty.sitemap.generator.web;

import net.brishty.sitemap.generator.service.domain.Sitemap;
import net.brishty.sitemap.generator.web.domain.SitemapResponseDto;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SitemapToSitemapResponseDtoConverter {

    public SitemapResponseDto convert(Sitemap sitemap) {
        return SitemapResponseDto.builder()
                .links(Optional.of(sitemap).map(Sitemap::getLinks).orElse(null))
                .build();
    }
}
