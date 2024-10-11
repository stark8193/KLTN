package com.apec.pos.dto.paymentDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequestDTO {
    private long amount;
    private String orderCode;
}
