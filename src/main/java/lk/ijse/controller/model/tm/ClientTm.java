package lk.ijse.controller.model.tm;


import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class ClientTm {
    private String id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String checkIn;
    private String checkOut;
}
