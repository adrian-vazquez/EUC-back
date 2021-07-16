package com.citi.euces.pronosticos.infra.dto;

import java.io.Serializable;

import org.springframework.data.domain.Page;

public class ReporteLayoutPrevioDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Page<RepoLayoutPrevioDTO> RepoLayoutPrevioDTO;
    private String file;
    
	public Page<RepoLayoutPrevioDTO> getRepoLayoutPrevioDTO() {
		return RepoLayoutPrevioDTO;
	}
	public void setRepoLayoutPrevioDTO(Page<RepoLayoutPrevioDTO> repoLayoutPrevioDTO) {
		RepoLayoutPrevioDTO = repoLayoutPrevioDTO;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
    
    

}