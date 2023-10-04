package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.vo.SetmealVO;

import java.util.List;

public interface SetmealService {
    /**
     * 新增套餐
     */
    void save(SetmealDTO setmealDTO);

    /**
     * 套餐分页查询
     * @param setmealPageQueryDTO
     * @return
     */
    PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 批量删除套餐
     * @param ids
     */
    void deleteBatch(List<Long> ids);

    /**
     * 获取套餐信息以及菜品信息
     * @param id
     * @return
     */
    SetmealVO getByIdWithSetmealDish(Long id);

    /**
     * 修改套餐
     * @param setmealDTO
     */
    void updateWithSetmealDish(SetmealDTO setmealDTO);

    /**
     * 套餐启售、禁售
     * @param status
     * @param id
     */
    void startOrStop(int status, Long id);
}
