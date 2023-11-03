package com.wzy.zyapi.job.once;

import com.wzy.zyapi.esdao.InterfaceEsDao;
import com.wzy.zyapi.model.dto.interfaceinfo.InterfaceEsDTO;
import com.wzy.zyapi.service.InterfaceInfoService;
import com.wzy.zycommon.model.entity.InterfaceInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.boot.CommandLineRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 全量同步帖子到 es
 *
 */
// todo 取消注释开启任务
//@Component
@Slf4j
public class FullSyncPostToEs implements CommandLineRunner {


    @Resource
    private InterfaceInfoService interfaceInfoService;

    @Resource
    private InterfaceEsDao interfaceEsDao;


    @Override
    public void run(String... args) {
        List<InterfaceInfo> postList = interfaceInfoService.list();
        if (CollectionUtils.isEmpty(postList)) {
            return;
        }
        List<InterfaceEsDTO> interfaceEsDTOList = postList.stream().map(InterfaceEsDTO::objToDto).collect(Collectors.toList());
        final int pageSize = 500;
        int total = interfaceEsDTOList.size();
        log.info("FullSyncPostToEs start, total {}", total);
        for (int i = 0; i < total; i += pageSize) {
            int end = Math.min(i + pageSize, total);
            log.info("sync from {} to {}", i, end);
            interfaceEsDao.saveAll(interfaceEsDTOList.subList(i, end));
        }
        log.info("FullSyncPostToEs end, total {}", total);
    }
}
