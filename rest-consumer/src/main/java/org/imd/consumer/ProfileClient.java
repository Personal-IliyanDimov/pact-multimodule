package org.imd.consumer;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * This class setup a `RestTemplate` defined in `WebConfig` to consume the API
 * provided by the `Producer`
 */
@Service
@RequiredArgsConstructor
public class ProfileClient {

    private String baseUrl = "http://localhost:9090/";
    private final RestTemplate restTemplate;

    public List<Profile> getAllProfiles() {
        return this.restTemplate.exchange(baseUrl + "/profiles", HttpMethod.GET, null, new ParameterizedTypeReference<List<Profile>>() {}).getBody();
    }

    public Profile getSingleProfile(int id) {
        return this.restTemplate.exchange(baseUrl + "/profiles/{id}", HttpMethod.GET, null, Profile.class, id).getBody();
    }

    public void setBaseUrl(String url) {
        this.baseUrl = url;
    }

}
