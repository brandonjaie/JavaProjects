/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.campkobold.dto;

import java.util.Date;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author Brandon
 */
public class AssetRecord {

    private int recordId;
    private Asset asset;
    private UserUserProfile employee;
    private UserUserProfile member;
    private boolean available;

    @Valid
    @NotNull
    private Status status;
    @Length(max = 35, message = "Note must be less than 35 characters")
    private String assetNote;
    private Date recordDate;

    public AssetRecord(int recordId, Asset asset, UserUserProfile employee, UserUserProfile member, Status status, String assetNote, Date recordDate) {
        this.recordId = recordId;
        this.asset = asset;
        this.employee = employee;
        this.member = member;
        this.status = status;
        this.assetNote = assetNote;
        this.recordDate = recordDate;

    }

    public AssetRecord() {

    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public UserUserProfile getEmployee() {
        return employee;
    }

    public void setEmployee(UserUserProfile employee) {
        this.employee = employee;
    }

    public UserUserProfile getMember() {
        return member;
    }

    public void setMember(UserUserProfile member) {
        this.member = member;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getAssetNote() {
        return assetNote;
    }

    public void setAssetNote(String assetNote) {
        this.assetNote = assetNote;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + this.recordId;
        hash = 17 * hash + Objects.hashCode(this.asset);
        hash = 17 * hash + Objects.hashCode(this.employee);
        hash = 17 * hash + Objects.hashCode(this.member);
        hash = 17 * hash + Objects.hashCode(this.status);
        hash = 17 * hash + Objects.hashCode(this.assetNote);
        hash = 17 * hash + Objects.hashCode(this.recordDate);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AssetRecord other = (AssetRecord) obj;
        if (this.recordId != other.recordId) {
            return false;
        }
        if (!Objects.equals(this.assetNote, other.assetNote)) {
            return false;
        }
        if (!Objects.equals(this.asset, other.asset)) {
            return false;
        }
        if (!Objects.equals(this.employee, other.employee)) {
            return false;
        }
        if (!Objects.equals(this.member, other.member)) {
            return false;
        }
        if (!Objects.equals(this.status, other.status)) {
            return false;
        }
        if (!Objects.equals(this.recordDate, other.recordDate)) {
            return false;
        }
        return true;
    }

}
