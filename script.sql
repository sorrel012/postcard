create table member (
	m_seq serial primary key,
	id varchar(100) not null,
	pw varchar(500) not null,
	name varchar(50) not null,
	address varchar(1000) not null,
	addressDetail varchar(3000) not null,
	email varchar(500) not null
	regDate timestamp not null default current_timestamp
);

create table paper (
	pcc_seq serial primary key,
	
	m_seq int not null,	
	constraint fk_m_seq foreign key(m_seq) references member(m_seq)
);
ALTER TABLE public.paper ADD bgcolor VARCHAR(50) DEFAULT '#FFFFFF';
ALTER TABLE public.paper ADD pccolor VARCHAR(50) DEFAULT '#FFFFFF';
ALTER TABLE public.paper ADD pcbcolor VARCHAR(50) DEFAULT '#000000';
ALTER TABLE public.paper ADD pcborderpx VARCHAR(50) not null;
ALTER TABLE public.paper ADD pcbradiuspx VARCHAR(50) not null;
ALTER TABLE public.paper ADD title VARCHAR(5000) not null;
ALTER TABLE public.paper ADD code int unique not null; 
ALTER TABLE public.paper ADD regdate timestamp not null default current_timestamp;
ALTER TABLE public.paper ADD isDelete varchar(1) not null default 'N';

create table postcard (
	pc_seq serial not null,
	content varchar(3000) not null,
	regDate timestamp not null default current_timestamp,
	
	pcc_seq int not null,
	constraint fk_pcc_seq foreign key(pcc_seq) references paper(pcc_seq)
);
ALTER TABLE public.postcard ADD textcolor VARCHAR(50) DEFAULT '#FFFFFF';
ALTER TABLE public.postcard ADD isDelete varchar(1) not null default 'N';

insert into postcard (content, pcc_seq, textcolor) values ('수영잉~~~~ 보구 싶엉 ㅠ', 1, '#FB6E52');
insert into postcard (content, pcc_seq, textcolor) values ('내가 항상 고마워하는 거 알지? 너가 최고야!', 1, '#000000');
update postcard set isdelete='N' where pc_seq=2;
update paper set isdelete='N' where pcc_seq=5;
update postcard  set isdelete='N' where pcc_seq=5;
select count(*) from postcard where pcc_seq=5;

select * from member;
select * from paper;
select * from postcard;

select count(1) as cnt from paper;