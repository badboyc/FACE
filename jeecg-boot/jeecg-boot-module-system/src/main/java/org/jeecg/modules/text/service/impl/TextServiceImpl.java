package org.jeecg.modules.text.service.impl;

import org.jeecg.modules.text.entity.Text;
import org.jeecg.modules.text.mapper.TextMapper;
import org.jeecg.modules.text.service.ITextService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 文本编辑
 * @Author: jeecg-boot
 * @Date:   2019-12-18
 * @Version: V1.0
 */
@Service
public class TextServiceImpl extends ServiceImpl<TextMapper, Text> implements ITextService {

}
