package com.zixue.adapt;

import com.zixue.entity.PaymentInfo;
import com.zixue.entity.PaymentType;

public interface PayAdaptService {
	public String pay(PaymentInfo paymentInfo, PaymentType paymentType );
}
