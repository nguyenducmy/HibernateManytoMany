import dto.ObjectFeeDb;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import service.FeeDiscount;
import service.impl.FeeDiscountImpl;

@NoArgsConstructor
public class Test {

    FeeDiscount feeDiscount = new FeeDiscountImpl();


    @org.junit.Test
    public void testgetFeeAllChannelPayment(){
        JSONArray actual =  feeDiscount.getFeeAllChannelPayment("VNPTMONEY", "32", "FT24112001", "50000");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("amount", 51000);
        jsonObject.put("fee", "1000");
        jsonObject.put("discount", 0);
        jsonObject.put("channelPaymentCode", "VNPTPAY");
        jsonObject.put("baseAmount", "50000");
        JSONArray expected = new JSONArray().put(jsonObject);
        Assert.assertEquals(expected.toString(), actual.toString());

//        System.out.printf(actual.toString());
//        ObjectFeeDb db = feeDiscount.getFee("VNPTMONEY", "32", "FT24112001", "VNPTPAY", "50000", 0);
//        System.out.printf(db.getFee());
    }
    @org.junit.Test
    public void testgetFee(){
        ObjectFeeDb actual =  feeDiscount.getFee("VNPTMONEY", "32", "FT24112001", "VNPTPAY", "50000", 0);
        ObjectFeeDb expected =  ObjectFeeDb.builder()
                .baseAmount(50000.0)
                .amount(51000.0).fee(1000.0).discount(0.0).channelPaymentCode("VNPTPAY").build();
        Assert.assertEquals(expected, actual);

    }
}
