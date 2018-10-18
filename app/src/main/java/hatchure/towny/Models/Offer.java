package hatchure.towny.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Offer {
    @SerializedName("shops_count")
    @Expose
    private String shopsCount;
    @SerializedName("offer_id")
    @Expose
    private String offerId;
    @SerializedName("offer_name")
    @Expose
    private String offerName;
    @SerializedName("offer_img")
    @Expose
    private String offerImg;
    @SerializedName("offer_description")
    @Expose
    private String offerDescription;
    @SerializedName("offer_extended_description")
    @Expose
    private String offerExtendedDescription;
    @SerializedName("distance")
    @Expose
    private String distance;

    public String getShopsCount() {
        return shopsCount;
    }

    public void setShopsCount(String shopsCount) {
        this.shopsCount = shopsCount;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getOfferName() {
        return offerName;
    }

    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }

    public String getOfferImg() {
        return offerImg;
    }

    public void setOfferImg(String offerImg) {
        this.offerImg = offerImg;
    }

    public String getOfferDescription() {
        return offerDescription;
    }

    public void setOfferDescription(String offerDescription) {
        this.offerDescription = offerDescription;
    }

    public String getOfferExtendedDescription() {
        return offerExtendedDescription;
    }

    public void setOfferExtendedDescription(String offerExtendedDescription) {
        this.offerExtendedDescription = offerExtendedDescription;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

}