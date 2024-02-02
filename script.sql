create table member (
	m_id varchar(100) primary key,
	
	pw varchar(500) null,
	name varchar(50) not null,
	address varchar(1000) not null,
	addressDetail varchar(3000) not null,
	tel varchar(15) not null,
	email varchar(50) not null,
	regdate timestamp not null default current_timestamp,
    birth VARCHAR(50) not null,
	deletedate timestamp null
)


create table paper (
	pcc_seq serial primary key,
	
	bgcolor VARCHAR(50) DEFAULT '#FFFFFF',
	pccolor VARCHAR(50) DEFAULT '#FFFFFF',
	pcbcolor VARCHAR(50) DEFAULT '#000000',
	pcborderpx VARCHAR(50) not null,
	pcbradiuspx VARCHAR(50) not null,
	title VARCHAR(5000) not null,
	code int unique not null,
	regdate timestamp not null default current_timestamp,
	is_deleted varchar(1) not null default 'N',
	
	m_id varchar(100) not null,	
	constraint fk_mId foreign key(m_id) references member(m_id)
);


create table postcard (
	pc_seq serial primary key,
	
	content varchar(3000) not null,
	regDate timestamp not null default current_timestamp,
	textcolor VARCHAR(50) DEFAULT '#FFFFFF',
	is_deleted varchar(1) not null default 'N',
	
	pcc_seq int not null,
	constraint fk_pcc_seq foreign key(pcc_seq) references paper(pcc_seq)
);


create table TreasureBox (
	b_seq	serial primary key,
	
	title	varchar(300)	not null,
	content	varchar(5000)	not null,
	regdate	timestamp	default current_timestamp,
	hit	int	default 0,
	is_deleted varchar(1) not null default 'N',
	
	m_id	varchar(100)	not null,
	constraint fk_m_id foreign key(m_id) references member(m_id)
);

create table TbPic (
	pic_url		varchar(500)	primary key,
	pic_name  varchar(500)	not null,
	
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
	constraint fk_m_id foreign key(m_id) references member(m_id),
	
	
	is_deleted varchar(1) not null default 'N'
	
);

--
--create table TbcPic (
--	pic_name	varchar(100) primary key,
--	
--	c_seq	int	not null,
--	constraint fk_c_seq foreign key(c_seq) references TbComment(c_seq)
--);
--
--
--create table TbcReply (
--	r_seq	serial primary key,
--	
--	content	varchar(5000)	not null,
--	regdate	timestamp	not null default current_timestamp,	
--	
--	c_seq	int	not null,
--	constraint fk_c_seq foreign key(c_seq) references TbComment(c_seq),	
--	
--	m_id	varchar(100)	not null,
--	constraint fk_m_id foreign key(m_id) references member(m_id),
--	
--	
--	is_deleted varchar(1) not null default 'N'
--);


select * from member;
select * from paper;
select * from postcard;
select * from treasurebox;
select * from tbpic;
select * from tbcomment;
--select * from tbcreply;


ALTER TABLE public.paper ADD bgcolor VARCHAR(50) DEFAULT '#FFFFFF';
ALTER TABLE public.paper ADD regdate timestamp not null default current_timestamp;

insert into postcard (content, pcc_seq, textcolor) values ('수영잉~~~~ 보구 싶엉 ㅠ', 1, '#FB6E52');
insert into postcard (content, pcc_seq, textcolor) values ('내가 항상 고마워하는 거 알지? 너가 최고야!', 1, '#000000');
update postcard set isdelete='N' where pc_seq=2;
update paper set isdelete='N' where pcc_seq=5;
update postcard  set isdelete='N' where pcc_seq=5;
select count(*) from postcard where pcc_seq=5;

select count(1) as cnt from paper;


INSERT INTO members (m_id, pw, name, address, addressdetail, tel, email, regdate, birth)
VALUES
    ('user001', 'p@ssw0rd', '사과Apple', '서울 강남구 강남대로 123', 'A동 456호', '01012345678', 'user001@gmail.com', '2024-02-02 22:35:12.342', '2000-05-15'),
    ('member42', 'secure567', 'bookworm', '서울 마포구 홍대로 789', '3층 302호', '01098765432', 'member42@hotmail.com', '2024-02-02 22:35:12.342', '1995-09-22'),
    ('gaming_pro1', 'gamer345', '게임마니아', '서울 종로구 인사동 456', 'B동 789호', '01056781234', 'gaming_pro1@yahoo.com', '2024-02-02 22:35:12.342', '1993-11-03'),
    ('music_lover23', 'pass321', 'MusicManiac', '서울 강동구 천호대로 321', 'C동 101호', '01023456789', 'music_lover23@gmail.com', '2024-02-02 22:35:12.342', '1997-08-11'),
    ('wanderlust88', 'secret999', '여행Bug', '서울 중구 남대문로 987', 'D동 205호', '01087654321', 'wanderlust88@hotmail.com', '2024-02-02 22:35:12.342', '1990-03-28'),
    ('fitness_fanatic7', 'strongpass7', '운동러', '서울 송파구 올림픽로 654', 'E동 307호', '01054321678', 'fitness_fanatic7@gmail.com', '2024-02-02 22:35:12.342', '1994-06-19'),
    ('movie_buff16', 'cinema123', 'MovieManiac', '서울 강서구 공항대로 123', 'F동 503호', '01098761234', 'movie_buff16@yahoo.com', '2024-02-02 22:35:12.342', '1991-02-14'),
    ('foodie99', 'delicious12', 'FoodLover', '서울 양천구 목동로 789', 'G동 104호', '01032167890', 'foodie99@hotmail.com', '2024-02-02 22:35:12.342', '1996-07-09'),
    ('pet_lover55', 'pet123', '애완동물마니아', '서울 강북구 삼양로 234', 'H동 208호', '01065432109', 'pet_lover55@gmail.com', '2024-02-02 22:35:12.342', '1989-10-01'),
    ('nature_hiker3', 'hiking456', '자연을사랑하는자', '서울 강남구 언주로 567', 'I동 601호', '01078901234', 'nature_hiker3@hotmail.com', '2024-02-02 22:35:12.342', '1992-04-25'),
    ('tech_guru21', 'secureTech!', 'TechEnthusiast', '서울 서초구 서초대로 345', 'J동 407호', '01043219876', 'tech_guru21@yahoo.com', '2024-02-02 22:35:12.342', '1999-12-30'),
    ('fashionista12', 'fashion123', '패션Lover', '서울 강동구 강동대로 678', 'K동 210호', '01076543210', 'fashionista12@gmail.com', '2024-02-02 22:35:12.342', '1988-06-05')