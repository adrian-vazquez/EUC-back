/**
 *
 */
package com.citi.euces.pronosticos.models;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author lbermejo
 *
 */
public class CustomerDetailResponse implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private static final AtomicLong counter = new AtomicLong();

	private Long tarjetaDeCredito = 4053060000150781l;
	private String promocion = "TRANSFERENCIA DE DEUDA";
	private String nombre = "ANA ERIKA MARTINEZ AVITIA "+ counter.incrementAndGet() ;
	private String producto = "Affinity Card Citibanamex";
	private Long lineaInvitada = 107000l;
	private Integer mensualidades = 0;
	private Double tasaIntAn = 9.99d;
	private Double catSinIva = 10.5d;
	private String vigencia = "10 de Febrero al 18 de abril";
	private String promoId = "LITERAL BPM V6A";
	//private Date date;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(
				this, ToStringStyle.JSON_STYLE  );
	}

	/**
	 * @return the tarjetaDeCredito
	 */
	public Long getTarjetaDeCredito() {
		return tarjetaDeCredito;
	}
	/**
	 * @param tarjetaDeCredito the tarjetaDeCredito to set
	 */
	public void setTarjetaDeCredito(Long tarjetaDeCredito) {
		this.tarjetaDeCredito = tarjetaDeCredito;
	}
	/**
	 * @return the promocion
	 */
	public String getPromocion() {
		return promocion;
	}
	/**
	 * @param promocion the promocion to set
	 */
	public void setPromocion(String promocion) {
		this.promocion = promocion;
	}
	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * @return the producto
	 */
	public String getProducto() {
		return producto;
	}
	/**
	 * @param producto the producto to set
	 */
	public void setProducto(String producto) {
		this.producto = producto;
	}
	/**
	 * @return the lineaInvitada
	 */
	public Long getLineaInvitada() {
		return lineaInvitada;
	}
	/**
	 * @param lineaInvitada the lineaInvitada to set
	 */
	public void setLineaInvitada(Long lineaInvitada) {
		this.lineaInvitada = lineaInvitada;
	}
	/**
	 * @return the mensualidades
	 */
	public Integer getMensualidades() {
		return mensualidades;
	}
	/**
	 * @param mensualidades the mensualidades to set
	 */
	public void setMensualidades(Integer mensualidades) {
		this.mensualidades = mensualidades;
	}
	/**
	 * @return the tasaIntAn
	 */
	public Double getTasaIntAn() {
		return tasaIntAn;
	}
	/**
	 * @param tasaIntAn the tasaIntAn to set
	 */
	public void setTasaIntAn(Double tasaIntAn) {
		this.tasaIntAn = tasaIntAn;
	}
	/**
	 * @return the catSinIva
	 */
	public Double getCatSinIva() {
		return catSinIva;
	}
	/**
	 * @param catSinIva the catSinIva to set
	 */
	public void setCatSinIva(Double catSinIva) {
		this.catSinIva = catSinIva;
	}
	/**
	 * @return the vigencia
	 */
	public String getVigencia() {
		return vigencia;
	}
	/**
	 * @param vigencia the vigencia to set
	 */
	public void setVigencia(String vigencia) {
		this.vigencia = vigencia;
	}
	/**
	 * @return the promoId
	 */
	public String getPromoId() {
		return promoId;
	}
	/**
	 * @param promoId the promoId to set
	 */
	public void setPromoId(String promoId) {
		this.promoId = promoId;
	}
	/**
	 * @return the date
	 */
	public Date getDate() {
		return Calendar.getInstance().getTime();
	}
	/**
	 * @param date the date to set
	 * /
	public void setDate(Date date) {
		this.date = date;
	}*/
	/**
	 * @return the counter
	 */
	public static AtomicLong getCounter() {
		return counter;
	}

}
