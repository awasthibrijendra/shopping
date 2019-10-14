package com.brij.shopping.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class ProductService {

    @Value("${product_service_url}")
    String productUrl;

    @Bean
    public RestTemplate template() {
        return new RestTemplate();
    }
    @Autowired
    private RestTemplate template;

    public String getProductUrl(){
        return productUrl;
    }

    public double getUnitCost(String sku){
        Map params = new HashMap<String,String>();
        params.put("sku", sku);

        Product p = template.getForObject(productUrl+"/products/{sku}", Product.class, params);
        return p.getPrice();

    }
}
