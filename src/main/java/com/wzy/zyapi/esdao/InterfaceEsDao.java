package com.wzy.zyapi.esdao;

import com.wzy.zyapi.model.dto.interfaceinfo.InterfaceEsDTO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @description
 * @author: Wangzhaoyi
 * @create: 2023-11-03 20:38
 **/
public interface InterfaceEsDao extends ElasticsearchRepository<InterfaceEsDTO,Long> {


}
