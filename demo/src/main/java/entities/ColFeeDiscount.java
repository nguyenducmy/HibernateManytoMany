package entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "SPF_COL_FEE_DISCOUNT_EU")
@Getter @Setter
public class ColFeeDiscount implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", columnDefinition = "int", nullable = false)
    private int id;

    @Column(name = "SEVICE_CODE_COL")
    private String serviceCodeCol;

    @Column(name = "SEVICE_PROVIDER_CODE_COL")
    private String serviceProviderCodeCol;

    @Column(name = "CHANNEL_PAYMENT_CODE")
    private String channelPaymentCode;

    @Column(name = "PARTNER_CODE")
    private String partnerCode;

    @Column(name = "NAME")
    private String name;

    @Column(name = "TYPE")
    private int type;

    @Column(name = "TYPE_FORMULA")
    private int typeFormula;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "EXTEND")
    private String extend;

    @Column(name = "ADD_INFO")
    private String addInfo;

    @Column(name = "FORMULA")
    private String formula;

    @Column(name = "START_DATE")
    private Date startDate;

    @Column(name = "END_DATE")
    private Date endDate;

    @Column(name = "ENABLE")
    private int enable;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "VALUE")
    private String value;

    @Column(name = "FROM_VALUE")
    private String fromValue;

    @Column(name = "TO_VALUE")
    private String toValue;

    @Column(name = "MIN_VALUE")
    private String minValue;

    @Column(name = "MAX_VALUE")
    private String maxValue;


}
