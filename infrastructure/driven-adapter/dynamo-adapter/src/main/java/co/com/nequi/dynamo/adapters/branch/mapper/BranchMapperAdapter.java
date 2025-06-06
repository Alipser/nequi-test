package co.com.nequi.dynamo.adapters.branch.mapper;

import co.com.nequi.dynamo.entity.RegisterDynamo;
import co.com.nequi.models.branch.Branch;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


public interface BranchMapperAdapter {

    BranchMapperAdapter MAPPER = Mappers.getMapper(BranchMapperAdapter.class);
    @Mapping(target = "franchise", expression = "java(\"franquicia#\" + branch.getFranchiseId())")
    @Mapping(target = "compositeKey", expression = "java(\"BRANCH#\" + branch.getId())")
    @Mapping(target = "uniqueId", source = "branch.id")
    @Mapping(target = "entityType", constant = "branch.entityType")
    @Mapping(target = "name", source = "branch.name")
    @Mapping(target = "stock", ignore = true)
    @Mapping(target = "sucursalId", ignore = true)
    @Mapping(target = "createdAt", source = "now")
    @Mapping(target = "updatedAt", source = "now")
    @Mapping(target = "state", ignore = true)
    RegisterDynamo toRegisterDynamo(Branch branch, String now);

    @Mapping(target = "id", source = "uniqueId")
    @Mapping(target = "franchiseId", expression = "java(register.getFranchise().replace(\"franquicia#\", \"\"))")
    @Mapping(target = "name", source = "name")
    Branch toDomain(RegisterDynamo register);
}
