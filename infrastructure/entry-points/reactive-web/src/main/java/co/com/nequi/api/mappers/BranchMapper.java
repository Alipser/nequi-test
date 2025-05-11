package co.com.nequi.api.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FranchiseMapper {

    FranchiseMapper MAPPER = Mappers.getMapper(FranchiseMapper.class);


}
