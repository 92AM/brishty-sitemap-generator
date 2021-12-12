package net.brishty.sitemap.generator.service.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Value;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Coord {

    @JsonProperty("lon")
    Double lon;

    @JsonProperty("lat")
    Double lat;
}
