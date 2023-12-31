-- auto-generated definition
create table user
(
    id            bigint auto_increment comment 'id'
        primary key,
    user_account  varchar(256)                           not null comment '账号',
    user_password varchar(512)                           not null comment '密码',
    union_id      varchar(256)                           null comment '微信开放平台id',
    mp_openId     varchar(256)                           null comment '公众号openId',
    user_name     varchar(256)                           null comment '用户昵称',
    user_avatar   varchar(1024)                          null comment '用户头像',
    user_profile  varchar(512)                           null comment '用户简介',
    user_role     varchar(256) default 'user'            not null comment '用户角色：user/admin/ban',
    create_time   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    is_delete     tinyint      default 0                 not null comment '是否删除',
    accesskey     varchar(512)                           not null comment 'accesskey',
    secretkey     varchar(512)                           not null comment 'secretkey'
)
    comment '用户' collate = utf8mb4_unicode_ci;

create index idx_unionId
    on user (union_id);

-- auto-generated definition
create table user_interface_info
(
    id               bigint auto_increment comment '主键'
        primary key,
    user_id          bigint                             not null comment '调用用户 id',
    interfaceInfo_id bigint                             not null comment '接口 id',
    total_num        int      default 0                 not null comment '总调用次数',
    left_num         int      default 0                 not null comment '剩余调用次数',
    status           int      default 0                 not null comment '0-正常，1-禁用',
    create_time      datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time      datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    is_delete        tinyint  default 0                 not null comment '是否删除(0-未删, 1-已删)'
)
    comment '用户调用接口关系';

-- auto-generated definition
create table interface_info
(
    id              bigint auto_increment comment '主键'
        primary key,
    name            varchar(256)                       not null comment '名称',
    description     varchar(256)                       null comment '描述',
    url             varchar(512)                       not null comment '接口地址',
    request_header  text                               null comment '请求头',
    response_header text                               null comment '响应头',
    status          int      default 0                 not null comment '接口状态（0-关闭，1-开启）',
    method          varchar(256)                       not null comment '请求类型',
    create_by       bigint                             not null comment '创建人',
    create_time     datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_by       bigint                             null comment '更新人',
    update_time     datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    is_delete       tinyint  default 0                 not null comment '是否删除(0-未删, 1-已删)',
    request_body    varchar(512)                       null comment '请求参数',
    response_body   varchar(512)                       not null comment '返回响应'
)
    comment '接口信息';

