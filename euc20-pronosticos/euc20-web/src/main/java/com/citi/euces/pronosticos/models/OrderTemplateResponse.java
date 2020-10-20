/**
 *
 */
package com.citi.euces.pronosticos.models;

/**
 * @author lbermejo
 *
 * Domain
 */
public class OrderTemplateResponse {

	private String orderCode;

	public OrderTemplateResponse(final String code) {
		this.orderCode = code;
	}

	public final String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(final String orderCode) {
		this.orderCode = orderCode;
	}

	@Override public String toString() {
	    return "Order(" + this.getOrderCode() + ")";
	}

}
