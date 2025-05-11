package co.com.nequi.api;

import co.com.nequi.api.handlers.BranchHandler;
import co.com.nequi.api.handlers.FranchiseHandler;
import co.com.nequi.api.handlers.ProductHandler;
import co.com.nequi.api.util.Routes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {
    @Bean
    public RouterFunction<ServerResponse> routerFunction(FranchiseHandler franchiseHandler, BranchHandler branchHandler, ProductHandler productHandler){
        return route(POST(Routes.CREATE_FRANCHISE), franchiseHandler::createFranchise)
                .andRoute(POST(Routes.CREATE_BRANCH), branchHandler::createBranch)
                .andRoute(POST(Routes.CREATE_PRODUCT),  productHandler::createProduct)
                .andRoute(DELETE(Routes.DELETE_PRODUCT),  productHandler::deleteProduct)
                .andRoute(PUT(Routes.UPDATE_STOCK), branchHandler::updateStock)
                .andRoute(GET(Routes.TOP_PRODUCTS_BY_STOCK), franchiseHandler::getTopProductsByStock)
                .andRoute(PUT(Routes.UPDATE_FRANCHISE_NAME), franchiseHandler::updateFranchiseName)
                .andRoute(PUT(Routes.UPDATE_BRANCH_NAME), branchHandler::updateBranchName)
                .andRoute(PUT(Routes.UPDATE_PRODUCT_NAME), productHandler::updateProductName);
    }
}

