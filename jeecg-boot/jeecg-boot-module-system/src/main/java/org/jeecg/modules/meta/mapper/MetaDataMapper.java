package org.jeecg.modules.meta.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.meta.entity.MetaData;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 元数据
 * @Author: jeecg-boot
 * @Date:   2019-10-20
 * @Version: V1.0
 */
public interface MetaDataMapper extends BaseMapper<MetaData> {
    /**
     * @功能：统计下载次数
     */
    public void countPlus(@Param("url")String url);

}
