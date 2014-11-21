
CREATE TABLE comentario (
                id serial NOT NULL,
                texto VARCHAR(255),
                id_local integer,
		id_usuario integer,
		constraint fk_localc foreign key(id_local) references local(id),
		constraint fk_localu foreign key(id_usuario) references usuario(idUsuario)
					
);


ALTER TABLE public.comentario ADD CONSTRAINT comentario_pkey
PRIMARY KEY (id);

CREATE TABLE public.descricaolocal (
                id NUMERIC(20) NOT NULL,
                banheiros INTEGER NOT NULL,
                emailcontato VARCHAR(255) NULL,
                garagem BIT(1) NOT NULL,
                preco DOUBLE PRECISION NOT NULL,
                qtdapartamentos INTEGER NOT NULL,
                qtdquartos INTEGER NOT NULL,
                quintal BIT(1) NOT NULL,
                telefonecontato VARCHAR(255) NULL,
                tipo VARCHAR(255) NULL
);

ALTER TABLE public.descricaolocal ADD CONSTRAINT descricaolocal_pkey
PRIMARY KEY (id);

CREATE TABLE public.tb_local (
                id NUMERIC(19) NOT NULL,
                banheiros INTEGER NOT NULL,
                emailcontato VARCHAR(255) NULL,
                endereco VARCHAR(255) NULL,
                garagem BIT(1) NOT NULL,
                latitude VARCHAR(255) NULL,
                longitude VARCHAR(255) NULL,
                preco DOUBLE PRECISION NOT NULL,
                qtdapartamentos INTEGER NOT NULL,
                qtdquartos INTEGER NOT NULL,
                quintal BIT(1) NOT NULL,
                telefonecontato VARCHAR(255) NULL,
                tipo VARCHAR(255) NULL,
                id_descricao NUMERIC(20) NULL,
                id_usuario NUMERIC(20) NULL
);

ALTER TABLE public.tb_local ADD CONSTRAINT local_pkey
PRIMARY KEY (id);

CREATE TABLE public.usuario (
                idusuario NUMERIC(19) NOT NULL,
                cidade VARCHAR(255) NULL,
                email VARCHAR(255) NULL,
                endereco VARCHAR(255) NULL,
                nome VARCHAR(255) NULL,
                senha VARCHAR(255) NULL,
                senhatemporaria VARCHAR(255) NULL
);

ALTER TABLE public.usuario ADD CONSTRAINT usuario_pkey
PRIMARY KEY (idusuario);

ALTER TABLE public.tb_local ADD CONSTRAINT fk_s381i32wpr7ckbjf96753p4h3
FOREIGN KEY (id_descricao)
REFERENCES public.descricaolocal (id);

ALTER TABLE public.comentario ADD CONSTRAINT fk_j2ug3fws66jcjayunysn5cj8t
FOREIGN KEY (id_local)
REFERENCES public.tb_local (id);

ALTER TABLE public.tb_local ADD CONSTRAINT fk_jvhc6yj1sy6bsgoof7txll1qa
FOREIGN KEY (id_usuario)
REFERENCES public.usuario (idusuario);