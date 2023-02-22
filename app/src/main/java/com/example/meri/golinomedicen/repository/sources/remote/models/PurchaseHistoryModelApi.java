package com.example.meri.golinomedicen.repository.sources.remote.models;

import com.google.gson.annotations.SerializedName;

public class PurchaseHistoryModelApi {

    @SerializedName("id")
    private int id;
    @SerializedName("time")
    private String time;
    @SerializedName("date")
    private String date;
    @SerializedName("pahrmacyId")
    private int pharmacyId;
    @SerializedName("price")
    private int price;
    @SerializedName("refID")
    private int refId;
    @SerializedName("discount")
    private int discount;
    @SerializedName("lastPrice")
    private int lastPrice;
    @SerializedName("isPay")
    private boolean isPay;
    @SerializedName("isFinish")
    private boolean isFinish;
    @SerializedName("isCancel")
    private boolean isCancel;
    @SerializedName("isSend")
    private boolean isSend;
    @SerializedName("isAccept")
    private boolean isAccept;
    @SerializedName("pharmacy")
    private Pharmacy pharmacy;
    @SerializedName("medicine")
    private Medicine prescriptionDrug;

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(final String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(final String date) {
        this.date = date;
    }

    public int getPharmacyId() {
        return pharmacyId;
    }

    public void setPharmacyId(final int pharmacyId) {
        this.pharmacyId = pharmacyId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(final int price) {
        this.price = price;
    }

    public int getRefId() {
        return refId;
    }

    public void setRefId(final int refId) {
        this.refId = refId;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(final int discount) {
        this.discount = discount;
    }

    public int getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(final int lastPrice) {
        this.lastPrice = lastPrice;
    }

    public boolean isPay() {
        return isPay;
    }

    public void setPay(final boolean pay) {
        isPay = pay;
    }

    public boolean isFinish() {
        return isFinish;
    }

    public void setFinish(final boolean finish) {
        isFinish = finish;
    }

    public boolean isCancel() {
        return isCancel;
    }

    public void setCancel(final boolean cancel) {
        isCancel = cancel;
    }

    public boolean isSend() {
        return isSend;
    }

    public void setSend(final boolean send) {
        isSend = send;
    }

    public boolean isAccept() {
        return isAccept;
    }

    public void setAccept(final boolean accept) {
        isAccept = accept;
    }

    public Pharmacy getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(final Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
    }

    public Medicine getPrescriptionDrug() {
        return prescriptionDrug;
    }

    public void setPrescriptionDrug(final Medicine prescriptionDrug) {
        this.prescriptionDrug = prescriptionDrug;
    }

    @Override
    public String toString() {
        return "PurchaseHistoryModelApi{" + "id=" + id + ", time='" + time + '\'' + ", date='" + date +
                '\'' + ", pharmacyId=" + pharmacyId + ", price=" + price + ", refId=" + refId + ", " +
                "discount=" + discount + ", lastPrice=" + lastPrice + ", isPay=" + isPay + ", isFinish=" +
                isFinish + ", isCancel=" + isCancel + ", isSend=" + isSend + ", isAccept=" + isAccept + ", " +
                "" + "pharmacy=" + pharmacy + ", prescriptionDrug=" + prescriptionDrug + '}';
    }

    public static class Pharmacy {
        @SerializedName("pharmacyName")
        private String pharmacyName;
        @SerializedName("phone")
        private int phone;
        @SerializedName("address")
        private String address;
        @SerializedName("longitude")
        private int longitude;
        @SerializedName("latitude")
        private int latitude;


        public String getPharmacyName() {
            return pharmacyName;
        }

        public void setPharmacyName(final String pharmacyName) {
            this.pharmacyName = pharmacyName;
        }

        public int getPhone() {
            return phone;
        }

        public void setPhone(final int phone) {
            this.phone = phone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(final String address) {
            this.address = address;
        }

        public int getLongitude() {
            return longitude;
        }

        public void setLongitude(final int longitude) {
            this.longitude = longitude;
        }

        public int getLatitude() {
            return latitude;
        }

        public void setLatitude(final int latitude) {
            this.latitude = latitude;
        }

        @Override
        public String toString() {
            return "Pharmacy{" + "pharmacyName='" + pharmacyName + '\'' + ", phone=" + phone + ", " +
                    "address='" + address + '\'' + ", longitude=" + longitude + ", latitude=" + latitude + '}';
        }
    }

    public static class Medicine {

        @SerializedName("image")
        private String image;

        public String getImage() {
            return image;
        }

        public void setImage(final String image) {
            this.image = image;
        }

        @Override
        public String toString() {
            return "Medicine{" + "image='" + image + '\'' + '}';
        }
    }


}
