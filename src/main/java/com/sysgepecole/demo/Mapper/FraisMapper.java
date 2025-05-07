package com.sysgepecole.demo.Mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.sysgepecole.demo.Dto.FraisDto;
import com.sysgepecole.demo.Models.Frais;

@Mapper(componentModel = "spring")
public interface FraisMapper {
	
	@Mapping(source = "idtranche", target = "tranche.idtranche")
    @Mapping(source = "idcategorie", target = "categorie.idcategorie")
    @Mapping(source = "idintermedaireannee", target = "intermedaireAnnee.idintermedaireannee")
    @Mapping(source = "idintermedaireclasse", target = "intermedaireClasse.idintermedaireclasse")
    Frais toEntity(FraisDto dto);

    @Mapping(source = "tranche.idtranche", target = "idtranche")
    @Mapping(source = "categorie.idcategorie", target = "idcategorie")
    @Mapping(source = "intermedaireAnnee.idintermedaireannee", target = "idintermedaireannee")
    @Mapping(source = "intermedaireClasse.idintermedaireclasse", target = "idintermedaireclasse")
    FraisDto toDto(Frais entity);

}
