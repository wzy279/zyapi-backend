-- 接口信息
create table if not exists zyapi.`interface_info` (
    `id` bigint not null auto_increment comment '主键' primary key,
     `name` varchar(256) not null comment '名称',
      `description` varchar(256) null comment '描述',
       `url` varchar(512) not null comment '接口地址',
        `request_header` text null comment '请求头',
         `response_header` text null comment '响应头',
          `status` int default 0 not null comment '接口状态（0-关闭，1-开启）',
           `method` varchar(256) not null comment '请求类型',
            `create_by` bigint not null comment '创建人',
             `create_time` datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `update_by` bigint not null comment '更新人',
              `update_time` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
               `is_delete` tinyint default 0 not null comment '是否删除(0-未删, 1-已删)' ) comment '接口信息';
