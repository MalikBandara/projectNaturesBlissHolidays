package lk.ijse.controller.model.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class RoomTm {

    private String roomId;
    private String roomNumber;
    private String type;
    private String rate;
    private String status;
}
