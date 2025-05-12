package co.com.nequi.dynamo.adapters.franchise.mapper;

import co.com.nequi.dynamo.entity.RegisterDynamo;
import co.com.nequi.models.franchise.Franchise;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FranchiseToDynamoMapper {

    FranchiseToDynamoMapper MAPPER = Mappers.getMapper(FranchiseToDynamoMapper.class);

    @Mapping(target = "franchise", expression = "java(\"franquicia#\" + franchise.getId())")
    @Mapping(target = "compositeKey", expression = "java(\"FRANCHISE#\" + franchise.getId())")
    @Mapping(target = "uniqueId", source = "id")
    @Mapping(target = "entityType", constant = "FRANCHISE")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "stock", ignore = true)
    @Mapping(target = "sucursalId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "state", ignore = true)
    RegisterDynamo toRegisterDynamo(Franchise franchise);

    @Mapping(target = "id", source = "uniqueId")
    @Mapping(target = "name", source = "name")
    Franchise toDomain(RegisterDynamo register);
}
