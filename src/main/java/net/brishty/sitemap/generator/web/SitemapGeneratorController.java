package net.brishty.sitemap.generator.web;

import net.brishty.sitemap.generator.service.SitemapGeneratorService;
import net.brishty.sitemap.generator.web.domain.Sitemap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SitemapGeneratorController {

    private final SitemapGeneratorService sitemapGeneratorService;

    public SitemapGeneratorController(SitemapGeneratorService sitemapGeneratorService) {
        this.sitemapGeneratorService = sitemapGeneratorService;
    }

    @GetMapping("/generate-sitemap")
    public Sitemap getDynamicSitemapLinks() {
        return sitemapGeneratorService.getSiteMap();
    }

    @GetMapping("/heart-beat")
    public String getHeartBeat() {
        return "I am alive!";
    }
}
