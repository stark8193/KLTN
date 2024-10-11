package com.apec.pos.controller;

import com.apec.pos.config.Config;
import com.apec.pos.dto.paymentDTO.PaymentRequestDTO;
import com.apec.pos.dto.paymentDTO.PaymentResDTO;
import com.apec.pos.dto.paymentDTO.TransactionStatusDTO;
import com.apec.pos.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.apec.pos.config.Config.vnp_Command;
import static com.apec.pos.config.Config.vnp_Version;
import static com.apec.pos.enu.PaymentStatus.PAID;
import static com.apec.pos.enu.PaymentStatus.UNPAID;

@RestController
@RequestMapping("/pay")
public class PaymentController {
    @Autowired
    private BillService billService;
    @PostMapping("/create_payment")
    public ResponseEntity<?> createPayment(@RequestBody PaymentRequestDTO paymentRequestDTO) throws UnsupportedEncodingException {

        long amount = paymentRequestDTO.getAmount() * 100;

        String vnp_TxnRef = Config.getRandomNumber(8);
        String vnp_TmnCode = Config.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount)); //tiền thanh toán
        vnp_Params.put("vnp_BankCode", "NCB");
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_TxnRef", paymentRequestDTO.getOrderCode()); //mã đơn hàng
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + paymentRequestDTO.getOrderCode()); //mã đơn hàng
        vnp_Params.put("vnp_OrderType","product");
        vnp_Params.put("vnp_ReturnUrl", Config.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", "192.168.1.1");

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 60*12);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (!fieldValue.isEmpty())) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = Config.hmacSHA512(Config.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;

        PaymentResDTO paymentResDTO = new PaymentResDTO();
        paymentResDTO.setStatus("OK");
        paymentResDTO.setMessage("Successful");
        paymentResDTO.setURL(paymentUrl);
        return ResponseEntity.status(HttpStatus.OK).body(paymentResDTO);
    }

    @GetMapping("/payment_info")
    public ResponseEntity<?> transaction(
            @RequestParam(value = "vnp_Amount") String amount,
            @RequestParam(value = "vnp_BankCode") String bankCode,
            @RequestParam(value = "vnp_OrderInfo") String orderInfo,
            @RequestParam(value = "vnp_ResponseCode") String responseCode
    ) throws UnsupportedEncodingException {
        TransactionStatusDTO transactionStatusDTO = new TransactionStatusDTO();
        if (responseCode.equals("00")){
            transactionStatusDTO.setStatus("OK");
            transactionStatusDTO.setMessage("Successful");
            transactionStatusDTO.setData("Da thanh toan thanh cong");
            System.out.println("orderInfo:::::::  "+ orderInfo);
            //substring(7)
            billService.updateBillOnline(PAID, Integer.valueOf(orderInfo.substring(20)));
        }
        else {
            transactionStatusDTO.setStatus("No");
            transactionStatusDTO.setMessage("Failed");
            transactionStatusDTO.setData("Thanh toan that bai");
            billService.updateBillOnline(UNPAID, Integer.valueOf(orderInfo.substring(20)));
        }
        return ResponseEntity.status(HttpStatus.OK).body(transactionStatusDTO);
    }

}
