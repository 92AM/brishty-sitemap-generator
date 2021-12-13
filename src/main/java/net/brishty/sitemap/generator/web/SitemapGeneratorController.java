package net.brishty.sitemap.generator.web;

import net.brishty.sitemap.generator.service.SitemapGeneratorService;
import net.brishty.sitemap.generator.service.domain.Sitemap;
import net.brishty.sitemap.generator.web.domain.SitemapResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SitemapGeneratorController {

    private final SitemapGeneratorService sitemapGeneratorService;
    private final SitemapToSitemapResponseDtoConverter sitemapToSitemapResponseDtoConverter;

    public SitemapGeneratorController(SitemapGeneratorService sitemapGeneratorService,
                                      SitemapToSitemapResponseDtoConverter sitemapToSitemapResponseDtoConverter) {
        this.sitemapGeneratorService = sitemapGeneratorService;
        this.sitemapToSitemapResponseDtoConverter = sitemapToSitemapResponseDtoConverter;
    }

    @GetMapping("/generate-sitemap")
    public SitemapResponseDto getDynamicSitemapLinks() {
        Sitemap sitemap = sitemapGeneratorService.getSiteMap();
        return sitemapToSitemapResponseDtoConverter.convert(sitemap);
    }

    @GetMapping("/heart-beat")
    public String getHeartBeat() {
        return "I am alive!";
    }
}
