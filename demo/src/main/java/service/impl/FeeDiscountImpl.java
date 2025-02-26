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
    public static void main(String[] args) {
        JSONArray jsonArray = getFeeAllChannelPayment("VNPTMONEY", "32", "FT24112001", "50000");
        System.out.printf("jsonArray = %s\n", jsonArray);
    }

//    @Override
    public static JSONArray getFeeAllChannelPayment(String partnerCode, String serviceCodeCol, String serviceProviderCodeCol, String baseAmount) {
        JSONArray jsonArray = new JSONArray();
        List<ColFeeDiscount> feeDiscounts = getFeeDiscounts(partnerCode, serviceCodeCol, serviceProviderCodeCol);

        for (ColFeeDiscount feeDiscount : feeDiscounts) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("baseAmount", baseAmount);
            jsonObject.put("channelPaymentCode", feeDiscount.getChannelPaymentCode());



            if(feeDiscount.getFromValue() == null && feeDiscount.getToValue() != null && Double.valueOf(feeDiscount.getToValue()) <= Double.valueOf(baseAmount)) {
                continue;
            }
            if(feeDiscount.getToValue() == null && feeDiscount.getFromValue() !=null  && Double.valueOf(feeDiscount.getFromValue()) >= Double.valueOf(baseAmount)) {
                continue;
            }
            if(feeDiscount.getMinValue() == null && feeDiscount.getMinValue() != null && Double.valueOf(feeDiscount.getMinValue()) <= Double.valueOf(feeDiscount.getValue())) {
                continue;
            }
            if(feeDiscount.getMaxValue() == null && feeDiscount.getMaxValue() != null && Double.valueOf(feeDiscount.getMaxValue()) >= Double.valueOf(feeDiscount.getValue())) {
                continue;
            }

            if(feeDiscount.getEnable() == 0){
                continue;
            }
            if(feeDiscount.getStartDate().after(new Date())) {
                continue;
            }
            if(feeDiscount.getEndDate().before(new Date())) {
                continue;
            }

            if(  (feeDiscount.getFromValue() == null && feeDiscount.getToValue() == null) || feeDiscount.getFromValue() != null && feeDiscount.getToValue() != null && Integer.valueOf(feeDiscount.getFromValue()) < Integer.valueOf(baseAmount) && Integer.valueOf(feeDiscount.getToValue()) > Integer.valueOf(baseAmount)) {
                String feeDiscountVal = "";

                if(Double.valueOf(feeDiscount.getValue()) < Double.valueOf(feeDiscount.getMinValue())){
                        feeDiscountVal = feeDiscount.getMinValue();

                }
                if(Double.valueOf(feeDiscount.getValue()) > Double.valueOf(feeDiscount.getMaxValue())){
                    feeDiscountVal = feeDiscount.getMaxValue();

                }
                if(Double.valueOf(feeDiscount.getMinValue()) <= Double.valueOf(feeDiscount.getValue()) && Double.valueOf(feeDiscount.getMaxValue()) >= Double.valueOf(feeDiscount.getValue())){
                    feeDiscountVal = feeDiscount.getValue();
                }

                jsonObject.put("fee", feeDiscount.getType() == 0 ? feeDiscountVal : 0);
                jsonObject.put("discount", feeDiscount.getType() == 0 ? 0 : feeDiscountVal);
                jsonObject.put("amount", feeDiscount.getTypeFormula() == 1 ? Double.valueOf(baseAmount) + Double.valueOf(feeDiscountVal) : Double.valueOf(baseAmount) + Double.valueOf(baseAmount) * Double.valueOf(feeDiscountVal) / 100);
                jsonArray.put(jsonObject);
            }

        }
        return jsonArray;
    }
    public static List<ColFeeDiscount>  getFeeDiscounts(String partnerCode, String serviceCodeCol, String serviceProviderCodeCol) {
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
