package htms.api.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassRequest {
    /*
    {
  "departmentId": "ef0aee96-d60f-422c-9418-0487d35e530e",
  "name": "Lớp test",
  "generalSchedule": "Start{10:00,MON};Stop{11:00,MON};Start{17:00,MON};Stop{19:00,MON}",
  "programId": "c2c6401d-b0ab-42b8-af7d-980260fcd963",
  "cycleId": "a64edcb5-a30b-47c5-a053-332cd2a10f6b",
  "minQuantity": 4,
  "maxQuantity": 30,
  "startDate": "2023-06-12T05:52:50.728Z",
  "endDate": "2023-12-12T05:52:50.728Z"
}
     */
    private String name;
    private Short minQuantity;
    private Short maxQuantity;
}
