package com.brij.shopping;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StockService {

    @Value("${stock_service_url}")
    String stockUrl;

    public String url(){
        return stockUrl;
    }
}
