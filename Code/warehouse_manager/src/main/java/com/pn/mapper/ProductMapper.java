package com.pn.mapper;

import com.pn.entity.Product;
import com.pn.entity.ProductType;
import com.pn.page.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductMapper {

//  查询商品行数的方法
    public Integer findProductRowCount(Product product);

//  分页查询商品的方法
    public List<Product> findProductPage(@Param("page") Page page, @Param("product") Product product);

//  查询所有商品分类的方法
    public List<ProductType>  findAllProductType();

//  添加商品的方法
    public int insertProduct(Product product);

//  根据型号查询商品的方法
    public Product findProductByNum(@Param("productNum") String productNum);

//  根据商品id修改商品上下架状态的方法
    public int setStateByPid(@Param("productId")Integer productId,@Param("upDownState")String upDownState);

//  根据商品id删除商品的方法
    public int removeProductByIds(List<Integer> productIdList);

//  根据商品的id修改商品的信息
    public int setProductById(Product product);

//  根据分类编码或者分类名称查询商品分类的方法
    public ProductType findTypeByCodeOrName(ProductType productType);

//  添加商品分类的方法
    public int insertProductType(ProductType productType);

//  根据分类id或父级分类id删除分类的方法
    public int removeProductType(Integer typeId);

//  根据分类id修改分类的方法
    public int setProductTypeById(ProductType productType);

//  根据id修改商品库存的方法
    public int setInventById(@Param("productId")Integer productId,@Param("invent")Integer invent);

//  根据商品id查询商品库存的方法
    public int findInventById(@Param("productId")Integer productId);

}
