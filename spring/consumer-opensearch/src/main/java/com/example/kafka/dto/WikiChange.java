package com.example.kafka.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WikiChange {

    Meta meta;
    Long id;
    String type;
    String title;
    @JsonProperty("title_url")
    String titleUrl;
    String comment;
    Long timestamp;
    String user;
    Boolean bot;
    Boolean minor;
    ChangeLength length;

    @JsonProperty("log_type")
    String logType;

    @JsonProperty("log_action")
    String logAction;

    @JsonProperty("log_action_comment")
    String logActionComment;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ChangeLength {

        @JsonProperty("old")
        Integer oldVal;

        @JsonProperty("new")
        Integer newVal;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Meta {

        String id;
    }
}
