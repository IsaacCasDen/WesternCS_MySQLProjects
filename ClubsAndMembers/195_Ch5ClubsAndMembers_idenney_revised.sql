
# 195
# Ch5
# Clubs and Members
# Isaac Denney

# Create Database, Tables

#/*
drop database if exists clubsandmembers;
create database clubsandmembers;

use clubsandmembers;

create table clubs
(
	id int primary key auto_increment,
    clubName varchar(20)
);
create table members
(
	id int primary key auto_increment,
    firstName varchar(40)
);
create table phonenumbers
(
	id int primary key auto_increment,
    phoneNumber varchar(12)
);
create table clubmembers
(
	id int primary key auto_increment,
    clubId int,
    memberId int
);
create table memberphones
(
	id int primary key auto_increment,
    memberId int,
    phoneId int
);

# Insert Rows

insert into clubs (clubName) values ('ski');
set @idSki = last_insert_id();

insert into clubs (clubName) values ('mt bike');
set @idMtBike = last_insert_id();

insert into clubs (clubName) values ('snowboard');
set @idSnowbd = last_insert_id();

insert into clubs (clubName) values ('skydiving');
set @idSkydv = last_insert_id();

insert into members (firstName) values ('Sam');
set @idSam = last_insert_id();

insert into members (firstName) values ('Julie');
set @idJulie = last_insert_id();

insert into members (firstName) values ('Adam');
set @idAdam = last_insert_id();

insert into members (firstName) values ('Devon');
set @idDevon = last_insert_id();

insert into members (firstName) values ('Winnifred');
set @idWinnifred = last_insert_id();

insert into members (firstName) values ('Walt');
set @idWalt = last_insert_id();

insert into members (firstName) values ('Adrian');
set @idAdrian = last_insert_id();

insert into members (firstName) values ('Quinn');
set @idQuinn = last_insert_id();

insert into phonenumbers (phoneNumber) values ('111-1111');
set @id1111111 = last_insert_id();

insert into phonenumbers (phoneNumber) values ('111-2222');
set @id1112222 = last_insert_id();

insert into phonenumbers (phoneNumber) values ('111-4444');
set @id1114444 = last_insert_id();

insert into phonenumbers (phoneNumber) values ('111-5555');
set @id1115555 = last_insert_id();

insert into phonenumbers (phoneNumber) values ('111-7777');
set @id1117777 = last_insert_id();

insert into phonenumbers (phoneNumber) values ('222-1212');
set @id2221212 = last_insert_id();

select * from clubs;
select * from members;
select * from phonenumbers;

insert into clubmembers (clubId,memberId) values (@idSki,@idSam);
insert into clubmembers (clubId,memberId) values (@idSki,@idJulie);
insert into clubmembers (clubId,memberId) values (@idSki,@idAdam);
insert into clubmembers (clubId,memberId) values (@idSki,@idDevon);
insert into clubmembers (clubId,memberId) values (@idSki,@idWinnifred);
insert into clubmembers (clubId,memberId) values (@idMtBike,@idJulie);
insert into clubmembers (clubId,memberId) values (@idMtBike,@idDevon);
insert into clubmembers (clubId,memberId) values (@idSnowbd,@idWalt);
insert into clubmembers (clubId,memberId) values (@idSnowbd,@idAdrian);
insert into clubmembers (clubId,memberId) values (@idSnowbd,@idQuinn);
insert into clubmembers (clubId,memberId) values (@idSnowbd,@idJulie);
insert into clubmembers (clubId,memberId) values (@idSkydv,@idQuinn);
insert into clubmembers (clubId,memberId) values (@idSkydv,@idAdrian);
insert into clubmembers (clubId,memberId) values (@idSkydv,@idJulie);
insert into clubmembers (clubId,memberId) values (@idSkydv,@idWalt);

