package com.apec.pos.dto.billDTO;

import com.apec.pos.entity.BillEntity;
import com.apec.pos.entity.VoucherEntity;

import java.util.List;

public class AddBillResponse {
    private BillEntity bill;
    private List<VoucherEntity> vouchers;

    // Constructor
    public AddBillResponse(BillEntity bill, List<VoucherEntity> vouchers) {
        this.bill = bill;
        this.vouchers = vouchers;
    }

    // Getters và Setters
    public BillEntity getBill() {
        return bill;
    }

    public void setBill(BillEntity bill) {
        this.bill = bill;
    }

    public List<VoucherEntity> getVouchers() {
        return vouchers;
    }

    public void setVouchers(List<VoucherEntity> vouchers) {
        this.vouchers = vouchers;
    }
}