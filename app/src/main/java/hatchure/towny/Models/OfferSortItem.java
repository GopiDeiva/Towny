package hatchure.towny.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OfferSortItem {
    @SerializedName("offersort_id")
    @Expose
    private String offersortId;
    @SerializedName("offersort_name")
    @Expose
    private String offersortName;

    public String getOffersortId() {
        return offersortId;
    }

    public void setOffersortId(String offersortId) {
        this.offersortId = offersortId;
    }

    public String getOffersortName() {
        return offersortName;
    }

    public void setOffersortName(String offersortName) {
        this.offersortName = offersortName;
    }

}
