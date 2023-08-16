create table member (
	m_seq serial primary key,
	id varchar(100) not null,
	pw varchar(500) not null,
	name varchar(50) not null,
	tel varchar(15) not null,
	regDate timestamp not null default current_timestamp
);


create table pcCode (
	pcc_seq serial primary key,
	
	m_seq int not null,	
	constraint fk_m_seq foreign key(m_seq) references member(m_seq)
);

create table postcard (
	pc_seq serial not null,
	content varchar(3000) not null,
	regDate timestamp not null default current_timestamp,
	
	pcc_seq int not null,
	constraint fk_pcc_seq foreign key(pcc_seq) references pcCode(pcc_seq)
);