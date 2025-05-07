package com.sysgepecole.demo.Dto;

public class LogosModelDto {
    private Long idecole;
    private String ecole;
    private Long id;
    private Long idcommune;
    private String commune;
    private String avenue;
    private String province;
    private String logos; 

   
    public LogosModelDto() {}

    public LogosModelDto(Long idecole, String ecole, Long id, Long idcommune, String commune, String avenue, String province, String logos) {
        this.idecole = idecole;
        this.ecole = ecole;
        this.id = id;
        this.idcommune = idcommune;
        this.commune = commune;
        this.avenue = avenue;
        this.province = province;
        this.logos = logos;
    }


    public Long getIdecole() { return idecole; }
    public void setIdecole(Long idecole) { this.idecole = idecole; }

    public String getEcole() { return ecole; }
    public void setEcole(String ecole) { this.ecole = ecole; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getIdcommune() { return idcommune; }
    public void setIdcommune(Long idcommune) { this.idcommune = idcommune; }

    public String getCommune() { return commune; }
    public void setCommune(String commune) { this.commune = commune; }

    public String getAvenue() { return avenue; }
    public void setAvenue(String avenue) { this.avenue = avenue; }

    public String getProvince() { return province; }
    public void setProvince(String province) { this.province = province; }

    public String getLogos() { return logos; }
    public void setLogos(String logos) { this.logos = logos; }
	    
	    

}
