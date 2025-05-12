package co.com.nequi.api.mappers;

import co.com.nequi.api.dtos.BranchRequest;
import co.com.nequi.models.branch.Branch;
import co.com.nequi.models.enums.EntityType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(imports = EntityType.class)
public interface BranchMapper {

    BranchMapper MAPPER = Mappers.getMapper(BranchMapper.class);

    @Mapping(target = "entityType", expression = "java(EntityType.BRANCH.getValue())")
    Branch toDomain(BranchRequest request);


}
