package com.example.fightersarena.ocflex_costumer.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Register {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("iserror")
    @Expose
    private Boolean iserror;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("date")
    @Expose
    private Object date;
    @SerializedName("value")
    @Expose
    private Value value;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Boolean getIserror() {
        return iserror;
    }

    public void setIserror(Boolean iserror) {
        this.iserror = iserror;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getDate() {
        return date;
    }

    public void setDate(Object date) {
        this.date = date;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    public class Value {

        @SerializedName("succeeded")
        @Expose
        private Boolean succeeded;
        @SerializedName("errors")
        @Expose
        private List<Object> errors = null;

        public Boolean getSucceeded() {
            return succeeded;
        }

        public void setSucceeded(Boolean succeeded) {
            this.succeeded = succeeded;
        }

        public List<Object> getErrors() {
            return errors;
        }

        public void setErrors(List<Object> errors) {
            this.errors = errors;
        }
    }
}
