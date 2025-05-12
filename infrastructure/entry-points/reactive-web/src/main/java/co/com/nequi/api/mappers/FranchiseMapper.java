package co.com.nequi.api.mappers;

import co.com.nequi.api.dtos.FranchiseRequest;
import co.com.nequi.models.enums.EntityType;
import co.com.nequi.models.franchise.Franchise;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(imports = EntityType.class)
public interface FranchiseMapper {

    FranchiseMapper MAPPER = Mappers.getMapper(FranchiseMapper.class);

    @Mapping(target = "entityType", expression = "java(EntityType.FRANCHISE.getValue())")
    Franchise toDomain(FranchiseRequest request);
}
