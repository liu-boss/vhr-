package com.itdan.my_vhr.service;

import com.itdan.my_vhr.mapper.NationMapper;
import com.itdan.my_vhr.model.Nation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NationService {

    private Logger logger = LoggerFactory.getLogger(NationService.class);

    @Autowired
    private NationMapper nationMapper;

    /**
     * 获取所有民族列表
     *
     * @return
     */
    public List<Nation> getAllNations() {
        logger.info("获取所有民族列表操作");
        List<Nation> nationList = nationMapper.getAllNations();
        logger.info("获取所有民族列表操作成功");
        return nationList;
    }


}
