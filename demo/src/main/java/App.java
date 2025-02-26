


import entities.ColFeeDiscount;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import utils.HibernateUtil;

import java.util.List;

public class App {


    public static void main(String[] args) {
        testCompositeKey();
    }

    public static void testCompositeKey(){
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {

            Transaction tx = session.beginTransaction();

            String partnerCode =  "VNPTMONEY";
            String serviceCodeCol = "32";
            String serviceProviderCodeCol = "FT24112001";

            String sql = "SELECT * FROM SPF_COL_FEE_DISCOUNT_EU where PARTNER_CODE='" + partnerCode + "' AND SEVICE_CODE_COL='" + serviceCodeCol + "' AND SEVICE_PROVIDER_CODE_COL='" + serviceProviderCodeCol + "'";
            NativeQuery query = session.createSQLQuery(sql).addEntity(ColFeeDiscount.class);
            List<ColFeeDiscount> feeDiscounts = query.list();
            System.out.printf("feeDiscounts size: %d\n", feeDiscounts.size());

            tx.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }



    }
}
