package co.com.nequi.api.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Routes {
    public static final String BASE_API = "/api/franquicias";
    public static final String CREATE_FRANCHISE = BASE_API;
    public static final String CREATE_BRANCH = BASE_API + "/{franquiciaId}/sucursales";
    public static final String CREATE_PRODUCT = CREATE_BRANCH + "/{sucursalId}/productos";

    public static final String DELETE_PRODUCT = CREATE_PRODUCT + "/{productoId}";
    public static final String UPDATE_STOCK = CREATE_PRODUCT + "/{productoId}/stock";
    public static final String TOP_PRODUCTS_BY_STOCK = BASE_API + "/{franquiciaId}/productos/mayor-stock";

    public static final String UPDATE_FRANCHISE_NAME = BASE_API + "/{franquiciaId}";
    public static final String UPDATE_BRANCH_NAME = CREATE_BRANCH + "/{sucursalId}";
    public static final String UPDATE_PRODUCT_NAME = CREATE_PRODUCT + "/{productoId}";
}
