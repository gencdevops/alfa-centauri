package com.example.cgorder.service;


import com.example.cgorder.dto.SlackContextMessage;
import com.example.cgorder.dto.SlackDetailMessage;
import com.example.cgorder.dto.SlackMessage;
import com.example.cgorder.dto.SlackMessageBlock;
import io.micrometer.core.instrument.util.IOUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;


@Component
@Slf4j
public class SlackReportingService {
    @Value("${slack.web.hook.base.url}")
    private String webHookBaseUrl;
    @Value("${slack.backend.errors.channel}")
    private String errorsChannel;

    private final RestTemplate restTemplate = getInstance();





    public void sendErrorMessage(String subject, Throwable e) {
        restTemplate.postForEntity(webHookBaseUrl + errorsChannel, getSlackMessage(subject, null, e), String.class);
    }

    private SlackMessageBlock getSlackMessage(String subject, String msg, Throwable e) {

        String stackTrace = e != null ? ExceptionUtils.getStackTrace(e).replace("\n", "<br>") : Strings.EMPTY;

        SlackMessage headerMessage = SlackMessage.builder()
                .text(SlackDetailMessage.builder()
                        .type("plain_text")
                        .text("😱" + subject + "😰")
                        .build())
                .type("header")
                .build();

        SlackContextMessage slackContextMessage = SlackContextMessage.builder()
                .elements(Collections.singletonList(SlackDetailMessage
                        .builder()
                        .type("mrkdwn")
                        .text("```" + (Objects.nonNull(msg) ? msg : StringUtils.EMPTY) + stackTrace + "```")
                        .build()))
                .type("context")
                .build();

        return SlackMessageBlock.builder()
                .blocks(Arrays.asList(headerMessage, slackContextMessage))
                .build();
    }

    public RestTemplate getInstance() {
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout(1000);
        requestFactory.setConnectionRequestTimeout(1000);
        final RestTemplate template = new RestTemplate(requestFactory);
        template.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                log.error("ERROR TEXT: {} {}", response.getRawStatusCode(), response.getStatusText());
                log.error("response body: {} ", IOUtils.toString(response.getBody(), StandardCharsets.UTF_8));
                super.handleError(response);
            }
        });
        return template;
    }
}
