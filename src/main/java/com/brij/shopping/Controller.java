package com.brij.shopping;

import com.brij.shopping.product.ProductService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
//import org.springframework.cloud.sleuth.sampler.AlwaysSampler;

import java.util.Set;

@RestController
public class Controller {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

//    @Bean
//    public AlwaysSampler defaultSampler() {
//        return new AlwaysSampler();
//    }

    @Autowired
    ShoppingService service;

    @Autowired
    ProductService productService;

    @Autowired
    StockService stockService;

    @GetMapping("/")
    public String home(){
        return "shopping home";
    }

    @GetMapping("/stockUrl")
    public String getStockUrl(){
        return stockService.url();
    }
    @GetMapping("/stockUrl/{sku}")
    public String getStockUrlStock(@PathVariable String sku){

        RestTemplateBuilder builder = new RestTemplateBuilder();
        RestTemplate t= builder.basicAuthorization("admin", "pass").build();
        ResponseEntity<String> e = t.getForEntity(stockService.url()+"/"+sku, String.class);
//        RestTemplate t = new RestTemplate();
//        String stock= t.getForObject(stockService.url()+"/"+sku, String.class);
        return String.valueOf(e.getStatusCodeValue()) + ":::" +e.getBody();
    }

    @GetMapping("/productUrl")
    public String getProductUrl(){
        return productService.getProductUrl();
    }
    //aloww only admins to get the list
    @GetMapping("/carts")
    @PreAuthorize("hasRole('ADMIN')")
    public Set<ShoppingCart> allCarts(){
        return service.getAll();
    }

    //ALLOW hte logged in user to see only his cart
    @GetMapping("/carts/{user}")
    @PreAuthorize("#user == principal.username || hasRole('ADMIN')")
    public ResponseEntity<ShoppingCart> cartForUser(@PathVariable String user){
        logger.info("Controller.cartForUser"+user);
        return service.forUser(user).map(c -> ResponseEntity.ok(c))
                .orElse( new ResponseEntity<>(HttpStatus.NO_CONTENT))
                ;
    }

    @PostMapping("/")
    public HttpStatus addToCart(CartItem item, @AuthenticationPrincipal User user){
        String userName= user.getUsername();
        item.setUnitCost(productService.getUnitCost(item.getSku()));
        service.addToCart(userName, item);
        return HttpStatus.CREATED;
    }


    //Add the items to logged in user cart
//    @PostMapping("/carts")
//    public ResponseEntity<HttpStatus> postCart(RequestBody Set<CartItem> items){
//        return new ResponseEntity(HttpStatus.CREATED);
//
//    }


}
