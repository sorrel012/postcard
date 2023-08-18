create table member (
	m_seq serial primary key,
	id varchar(100) not null,
	pw varchar(500) not null,
	name varchar(50) not null,
	tel varchar(15) not null,
	regDate timestamp not null default current_timestamp
);


create table paper (
	pcc_seq serial primary key,
	
	m_seq int not null,	
	constraint fk_m_seq foreign key(m_seq) references member(m_seq)
);
ALTER TABLE public.pccode ADD bgcolor VARCHAR(50) DEFAULT '#FFFFFF';
ALTER TABLE public.pccode ADD pccolor VARCHAR(50) DEFAULT '#FFFFFF';
ALTER TABLE public.pccode ADD pcbcolor VARCHAR(50) DEFAULT '#000000';
ALTER TABLE public.pccode ADD pcborder VARCHAR(50) not null;
ALTER TABLE public.pccode ADD pcbradius VARCHAR(50) not null;
ALTER TABLE public.pccode ADD title VARCHAR(5000) not null;

create table postcard (
	pc_seq serial not null,
	content varchar(3000) not null,
	regDate timestamp not null default current_timestamp,
	
	pcc_seq int not null,
	constraint fk_pcc_seq foreign key(pcc_seq) references pcCode(pcc_seq)
);
ALTER TABLE public.postcard ADD textcolor VARCHAR(50) DEFAULT '#FFFFFF';

select * from member;