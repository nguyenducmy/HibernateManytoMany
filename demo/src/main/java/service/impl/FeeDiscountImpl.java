package service.impl;


import entities.ColFeeDiscount;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.json.JSONArray;
import org.json.JSONObject;
import service.FeeDiscount;
import utils.HibernateUtil;

import java.util.Date;
import java.util.List;

public class FeeDiscountImpl implements FeeDiscount {

    @Override
    public JSONArray getFeeAllChannelPayment(String partnerCode, String serviceCodeCol, String serviceProviderCodeCol, String baseAmount) {
        JSONArray jsonArray = new JSONArray();
        List<ColFeeDiscount> feeDiscounts = getFeeDiscounts(partnerCode, serviceCodeCol, serviceProviderCodeCol);
        feeDiscounts.removeIf(i -> Integer.valueOf(i.getFromValue()) < Integer.valueOf(baseAmount) || Integer.valueOf(i.getFromValue()) > Integer.valueOf(baseAmount)
                || i.getEnable() == 0 || (i.getStartDate().after((new Date())) && i.getEndDate().before(new Date())));
        for (ColFeeDiscount feeDiscount : feeDiscounts) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("baseAmount", baseAmount);
            jsonObject.put("channelPaymentCode", feeDiscount.getChannelPaymentCode());

            if(Integer.valueOf(feeDiscount.getMinValue()) < Integer.valueOf(feeDiscount.getValue()) && Integer.valueOf(feeDiscount.getMaxValue()) > Integer.valueOf(feeDiscount.getValue()) ) {
                jsonObject.put("fee", feeDiscount.getType() == 0 ? feeDiscount.getValue() : 0);
                jsonObject.put("discount", feeDiscount.getType() == 0 ? 0 : feeDiscount.getValue());
                jsonObject.put("amount", feeDiscount.getTypeFormula() == 1 ? Integer.valueOf(baseAmount) + Integer.valueOf(feeDiscount.getValue()) : Integer.valueOf(baseAmount) + Integer.valueOf(baseAmount) * Integer.valueOf(feeDiscount.getValue()) / 100);
            }
            if(Integer.valueOf(feeDiscount.getValue()) == Integer.valueOf(feeDiscount.getMinValue())) {
                jsonObject.put("fee", feeDiscount.getType() == 0 ? feeDiscount.getMinValue() : 0);
                jsonObject.put("discount", feeDiscount.getType() == 0 ? 0 : feeDiscount.getMinValue());
                jsonObject.put("amount", feeDiscount.getTypeFormula() == 1 ? Integer.valueOf(baseAmount) + Integer.valueOf(feeDiscount.getMinValue()) : Integer.valueOf(baseAmount) + Integer.valueOf(baseAmount) * Integer.valueOf(feeDiscount.getMinValue()) / 100);
            }
            if(Integer.valueOf(feeDiscount.getValue()) == Integer.valueOf(feeDiscount.getMaxValue())) {
                jsonObject.put("fee", feeDiscount.getType() == 0 ? feeDiscount.getMaxValue() : 0);
                jsonObject.put("discount", feeDiscount.getType() == 0 ? 0 : feeDiscount.getMaxValue());
                jsonObject.put("amount", feeDiscount.getTypeFormula() == 1 ? Integer.valueOf(baseAmount) + Integer.valueOf(feeDiscount.getMaxValue()) : Integer.valueOf(baseAmount) + Integer.valueOf(baseAmount) * Integer.valueOf(feeDiscount.getMaxValue()) / 100);
            }

            jsonArray.put(jsonObject);
        }
        return jsonArray;
    }
    public  List<ColFeeDiscount>  getFeeDiscounts(String partnerCode, String serviceCodeCol, String serviceProviderCodeCol) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        String sql = "SELECT * FROM SPF_COL_FEE_DISCOUNT_EU where PARTNER_CODE='" + partnerCode + "' AND SEVICE_CODE_COL='" + serviceCodeCol + "' AND SEVICE_PROVIDER_CODE_COL='" + serviceProviderCodeCol + "'";
        NativeQuery query = session.createSQLQuery(sql).addEntity(ColFeeDiscount.class);
        List<ColFeeDiscount> feeDiscounts = query.list();
        tx.commit();
        session.close();
        return feeDiscounts;
    }

}
