package co.com.nequi.dynamo.adapters.product.mapper;

import co.com.nequi.dynamo.entity.RegisterDynamo;
import co.com.nequi.models.product.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductAdapterMapper {

    ProductAdapterMapper MAPPER = Mappers.getMapper(ProductAdapterMapper.class);

    @Mapping(target = "franchise", expression = "java(\"franquicia#\" + product.getFranchiseId())")
    @Mapping(target = "compositeKey", expression = "java(\"PRODUCT#\" + product.getId())")
    @Mapping(target = "uniqueId", source = "id")
    @Mapping(target = "entityType", constant = "PRODUCT")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "stock", source = "stock")
    @Mapping(target = "sucursalId", source = "branchId")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "state", ignore = true)
    RegisterDynamo toRegisterDynamo(Product product);

    @Mapping(target = "id", source = "uniqueId")
    @Mapping(target = "franchiseId", expression = "java(register.getFranchise().replace(\"franquicia#\", \"\"))")
    @Mapping(target = "branchId", source = "sucursalId")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "stock", source = "stock")
    Product toDomain(RegisterDynamo register);


}
