package io.getarrays.server.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Setter
@SuperBuilder
@JsonInclude(NON_NULL)
public class Response {

    protected LocalDateTime timeStamp;
    protected Map<?, ?> data;
    protected HttpStatus status;
    protected int statusCode;
    protected String reason;
    protected String message;
    protected String developerMessage;
}