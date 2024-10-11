package com.apec.pos.dto.billDTO;

import com.apec.pos.dto.FoodDto.BillFoodRequest;
import com.apec.pos.enu.OrderStatus;
import com.apec.pos.enu.PaymentStatus;
import com.apec.pos.enu.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillRequest {
    private OrderStatus orderStatus;
    private PaymentStatus paymentStatus;
    private PaymentType paymentType;
    private long totalAmount;
    private int shipFee;
    private String codeVoucher;
    private String note;
    private String sendTo;
    private List<BillFoodRequest> billFoodRequests;

}
