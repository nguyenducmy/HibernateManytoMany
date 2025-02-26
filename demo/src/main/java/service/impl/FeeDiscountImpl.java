package service.impl;


import dto.ObjectFeeDb;
import entities.ColFeeDiscount;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import service.FeeDiscount;
import utils.HibernateUtil;

import java.util.Date;
import java.util.List;

@Service
public class FeeDiscountImpl implements FeeDiscount {

    @Override
    public  JSONArray getFeeAllChannelPayment(String partnerCode, String serviceCodeCol, String serviceProviderCodeCol, String baseAmount) {
        JSONArray jsonArray = new JSONArray();
        String sql = "SELECT * FROM SPF_COL_FEE_DISCOUNT_EU where PARTNER_CODE='" + partnerCode + "' AND SEVICE_CODE_COL='" + serviceCodeCol + "' AND SEVICE_PROVIDER_CODE_COL='" + serviceProviderCodeCol + "'";
        List<ColFeeDiscount> feeDiscounts = getFeeDiscounts(partnerCode, serviceCodeCol, serviceProviderCodeCol, sql);

        for (ColFeeDiscount feeDiscount : feeDiscounts) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("baseAmount", baseAmount);
            jsonObject.put("channelPaymentCode", feeDiscount.getChannelPaymentCode());
            if (checkCondition(feeDiscount, baseAmount)) {
                String feeDiscountVal = checkFeeDiscountVal(feeDiscount, baseAmount);
                jsonObject.put("fee", feeDiscount.getType() == 0 ? feeDiscountVal : 0);
                jsonObject.put("discount", feeDiscount.getType() == 0 ? 0 : feeDiscountVal);
                jsonObject.put("amount", feeDiscount.getTypeFormula() == 1 ? Double.valueOf(baseAmount) + Double.valueOf(feeDiscountVal) : Double.valueOf(baseAmount) + Double.valueOf(baseAmount) * Double.valueOf(feeDiscountVal) / 100);

                jsonArray.put(jsonObject);
            }
        }
        return jsonArray;
    }

    @Override
    public ObjectFeeDb getFee(String partnerCode, String serviceCodeCol, String serviceProviderCodeCol, String channelPaymentCode, String baseAmount, int type) {
        String sql = "SELECT * FROM SPF_COL_FEE_DISCOUNT_EU where PARTNER_CODE='" + partnerCode + "' AND SEVICE_CODE_COL='" + serviceCodeCol + "' AND SEVICE_PROVIDER_CODE_COL='" + serviceProviderCodeCol + "' and CHANNEL_PAYMENT_CODE='" + channelPaymentCode + "' AND TYPE='" + type + "'";
        List<ColFeeDiscount> feeDiscounts = getFeeDiscounts(partnerCode, serviceCodeCol, serviceProviderCodeCol, sql);
        ObjectFeeDb objectFeeDb = new ObjectFeeDb();
        Double feeDiscountTotal = 0.0;
        Double amountTotal = 0.0;
        for (ColFeeDiscount feeDiscount : feeDiscounts) {
            String feeDiscountVal = "";
            if (checkCondition(feeDiscount, baseAmount)) {
                feeDiscountVal = checkFeeDiscountVal(feeDiscount, baseAmount);
            }
            feeDiscountTotal = Double.valueOf(feeDiscountVal.isEmpty() ? "0.0" : feeDiscountVal) + feeDiscountTotal;
//            amountTotal = amountTotal+  feeDiscount.getTypeFormula() == 1 ? Double.valueOf(baseAmount) + Double.valueOf(feeDiscountVal.isEmpty() ? "0.0" : feeDiscountVal) : Double.valueOf(baseAmount) + Double.valueOf(baseAmount) * Double.valueOf(feeDiscountVal.isEmpty() ? "0.0" : feeDiscountVal) / 100;
        }
        objectFeeDb.setBaseAmount(baseAmount);
        objectFeeDb.setChannelPaymentCode(channelPaymentCode);
//        objectFeeDb.setAmount(String.valueOf(amountTotal+Double.valueOf(baseAmount)));
        objectFeeDb.setFee(type==0?String.valueOf(feeDiscountTotal):"0.0");

        objectFeeDb.setDiscount(type == 0 ?"0.0":String.valueOf(feeDiscountTotal));
        return objectFeeDb;
    }

    public static List<ColFeeDiscount> getFeeDiscounts(String partnerCode, String serviceCodeCol, String serviceProviderCodeCol, String sql) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        NativeQuery query = session.createSQLQuery(sql).addEntity(ColFeeDiscount.class);
        List<ColFeeDiscount> feeDiscounts = query.list();
        tx.commit();
        session.close();
        return feeDiscounts;
    }

    public static boolean checkCondition(ColFeeDiscount feeDiscount, String baseAmount) {
        if (feeDiscount.getFromValue() == null && feeDiscount.getToValue() != null && Double.valueOf(feeDiscount.getToValue()) <= Double.valueOf(baseAmount)) {
            return false;
        }
        if (feeDiscount.getToValue() == null && feeDiscount.getFromValue() != null && Double.valueOf(feeDiscount.getFromValue()) >= Double.valueOf(baseAmount)) {
            return false;
        }
        if (feeDiscount.getMinValue() == null && feeDiscount.getMinValue() != null && Double.valueOf(feeDiscount.getMinValue()) <= Double.valueOf(feeDiscount.getValue())) {
            return false;
        }
        if (feeDiscount.getMaxValue() == null && feeDiscount.getMaxValue() != null && Double.valueOf(feeDiscount.getMaxValue()) >= Double.valueOf(feeDiscount.getValue())) {
            return false;
        }

        if (feeDiscount.getEnable() == 0) {
            return false;
        }
        if (feeDiscount.getStartDate().after(new Date())) {
            return false;
        }
        if (feeDiscount.getEndDate().before(new Date())) {
            return false;
        }
        return true;
    }

    public static String checkFeeDiscountVal(ColFeeDiscount feeDiscount, String baseAmount) {
        String feeDiscountVal = "";
        if ((feeDiscount.getFromValue() == null && feeDiscount.getToValue() == null) || feeDiscount.getFromValue() != null && feeDiscount.getToValue() != null && Integer.valueOf(feeDiscount.getFromValue()) < Integer.valueOf(baseAmount) && Integer.valueOf(feeDiscount.getToValue()) > Integer.valueOf(baseAmount)) {
            if (Double.valueOf(feeDiscount.getValue()) < Double.valueOf(feeDiscount.getMinValue())) {
                feeDiscountVal = feeDiscount.getMinValue();
            }
            if (Double.valueOf(feeDiscount.getValue()) > Double.valueOf(feeDiscount.getMaxValue())) {
                feeDiscountVal = feeDiscount.getMaxValue();
            }
            if (Double.valueOf(feeDiscount.getMinValue()) <= Double.valueOf(feeDiscount.getValue()) && Double.valueOf(feeDiscount.getMaxValue()) >= Double.valueOf(feeDiscount.getValue())) {
                feeDiscountVal = feeDiscount.getValue();
            }
        }
        return feeDiscountVal;
    }
}
