package com.quickmas.salim.quickmasretail.Model.RTM;

/**
 * Created by Forhad on 01/11/2018.
 */

public class RtmAssignmentList {
    public String asignNo;
    public String dateFrom;
    public String dateTo;
    public String quantity;
    public String status;

    public void setAsignNo(String asignNo) {
        this.asignNo = asignNo;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAsignNo() {
        return asignNo;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getStatus() {
        return status;
    }
}
