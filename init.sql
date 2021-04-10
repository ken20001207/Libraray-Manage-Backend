create table book
(
    bno      char(20),
    category varchar(100),
    title    varchar(200),
    press    varchar(20),
    year     int,
    author   varchar(10),
    price    decimal(7, 2),
    total    int,
    stock    int,
    primary key (bno)
);

create table card
(
    cno        char(7),
    name       varchar(10),
    department varchar(40),
    type       char(1),
    primary key (cno),
    check (type in ('T', 'S'))
);

create table borrow
(
    uuid        char(20),
    cno         char(7),
    bno         char(8),
    borrow_date date,
    return_date date,
    primary key (uuid),
    foreign key (cno) references card (cno),
    foreign key (bno) references book (bno)
);
