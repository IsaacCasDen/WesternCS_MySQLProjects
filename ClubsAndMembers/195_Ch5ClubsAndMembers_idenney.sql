
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
insert into clubs (clubName) values ('mt bike');
insert into clubs (clubName) values ('snowboard');
insert into clubs (clubName) values ('skydiving');

insert into members (firstName) values ('Sam');
insert into members (firstName) values ('Julie');
insert into members (firstName) values ('Adam');
insert into members (firstName) values ('Devon');
insert into members (firstName) values ('Winnifred');
insert into members (firstName) values ('Walt');
insert into members (firstName) values ('Adrian');
insert into members (firstName) values ('Quinn');

insert into phonenumbers (phoneNumber) values ('111-1111');
insert into phonenumbers (phoneNumber) values ('111-2222');
insert into phonenumbers (phoneNumber) values ('111-4444');
insert into phonenumbers (phoneNumber) values ('111-5555');
insert into phonenumbers (phoneNumber) values ('111-7777');
insert into phonenumbers (phoneNumber) values ('222-1212');

#select * from clubs;
#select * from members;
#select * from phonenumbers;

insert into clubmembers (clubId,memberId) values (1,1);
insert into clubmembers (clubId,memberId) values (1,2);
insert into clubmembers (clubId,memberId) values (1,3);
insert into clubmembers (clubId,memberId) values (1,4);
insert into clubmembers (clubId,memberId) values (1,5);
insert into clubmembers (clubId,memberId) values (2,2);
insert into clubmembers (clubId,memberId) values (2,4);
insert into clubmembers (clubId,memberId) values (3,6);
insert into clubmembers (clubId,memberId) values (3,7);
insert into clubmembers (clubId,memberId) values (3,8);
insert into clubmembers (clubId,memberId) values (3,2);
insert into clubmembers (clubId,memberId) values (4,8);
insert into clubmembers (clubId,memberId) values (4,7);
insert into clubmembers (clubId,memberId) values (4,2);
insert into clubmembers (clubId,memberId) values (4,6);

insert into memberphones (memberId,phoneId) values (1,1);
insert into memberphones (memberId,phoneId) values (2,2);
insert into memberphones (memberId,phoneId) values (3,2);
insert into memberphones (memberId,phoneId) values (4,3);
insert into memberphones (memberId,phoneId) values (6,4);
insert into memberphones (memberId,phoneId) values (7,3);
insert into memberphones (memberId,phoneId) values (8,5);
insert into memberphones (memberId,phoneId) values (5,1);
insert into memberphones (memberId,phoneId) values (8,6);
insert into memberphones (memberId,phoneId) values (2,1);
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
