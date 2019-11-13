package org.jeecg.modules.meta.service;

import org.jeecg.modules.meta.entity.MetaData;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 元数据
 * @Author: jeecg-boot
 * @Date:   2019-10-20
 * @Version: V1.0
 */
public interface IMetaDataService extends IService<MetaData> {
    /**
     * @功能：统计下载次数
     */
    public void countPlus(String url);

}
