package com.sky.service;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {
    /**
     * 添加菜品至购物车
     * @param shoppingCartDTO
     */
 void addShoppingCart(ShoppingCartDTO shoppingCartDTO);

    /**
     * 查询购物车数据
     * @param shoppingCart
     * @return
     */
    List<ShoppingCart> showShoppingCart(ShoppingCart shoppingCart);

    /**
     * 清空购物车数据
     * @param currentId
     */
    void cleanShoppingCart(Long currentId);

    /**
     * 删除购物车数据
     * @param shoppingCartDTO
     */
    void subShoppingCart(ShoppingCartDTO shoppingCartDTO);
}