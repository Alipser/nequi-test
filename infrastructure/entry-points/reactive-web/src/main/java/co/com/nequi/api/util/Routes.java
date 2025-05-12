package co.com.nequi.api.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Routes {
    public static final String BASE_API = "/api/franquicias";
    public static final String CREATE_FRANCHISE = BASE_API;

    public static final String CREATE_BRANCH = CREATE_FRANCHISE + "/sucursal";

    public static final String CREATE_PRODUCT = CREATE_BRANCH + "/producto";
    public static final String PATH_BRANCH = BASE_API + "/{franquiciaId}/sucursales";
    public static final String PATH_PRODUCT = PATH_BRANCH + "/{sucursalId}/productos";

    public static final String DELETE_PRODUCT = PATH_PRODUCT + "/{productoId}";
    public static final String UPDATE_STOCK = PATH_PRODUCT + "/{productoId}/stock";
    public static final String TOP_PRODUCTS_BY_STOCK = BASE_API + "/{franquiciaId}/productos/mayor-stock";

    public static final String UPDATE_FRANCHISE_NAME = BASE_API + "/{franquiciaId}";
    public static final String UPDATE_BRANCH_NAME = PATH_BRANCH + "/{sucursalId}";
    public static final String UPDATE_PRODUCT_NAME = PATH_PRODUCT + "/{productoId}";
}
