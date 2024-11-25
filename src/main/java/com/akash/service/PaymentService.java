package com.akash.service;

import com.akash.model.Order;
import com.akash.response.PaymentResponse;
import com.stripe.exception.StripeException;

public interface PaymentService  {
	
	public PaymentResponse createPaymentLink(Order order) throws StripeException;
}
