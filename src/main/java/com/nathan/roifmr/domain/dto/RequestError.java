package com.nathan.roifmr.domain.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RequestError {
    private LocalDateTime occuredAt;
    private int errorCode;
    private String summary;
    private List<String> details;

    public RequestError() {
        this.occuredAt = LocalDateTime.now();
        this.details = new ArrayList<>();
    }

    public RequestError(int errorCode, String summary) {
        this();
        this.errorCode = errorCode;
        this.summary = summary;
    }

    public RequestError(int errorCode, String summary, List<String> details) {
        this(errorCode,summary);
        this.details = details;
    }

    public LocalDateTime getOccuredAt() {
        return occuredAt;
    }

    public void setOccuredAt(LocalDateTime occuredAt) {
        this.occuredAt = occuredAt;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestError that = (RequestError) o;
        return errorCode == that.errorCode &&
                occuredAt.equals(that.occuredAt) &&
                summary.equals(that.summary) &&
                Objects.equals(details, that.details);
    }

    @Override
    public int hashCode() {
        return Objects.hash(occuredAt, errorCode, summary, details);
    }
}
