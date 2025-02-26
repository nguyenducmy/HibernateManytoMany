package service;

import dto.ObjectFeeDb;
import org.json.JSONArray;
import org.springframework.stereotype.Service;

@Service
public interface FeeDiscount {
    public JSONArray getFeeAllChannelPayment(String partnerCode, String serviceCodeCol, String serviceProviderCodeCol, String baseAmount);
    public ObjectFeeDb getFee(String partnerCode, String serviceCodeCol, String serviceProviderCodeCol, String channelPaymentCode, String baseAmount, int type);
}
