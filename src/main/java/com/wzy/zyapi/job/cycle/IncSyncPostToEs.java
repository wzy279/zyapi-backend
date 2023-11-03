package com.wzy.zyapi.job.cycle;

import com.wzy.zyapi.esdao.InterfaceEsDao;
import com.wzy.zyapi.mapper.InterfaceInfoMapper;
import com.wzy.zyapi.model.dto.interfaceinfo.InterfaceEsDTO;
import com.wzy.zycommon.model.entity.InterfaceInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 增量同步帖子到 es
 *
 */
// todo 取消注释开启任务
//@Component
@Slf4j
public class IncSyncPostToEs {



    @Resource
    private InterfaceInfoMapper interfaceInfoMapper;

    @Resource
    private InterfaceEsDao interfaceEsDao;


    /**
     * 每分钟执行一次
     */
    @Scheduled(fixedRate = 60 * 1000)
    public void run() {
        // 查询近 5 分钟内的数据
        Date fiveMinutesAgoDate = new Date(new Date().getTime() - 5 * 60 * 1000L);
        List<InterfaceInfo> interfaceInfoList = interfaceInfoMapper.queryAllByUpdateTime(fiveMinutesAgoDate);
        if (CollectionUtils.isEmpty(interfaceInfoList)) {
            log.info("no inc interface");
            return;
        }
        List<InterfaceEsDTO> interfaceEsDTOList = interfaceInfoList.stream()
                .map(InterfaceEsDTO::objToDto)
                .collect(Collectors.toList());
        final int pageSize = 500;
        int total = interfaceEsDTOList.size();
        log.info("IncSyncPostToEs start, total {}", total);
        for (int i = 0; i < total; i += pageSize) {
            int end = Math.min(i + pageSize, total);
            log.info("sync from {} to {}", i, end);
            interfaceEsDao.saveAll(interfaceEsDTOList.subList(i, end));
        }
        log.info("IncSyncPostToEs end, total {}", total);
    }
}
