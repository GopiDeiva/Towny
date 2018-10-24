package hatchure.towny.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OfferSortList {
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("offers_list")
    @Expose
    private List<OfferSortItem> offersSortList = null;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public List<OfferSortItem> getOffersSortList() {
        return offersSortList;
    }

    public void setOffersList(List<OfferSortItem> offersSortList) {
        this.offersSortList = offersSortList;
    }
}
