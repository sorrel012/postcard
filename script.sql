create table member (
	m_id varchar(100) primary key,
	
	pw varchar(500) null,
	name varchar(50) not null,
	address varchar(1000) not null,
	addressDetail varchar(3000) not null,
	tel varchar(15) not null,
	email varchar(50) not null,
	regdate timestamp not null default current_timestamp,
    birth VARCHAR(50) not null;
	deletedate timestamp not null default current_timestamp,
);


create table paper (
	pcc_seq serial primary key,
	
	bgcolor VARCHAR(50) DEFAULT '#FFFFFF';
	pccolor VARCHAR(50) DEFAULT '#FFFFFF';
	pcbcolor VARCHAR(50) DEFAULT '#000000';
	pcborderpx VARCHAR(50) not null;
	pcbradiuspx VARCHAR(50) not null;
	title VARCHAR(5000) not null;
	code int unique not null; 
	regdate timestamp not null default current_timestamp;
	isDelete varchar(1) not null default 'N';
	
	m_id varchar(100) not null,	
	constraint fk_mId foreign key(m_id) references member(m_id)
);


create table postcard (
	pc_seq serial primary key,
	
	content varchar(3000) not null,
	regDate timestamp not null default current_timestamp,
	textcolor VARCHAR(50) DEFAULT '#FFFFFF';
	isDelete varchar(1) not null default 'N';
	
	pcc_seq int not null,
	constraint fk_pcc_seq foreign key(pcc_seq) references paper(pcc_seq)
);



create table TreasureBox (
	b_seq	serial primary key,
	
	title	varchar(300)	not null,
	content	varchar(5000)	not null,
	regdate	timestamp	not null,
	hit	int	not null,
	
	m_id	varchar(100)	not null,
	constraint fk_m_id foreign key(m_id) references member(m_id)
);


create table TbPic (
	pic_name	varchar(100)	primary key,
	
	b_seq		int	not null,
	constraint fk_b_seq foreign key(b_seq) references TreasureBox(b_seq)
);


create table TbComment (
	c_seq	serial primary key,
	
	content	varchar(1000)	not null,
	regdate	timestamp	not null default current_timestamp,	
	
	b_seq	int	not null,
	constraint fk_b_seq foreign key(b_seq) references TreasureBox(b_seq),
	
	m_id	varchar(100)	not null,	
	constraint fk_m_id foreign key(m_id) references member(m_id)
	
);


create table TbcPic (
	pic_name	varchar(100) primary key,
	
	c_seq	int	not null,
	constraint fk_c_seq foreign key(c_seq) references TbComment(c_seq)
);


create table TbcReply (
	r_seq	serial primary key,
	
	content	varchar(5000)	not null,
	regdate	timestamp	not null default current_timestamp,	
	
	c_seq	int	not null,
	constraint fk_c_seq foreign key(c_seq) references TbComment(c_seq),	
	
	m_id	varchar(100)	not null,
	constraint fk_m_id foreign key(m_id) references member(m_id)
); 


select * from member;
select * from paper;
select * from postcard;






ALTER TABLE public.paper ADD bgcolor VARCHAR(50) DEFAULT '#FFFFFF';
ALTER TABLE public.paper ADD regdate timestamp not null default current_timestamp;

insert into postcard (content, pcc_seq, textcolor) values ('수영잉~~~~ 보구 싶엉 ㅠ', 1, '#FB6E52');
insert into postcard (content, pcc_seq, textcolor) values ('내가 항상 고마워하는 거 알지? 너가 최고야!', 1, '#000000');
update postcard set isdelete='N' where pc_seq=2;
update paper set isdelete='N' where pcc_seq=5;
update postcard  set isdelete='N' where pcc_seq=5;
select count(*) from postcard where pcc_seq=5;

select count(1) as cnt from paper;
