create table book
(
    bno      char(100),
    category varchar(100),
    title    varchar(200),
    press    varchar(100),
    year     int,
    author   varchar(100),
    price    decimal(7, 2),
    total    int,
    stock    int,
    primary key (bno)
);

create table card
(
    cno        char(7),
    name       varchar(100),
    department varchar(100),
    type       char(1),
    primary key (cno),
    check (type in ('T', 'S'))
);

create table borrow
(
    uuid        char(20) not null
        primary key,
    cno         char(100) null,
    bno         char(100) null,
    borrow_date date null,
    return_date date null,
    constraint borrow_ibfk_1
        foreign key (cno) references library.card (cno)
            on update cascade on delete set null,
    constraint borrow_ibfk_2
        foreign key (bno) references library.book (bno)
            on update cascade on delete set null
);
