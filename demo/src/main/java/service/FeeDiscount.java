package service;

import org.json.JSONArray;

public interface FeeDiscount {
    public JSONArray getFeeAllChannelPayment(String partnerCode, String serviceCodeCol, String serviceProviderCodeCol, String baseAmount);

}
