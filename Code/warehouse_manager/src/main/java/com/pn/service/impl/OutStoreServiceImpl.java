package com.pn.service.impl;

import com.pn.entity.OutStore;
import com.pn.entity.Result;
import com.pn.mapper.OutStoreMapper;
import com.pn.service.OutStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OutStoreServiceImpl implements OutStoreService {

  @Autowired
  private OutStoreMapper outStoreMapper;

    @Override
    public Result saveOutStore(OutStore outStore) {
//      添加
        int success = outStoreMapper.insertOutStore(outStore);

        return success>0? Result.ok("添加出货单成功") :Result.err(501,"添加出货单失败");
    }
}
