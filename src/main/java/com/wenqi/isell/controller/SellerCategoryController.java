package com.wenqi.isell.controller;

import com.wenqi.isell.entity.ProductCategory;
import com.wenqi.isell.exception.ISellException;
import com.wenqi.isell.form.CategoryForm;
import com.wenqi.isell.service.ProductCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @Author: 文琪
 * @Description: 商品类目
 * @Date: Created in 2018/4/2 下午12:29
 * @Modified By:
 */
@Controller()
@Slf4j
@RequestMapping("seller/category")
public class SellerCategoryController {

    @Autowired
    private ProductCategoryService categoryService;

    /**
     * 获取商品类目列表
     * @param map
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(Map<String,Object> map){
        List<ProductCategory> categoryList = categoryService.findALL();
        map.put("categoryList",categoryList);
        return new ModelAndView("category/list", map);
    }

    /**
     *  新增修改商品类目界面
     * @param categoryId
     * @param map
     * @return
     */
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "categoryId" ,required = false) Integer categoryId,
                              Map<String,Object> map){
        if(categoryId != null){
          ProductCategory productCategory =  categoryService.findOne(categoryId);
          map.put("productCategory",productCategory);
        }
        return new ModelAndView("category/index",map);
    }

    /**
     * 新增修改商品类型
     * @param form
     * @param bindingResult
     * @param map
     * @return
     */
    @PostMapping("/save")
    public ModelAndView save(@Valid CategoryForm form,
                             BindingResult bindingResult,
                             Map<String,Object> map){
        if (bindingResult.hasErrors()){
            map.put("msg",bindingResult.getFieldError().getDefaultMessage());
            map.put("url","/sell/seller/category/index");
            return new ModelAndView("common/error",map);
        }
        ProductCategory productCategory = new ProductCategory();
        try {
            if (form.getCategoryId() !=null){
                productCategory = categoryService.findOne(form.getCategoryId());
            }
            productCategory.setCategoryType(form.getCategoryType());
            productCategory.setCategoryName(form.getCategoryName());

            categoryService.save(productCategory);
        }catch (ISellException e){
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/category/index");
            return new ModelAndView("common/error",map);
        }

        map.put("url","/sell/seller/category/list");
        return new ModelAndView("common/success",map);
    }
}
