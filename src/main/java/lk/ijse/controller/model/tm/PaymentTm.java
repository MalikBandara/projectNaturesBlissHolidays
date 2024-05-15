package lk.ijse.controller.model.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode

public class PaymentTm {
    private String payId;
    private String amount;
    private String method;
    private String paidDate;
    private String status;


}
