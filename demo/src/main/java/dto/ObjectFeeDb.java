package dto;

import entities.ColFeeDiscount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ObjectFeeDb{
     private Double baseAmount;
     private String channelPaymentCode;
     private Double amount;
     private Double fee;
     private Double discount;

}
