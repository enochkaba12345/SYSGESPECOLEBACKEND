package com.sysgepecole.demo.Dto;

public class EcoleDto {

	    private String ecole;
	    private String avenue;
	    private Long idecole;
	    private Long idcommune;
	    public String getAvenue() {
			return avenue;
		}
		public void setAvenue(String avenue) {
			this.avenue = avenue;
		}
		public Long getIdecole() {
			return idecole;
		}
		public void setIdecole(Long idecole) {
			this.idecole = idecole;
		}
		public Long getIdcommune() {
			return idcommune;
		}
		public void setIdcommune(Long idcommune) {
			this.idcommune = idcommune;
		}
		public Long getIdprovince() {
			return idprovince;
		}
		public void setIdprovince(Long idprovince) {
			this.idprovince = idprovince;
		}
		private Long idprovince;
		private String province;
		private String annee;
		private String commune;
	    public String getCommune() {
			return commune;
		}
		public void setCommune(String commune) {
			this.commune = commune;
		}
		public String getAnnee() {
			return annee;
		}
		public void setAnnee(String annee) {
			this.annee = annee;
		}
		public String getProvince() {
			return province;
		}
		public void setProvince(String province) {
			this.province = province;
		}
		private AnneeDto anneeDto;
		public String getEcole() {
			return ecole;
		}
		public void setEcole(String ecole) {
			this.ecole = ecole;
		}
		public AnneeDto getAnneeDto() {
			return anneeDto;
		}
		public void setAnneeDto(AnneeDto anneeDto) {
			this.anneeDto = anneeDto;
		}
	
	    
	    
}
