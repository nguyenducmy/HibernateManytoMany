package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ObjectFeeDb {
    private String baseAmount;
    private String channelPaymentCode;
    private String amount;
    private String fee;
    private String discount;

}
