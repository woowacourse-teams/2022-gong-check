package com.woowacourse.gongcheck.acceptance;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.gongcheck.application.HostAuthService;
import com.woowacourse.gongcheck.application.response.GithubAccessTokenResponse;
import com.woowacourse.gongcheck.application.response.GithubProfileResponse;
import com.woowacourse.gongcheck.application.response.TokenResponse;
import com.woowacourse.gongcheck.presentation.request.TokenRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class FakeHostAuthController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private HostAuthService hostAuthService;

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/fake/login")
    public ResponseEntity<TokenResponse> login() throws JsonProcessingException {
        MockRestServiceServer mockRestServiceServer = MockRestServiceServer.createServer(restTemplate);
        mockRestServiceServer.expect(requestTo("https://github.com/login/oauth/access_token"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString(new GithubAccessTokenResponse("token"))));

        GithubProfileResponse githubProfileResponse = new GithubProfileResponse("nickname", "loginName",
                "2", "test.com");
        mockRestServiceServer.expect(requestTo("https://api.github.com/user"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString(githubProfileResponse)));

        TokenResponse result = hostAuthService.createToken(new TokenRequest("code"));
        return ResponseEntity.ok(result);
    }

    @PostMapping("/fake/signup")
    public ResponseEntity<TokenResponse> signup() throws JsonProcessingException {
        MockRestServiceServer mockRestServiceServer = MockRestServiceServer.createServer(restTemplate);
        mockRestServiceServer.expect(requestTo("https://github.com/login/oauth/access_token"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString(new GithubAccessTokenResponse("token"))));

        GithubProfileResponse githubProfileResponse = new GithubProfileResponse("nickname", "loginName",
                "3", "test.com");
        mockRestServiceServer.expect(requestTo("https://api.github.com/user"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString(githubProfileResponse)));

        TokenResponse result = hostAuthService.createToken(new TokenRequest("code"));
        return ResponseEntity.ok(result);
    }
}