insert into memberphones (memberId,phoneId) values (@idSam,@id1111111);
insert into memberphones (memberId,phoneId) values (@idJulie,@id1112222);
insert into memberphones (memberId,phoneId) values (@idAdam,@id1112222);
insert into memberphones (memberId,phoneId) values (@idDevon,@id1114444);
insert into memberphones (memberId,phoneId) values (@idWalt,@id1115555);
insert into memberphones (memberId,phoneId) values (@idAdrian,@id1114444);
insert into memberphones (memberId,phoneId) values (@idQuinn,@id1117777);
insert into memberphones (memberId,phoneId) values (@idWinnifred,@id1111111);
insert into memberphones (memberId,phoneId) values (@idQuinn,@id2221212);
insert into memberphones (memberId,phoneId) values (@idJulie,@id1111111);
#*/

#End Create Database, Tables, and Rows

#
# 1. Who is in the ski club?
#

use clubsandmembers;
select
	clubName,
    firstName
from
	clubmembers cm
left join
	clubs c
on
	cm.clubId = c.id
left join
	members m
on
	cm.memberId = m.id
where
	clubName = 'ski';
	

#
# 2. Who's in the snowboard club
#

use clubsandmembers;
select
	clubName,
    firstName
from
	clubmembers cm
left join
	clubs c
on
	cm.clubId = c.id
left join
	members m
on
	cm.memberId = m.id
where
	clubName = 'snowboard';

#
# 3. Who is in each club? One query, display neatly
#

use clubsandmembers;
select
	clubName,
    firstName
from
	clubmembers cm
left join
	clubs c
on
	cm.clubId = c.id
left join
	members m
on
	cm.memberId = m.id
order by
	clubName,
    firstName;

#
# 4. What clubs is Sam in?
#

use clubsandmembers;
select
	clubName,
    firstName
from
	clubmembers cm
left join
	clubs c
on
	cm.clubId = c.id
left join
	members m
on
	cm.memberId = m.id
where
	firstName = 'Sam';

#
# 5. What clubs are each student in? One query, display neatly.
#

use clubsandmembers;
select
	firstName,
    clubName
from
	clubmembers cm
left join
	clubs c
on
	cm.clubId = c.id
left join
	members m
on
	cm.memberId = m.id
order by
	firstName,
    clubName;

#
# 6. What are the phone numbers of the ski club members? With names of course
#

use clubsandmembers;
select
	firstName,
    phonenumber
from
	clubmembers cm
left join
	clubs c
on
	cm.clubId = c.id
left join
	members m
on
	cm.memberId = m.id
left join
	memberphones mp
on 
	m.id = mp.memberId
left join
	phonenumbers p
on
	mp.phoneId = p.id
where
	clubName = 'ski'
order by
	firstName,
    phonenumber;

#
# 7. Who has the phone number 111-2222?s Be sure to list everyone.
#

use clubsandmembers;
select
	firstName
from
	memberphones mp
left join
	members m
on
	mp.memberId = m.id
left join
	phonenumbers p
on
	mp.phoneId = p.id
where
	p.phoneNumber = '111-2222';

#
# 8. What clubs are the 111-2222 owners in?
#

use clubsandmembers;
select distinct
	clubName
from
	clubmembers cm
left join
	clubs c
on
	cm.clubId = c.id
left join
	members m
on
	cm.memberId = m.id
left join
	memberphones mp
on 
	m.id = mp.memberId
left join
	phonenumbers p
on
	mp.phoneId = p.id
where
	phoneNumber = '111-2222'
order by
	clubName;

#
# 9. How many persons are in each club?
#

use clubsandmembers;
select
	clubName,
    count(memberId) memberCount
from
	clubmembers cm
left join
	clubs c
on
	cm.clubId = c.id
group by
	clubName;
	
#
# 10. Who is in both the snowboard and ski club? Can you answer this with what you know?
#

use clubsandmembers;

select
	firstName
from
(
select
    firstName,
    count(*) clubCount
from
	clubmembers cm
left join 
	clubs c
on
	cm.clubId = c.id
left join 
	members m
on	
	cm.memberId = m.id
where
	clubName = 'snowboard' or
    clubName = 'ski'
group by
	firstName
) a
where 
	clubCount>1;
