package com.sky.controller.user;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import com.sky.service.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/shoppingCart")
@Slf4j
@Api(tags = "C窜购物车接口")
public class ShoppingCartContoller {
    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * 新增数据到购物车
     * @param shoppingCartDTO
     * @return
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增数据到购物车")
    public Result add(@RequestBody ShoppingCartDTO shoppingCartDTO){
        log.info("新增数据到购物车");
        shoppingCartService.addShoppingCart(shoppingCartDTO);
        return Result.success();
    }

    /**
     * 获取购物车数据
     */
    @GetMapping("/list")
    @ApiOperation(value = "获取购物车数据")
    public Result list(){
        //获取用户id，以便查看某个用户的购物车
        Long currentId = BaseContext.getCurrentId();
        ShoppingCart shoppingCart = new ShoppingCart();

        //使用设计好的动态查询（可选参数有,userId,dishId,setmealId,dishFlavor...），此时只需要用户id
        shoppingCart.builder()
                .id(currentId)
                .build();
        List<ShoppingCart> shoppingCartList = shoppingCartService.showShoppingCart(shoppingCart);
        return Result.success(shoppingCartList);
    }

    /**
     * 清空购物车数据
     */
    @DeleteMapping("/clean")
    @ApiOperation(value = "清空购物车数据")
    public Result clean(){
        //获取用户id，以便查看某个用户的购物车
        Long currentId = BaseContext.getCurrentId();
        shoppingCartService.cleanShoppingCart(currentId);
        return Result.success();
    }

    /**
     * 删除购物车数据
     */
    @PostMapping("/sub")
    @ApiOperation(value = "删除购物车数据")
    public Result delete(@RequestBody ShoppingCartDTO shoppingCartDTO){
        shoppingCartService.subShoppingCart(shoppingCartDTO);
        return Result.success();
    }
}
