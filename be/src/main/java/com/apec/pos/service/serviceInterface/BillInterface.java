package com.apec.pos.service.serviceInterface;

import com.apec.pos.dto.billDTO.AddBillResponse;
import com.apec.pos.dto.billDTO.BillRequest;
import com.apec.pos.dto.billDTO.BillResponse;
import com.apec.pos.dto.billDTO.BillResponsePage;
import com.apec.pos.entity.BillEntity;
import com.apec.pos.enu.OrderStatus;
import com.apec.pos.enu.PaymentStatus;

public interface BillInterface {
    public AddBillResponse addBill(BillRequest billRequest, String username) throws Exception;

    public BillEntity updateBill(OrderStatus orderStatus, Integer id);

    public BillResponsePage getBill(int pageIndex, int pageSize, OrderStatus orderStatus);

    public BillResponsePage getBillUser(int pageIndex, int pageSize, OrderStatus orderStatus,String username);

    public BillResponse getDetailBill(Integer id);

    public void cancelBill(Integer id,String username);

    public void updateBillOnline(PaymentStatus paymentStatus, Integer id);
}
