package lk.ijse.controller.model.tm;

import lombok.*;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class BookingTm {
    private String bookingId;
    private String packageId;
    private String identityDetails;
    private String bookingDate;
    private String payId;
    private String roomId;

}
