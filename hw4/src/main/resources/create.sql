create table task_list
(
    id   serial      not null primary key,
    name text unique not null
);


create table task
(
    id        serial  not null primary key,
    list_id   int,
    name      text    not null,
    completed boolean not null,
    constraint list_fk foreign key (list_id) references task_list (id),
    constraint unique_name unique (list_id, name)
);