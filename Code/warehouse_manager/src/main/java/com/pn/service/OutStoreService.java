package com.pn.service;

import com.pn.entity.OutStore;
import com.pn.entity.Result;

public interface OutStoreService {
    //添加出库单的业务方法
    public Result saveOutStore(OutStore outStore);

//   确认出库的业务方法
   public Result outStoreConfirm(OutStore outStore);
}
