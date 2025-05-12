package co.com.nequi.api.mappers;

import co.com.nequi.api.dtos.FranchiseRequest;
import co.com.nequi.api.dtos.ProductRequest;
import co.com.nequi.models.enums.EntityType;
import co.com.nequi.models.franchise.Franchise;
import co.com.nequi.models.product.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(imports = EntityType.class)
public interface ProductMapper {

    ProductMapper MAPPER = Mappers.getMapper(ProductMapper.class);
    @Mapping(target = "entityType", expression = "java(EntityType.PRODUCT.getValue())")
    Product toDomain(ProductRequest request);

}
