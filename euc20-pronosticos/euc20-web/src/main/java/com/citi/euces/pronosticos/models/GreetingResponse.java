/**
 * 
 */
package com.citi.euces.pronosticos.models;

/**
 * @author lbermejo
 *
 */
public class GreetingResponse {
	
	private final Long id;
	private final String content;
	
	/**
	 * @param id
	 * @param content
	 */
	public GreetingResponse(Long id, String content) {
		this.id = id;
		this.content = content;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	
	@Override
	public String toString() {
		return "Greeting{ id:" + this.getId() 
						+", content:" 
				+ this.getContent() + "}";
	}
	
}
