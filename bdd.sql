create database if not exists PROG_BD;
use PROG_BD;

create table if not exists programmers
(
    id         int auto_increment primary key,
    name       varchar(100)   not null,
    first_name varchar(100)   not null,
    birthdate  date           not null,
    salary     decimal(10, 2) not null,
    bonus      decimal(10, 2) null,
    username   varchar(50)    not null unique
);

create table if not exists projects
(
    id         int auto_increment primary key,
    name       varchar(100) not null,
    start_date date         not null,
    end_date   date         null,
    state      varchar(50)  not null
);

create table if not exists programmer_project
(
    programmer_id int not null,
    project_id    int not null,
    primary key (programmer_id, project_id),
    foreign key (programmer_id) references programmers (id) on delete cascade,
    foreign key (project_id) references projects (id) on delete cascade
);
