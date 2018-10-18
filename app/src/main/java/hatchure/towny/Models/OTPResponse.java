package hatchure.towny.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OTPResponse {
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("otp")
    @Expose
    private String otp;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

}