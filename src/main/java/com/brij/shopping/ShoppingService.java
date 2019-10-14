package com.brij.shopping;

import com.brij.shopping.product.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ShoppingService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ProductService productService;

    private static Set<ShoppingCart> carts= new HashSet<ShoppingCart>();
    static {
        Set<CartItem> user1Items = new HashSet<>();
        Set<CartItem> user2Items = new HashSet<>();
        ShoppingCart user1Cart = new ShoppingCart();
        ShoppingCart user2Cart = new ShoppingCart();
        for(int i=1; i<=5;i++){
            CartItem item = new CartItem();
            item.setId(1);
            item.setSku("sku"+i);
            //Get unit cost from product service
            //i.setUnitCost();
           
            user1Items.add(item);
            user2Items.add(item);
        }
        user1Cart.setCustomerId("user1");
        user1Cart.setItems(user1Items);
        user2Cart.setCustomerId("user2");
        user2Cart.setItems(user2Items);
        carts.add(user1Cart);
        carts.add(user2Cart);
    }
    
    public Set<ShoppingCart> getAll(){
        return carts;
    }
    public Optional<ShoppingCart> forUser(String user){
        logger.info("ShoppingService.Controller.cartForUser"+user);
        Optional<ShoppingCart> cart= carts.stream().filter(c -> c.getCustomerId().equalsIgnoreCase(user)).findFirst();
        cart.ifPresent(c ->{
            c.getItems().stream().forEach( i->{
                i.setUnitCost(productService.getUnitCost(i.getSku()));
            });
        });
        return cart;
    }
    public void addToCart(String userName, CartItem item){
        carts.stream().filter(c -> c.getCustomerId().equalsIgnoreCase(userName))
                .findFirst().map(c -> {
                        item.setId(c.getItems().size());
                         c.getItems().add(item);
                      return c.getItems();

        });
        System.out.println(carts);

    }
}
