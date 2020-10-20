/**
 *
 */
package com.citi.euces.pronosticos.models;

import java.io.Serializable;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author lbermejo
 *
 */
public class CustomerDetailRequest implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "Please provide a tdcCustomer")
	@NotNull(message = "Please provide a tdcCustomer")
	@Digits( integer = 16, fraction = 0, message = "Only 16 digits")
	private Long tdcCustomer;

	/**
	 * @return the tdcCustomer
	 */
	public Long getTdcCustomer() {
		return tdcCustomer;
	}

	/**
	 * @param tdcCustomer the tdcCustomer to set
	 */
	public void setTdcCustomer(Long tdcCustomer) {
		this.tdcCustomer = tdcCustomer;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(
				this, ToStringStyle.JSON_STYLE  );
	}
	
}
