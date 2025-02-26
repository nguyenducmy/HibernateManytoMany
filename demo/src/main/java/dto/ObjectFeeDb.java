package dto;

import lombok.Data;

@Data
public class ObjectFeeDb {
    private String baseAmount;
    private String channelPaymentCode;
    private String amount;
    private String fee;
    private String discount;

}
