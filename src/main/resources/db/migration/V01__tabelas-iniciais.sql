create table categoria (
     id integer not null auto_increment,
     ativo bit not null,
     descricao varchar(255),
     nome varchar(50),
     primary key (id)
) engine=InnoDB default charset=utf8;

create table marca (
     id integer not null auto_increment,
     ativo bit not null,
     nome varchar(50),
     primary key (id)
) engine=InnoDB default charset=utf8;

create table produto (
    id integer not null auto_increment,
    ativo bit not null,
    data_atualizacao datetime(6),
    data_criacao datetime(6),
    descricao varchar(255),
    nome varchar(50) not null,
    preco decimal(10,2) not null,
    categoria_id integer not null,
    marca_id integer not null,
    primary key (id)
) engine=InnoDB default charset=utf8;

alter table produto add constraint fk_produto_categoria foreign key (categoria_id) references categoria(id);
alter table produto add constraint fk_produto_marca foreign key (marca_id) references marca(id);