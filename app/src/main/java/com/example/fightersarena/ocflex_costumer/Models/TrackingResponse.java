package com.example.fightersarena.ocflex_costumer.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrackingResponse {

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
    private String date;
    @SerializedName("value")
    @Expose
    private List<TrackValue> value = null;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<TrackValue> getValue() {
        return value;
    }

    public void setValue(List<TrackValue> value) {
        this.value = value;
    }



    public class TrackValue {

        @SerializedName("associateId")
        @Expose
        private String associateId;
        @SerializedName("associateName")
        @Expose
        private String associateName;
        @SerializedName("latitude")
        @Expose
        private String latitude;
        @SerializedName("longitude")
        @Expose
        private String longitude;
        @SerializedName("trackTime")
        @Expose
        private String trackTime;

        public String getAssociateId() {
            return associateId;
        }

        public void setAssociateId(String associateId) {
            this.associateId = associateId;
        }

        public String getAssociateName() {
            return associateName;
        }

        public void setAssociateName(String associateName) {
            this.associateName = associateName;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getTrackTime() {
            return trackTime;
        }

        public void setTrackTime(String trackTime) {
            this.trackTime = trackTime;
        }

    }
}
