
import dto.ObjectFeeDb;
import org.json.JSONArray;
import service.FeeDiscount;
import service.impl.FeeDiscountImpl;


public class App {
    public static FeeDiscount feeDiscount = new FeeDiscountImpl();


    public static void main(String[] args) {
        // test case
        JSONArray jsonArray =  feeDiscount.getFeeAllChannelPayment("VNPTMONEY", "32", "FT24112001", "50000");
        ObjectFeeDb db = feeDiscount.getFee("VNPTMONEY", "32", "FT24112001", "VNPTPAY", "50000", 0);
        System.out.printf(db.getFee());
    }

}
