create schema if not exists user_management;

create table if not exists user_management.t_user
(
    id         serial primary key,
    c_name     varchar(50) not null check ( length( trim(c_name)) > 0 ),
    c_password varchar(100) not null check ( length(trim(c_password)) > 0 )
);

create table if not exists user_management.t_role
(
    id     serial primary key,
    c_name varchar(50) not null check ( length(trim(c_name)) > 0 )
);

create table if not exists user_management.t_user_role
(
    c_user_id int not null,
    c_role_id int not null,
    primary key (c_user_id, c_role_id),
    constraint fk_user_role_user foreign key (c_user_id)
        references user_management.t_user (id),
    constraint fk_user_role_role foreign key (c_role_id)
        references user_management.t_role (id)
);