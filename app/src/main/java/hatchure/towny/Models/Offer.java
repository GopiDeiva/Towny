package hatchure.towny.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Offer {
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
    @SerializedName("offer_basepath")
    @Expose
    private String offerBasepath;
    @SerializedName("offer_expires")
    @Expose
    private String offerExpires;
    @SerializedName("shop_name")
    @Expose
    private String shopName;
    @SerializedName("shop_rating")
    @Expose
    private String shopRating;
    @SerializedName("distance")
    @Expose
    private String distance;

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

    public String getOfferBasepath() {
        return offerBasepath;
    }

    public void setOfferBasepath(String offerBasepath) {
        this.offerBasepath = offerBasepath;
    }

    public String getOfferExpires() {
        return offerExpires;
    }

    public void setOfferExpires(String offerExpires) {
        this.offerExpires = offerExpires;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopRating() {
        return shopRating;
    }

    public void setShopRating(String shopRating) {
        this.shopRating = shopRating;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}