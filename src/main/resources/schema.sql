create schema mechanic
  authorization postgres;

alter table accumulator_auto
    drop constraint FKi76mu6sowtqvmr84jjsqcw5x0;

alter table accumulator_auto
    drop constraint FKtdcsx1j90193p0r24b9c8x4t0;

alter table bus_auto
    drop constraint FK9ggdgocltb4odv6ibqh8i66iv;

alter table bus_auto
    drop constraint FKd4ax4mhaew9cqyxhe8s5k624t;

alter table trip
    drop constraint FK8g9bg7l93ocv27vxqd70xx45q;

alter table trip
    drop constraint FKfilni6cj0t8rqtqt0fn4u6h74;

drop table if exists accumulator cascade;

drop table if exists accumulator_auto cascade;

drop table if exists auto cascade;

drop table if exists bus cascade;

drop table if exists bus_auto cascade;

drop table if exists employee cascade;

drop table if exists trip cascade;

drop sequence hibernate_sequence;

create sequence hibernate_sequence start 1 increment 1;

create table accumulator (
    id int4 not null,
    cost float8,
    create_date varchar(255),
    factory varchar(50),
    factoryNumber varchar(10),
    number varchar(10),
    type varchar(25),
    primary key (id)
);

create table accumulator_auto (
    id int4 not null,
    condition varchar(255),
    end_date varchar(255),
    reason varchar(255),
    start_date varchar(255),
    accumulator_id int4,
    auto_id int4,
    primary key (id)
);
create table auto (
    id int4 not null,
    gos_number varchar(10),
    model varchar(25),
    number varchar(10),
    primary key (id)
);

create table bus (
    id int4 not null,
    cost float8,
    date_create varchar(255),
    factory varchar(50),
    factoryNumber varchar(10),
    indication varchar(255),
    model varchar(30),
    norm varchar(20),
    primary key (id)
);

create table bus_auto (
    id int4 not null,
    condition varchar(255),
    end_date varchar(255),
    reason varchar(255),
    start_date varchar(255),
    auto_id int4,
    Bus_id int4,
    primary key (id)
);

create table employee (
    id int4 not null,
    full_name varchar(50),
    position varchar(30),
    primary key (id)
);

create table trip (
    id int4 not null,
    end_date varchar(255),
    mileage float8,
    start_date varchar(255),
    auto_id int4,
    employee_id int4,
    primary key (id)
);

alter table accumulator_auto
    add constraint FKi76mu6sowtqvmr84jjsqcw5x0
    foreign key (accumulator_id)
    references accumulator;

alter table accumulator_auto
    add constraint FKtdcsx1j90193p0r24b9c8x4t0
    foreign key (auto_id)
    references auto;

alter table bus_auto
    add constraint FK9ggdgocltb4odv6ibqh8i66iv
    foreign key (auto_id)
    references auto;

alter table bus_auto
    add constraint FKd4ax4mhaew9cqyxhe8s5k624t
    foreign key (Bus_id)
    references bus;

alter table trip
    add constraint FK8g9bg7l93ocv27vxqd70xx45q
    foreign key (auto_id)
    references auto;

alter table trip
    add constraint FKfilni6cj0t8rqtqt0fn4u6h74
    foreign key (employee_id)
    references employee;