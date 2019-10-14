package com.brij.shopping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class WebController {

    @GetMapping("/addCartItem")
    public String addCartItem(CartItem cartItem){
        return "addCartItem";

    }
}
