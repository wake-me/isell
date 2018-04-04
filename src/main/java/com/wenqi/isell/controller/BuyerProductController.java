package com.wenqi.isell.controller;

import com.wenqi.isell.entity.ProductCategory;
import com.wenqi.isell.entity.ProductInfo;
import com.wenqi.isell.service.ProductCategoryService;
import com.wenqi.isell.service.ProductInfoService;
import com.wenqi.isell.util.HttpResult;
import com.wenqi.isell.util.ResultUtil;
import com.wenqi.isell.vo.ProductInfoVO;
import com.wenqi.isell.vo.ProductVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 文琪
 * @Description: 买家商品
 * @Date: Created in 2018/3/26 下午12:38
 * @Modified By:
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductCategoryService categoryService;

    @Autowired
    private ProductInfoService infoService;

    /**
     * 买家查询所有上架的商品
     *
     * @return
     * @throws Exception
     */
    @GetMapping("/list")
//    @Cacheable(cacheNames = "product",key = "123")
    // caheNames 换成名称， key缓存
    @Cacheable(cacheNames = "product" ,key = "#sellerId" ,condition = "#sellerId.length()>3" ,unless = "#result.getCode() != 0")
    public HttpResult list(@RequestParam String sellerId) throws Exception {

        // 1.查询所有上架商品
        List<ProductInfo> productInfoList = infoService.findUpAll();

        // 2.查询类目(一次查询)
        List<Integer> categoryTypeList = new ArrayList<>();
        //传统方法
//        for (ProductInfo productInfo: productInfoList){
//            categoryTypeList.add(productInfo.getCategoryType());
//        }
        // 精简方法（Java8, lambda）
        categoryTypeList = productInfoList.stream()
                .map(e -> e.getCategoryType())
                .collect(Collectors.toList());

        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(categoryTypeList);

        // 3.数据拼装
        List<ProductVO> productVOList = new ArrayList<>();
        for (ProductCategory productCategory : productCategoryList) {
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(productCategory.getCategoryName());
            productVO.setCategoryType(productCategory.getCategoryType());

            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for (ProductInfo productInfo : productInfoList) {
                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())) {
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo, productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }


        return ResultUtil.success(productVOList);
    }
}
