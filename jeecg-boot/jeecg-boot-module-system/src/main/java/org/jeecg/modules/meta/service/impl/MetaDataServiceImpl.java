package org.jeecg.modules.meta.service.impl;

import org.jeecg.modules.meta.entity.MetaData;
import org.jeecg.modules.meta.mapper.MetaDataMapper;
import org.jeecg.modules.meta.service.IMetaDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 元数据
 * @Author: jeecg-boot
 * @Date:   2019-10-20
 * @Version: V1.0
 */
@Service
public class MetaDataServiceImpl extends ServiceImpl<MetaDataMapper, MetaData> implements IMetaDataService {
   @Autowired
    private MetaDataMapper metaDataMapper;
    /**
     * @功能：统计下载次数
     */
    @Override
    public void countPlus(String uocPackageUrl) {
        metaDataMapper.countPlus(uocPackageUrl);
    }

}
