package com.sky.service;

import com.sky.dto.ShoppingCartDTO;

public interface ShoppingCartService {
    /**
     * 添加菜品至购物车
     * @param shoppingCartDTO
     */
 void addShoppingCart(ShoppingCartDTO shoppingCartDTO);
 }