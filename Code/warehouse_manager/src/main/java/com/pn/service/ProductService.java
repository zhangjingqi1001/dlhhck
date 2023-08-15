package com.pn.service;

import com.pn.entity.Product;
import com.pn.page.Page;

public interface ProductService {

    public Page queryProductPage(Page page, Product product);
}
