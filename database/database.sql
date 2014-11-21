

CREATE TABLE descricaolocal (
                idDescricao serial NOT NULL,
                banheiros INTEGER ,
                emailcontato VARCHAR(255),
                garagem boolean,
                preco DOUBLE PRECISION,
                qtdapartamentos INTEGER,
                qtdquartos INTEGER,
                quintal boolean,
                telefonecontato VARCHAR(255),
                tipo VARCHAR(255)
);

ALTER TABLE descricaolocal ADD CONSTRAINT descricaolocal_pkey
PRIMARY KEY (idDescricao);

CREATE TABLE local (
                idLocal serial,
                endereco VARCHAR(255),
                latitude VARCHAR(255),
                longitude VARCHAR(255),
                id_descricao INTEGER,
                id_usuario INTEGER
);

ALTER TABLE local ADD CONSTRAINT local_pkey
PRIMARY KEY (idLocal);

CREATE TABLE usuario (
                idusuario serial NOT NULL,
                cidade VARCHAR(255),
                email VARCHAR(255),
                endereco VARCHAR(255),
                nome VARCHAR(255),
                senha VARCHAR(255),
                senhatemporaria VARCHAR(255)
);

ALTER TABLE usuario ADD CONSTRAINT usuario_pkey
PRIMARY KEY (idusuario);

CREATE TABLE comentario (
		idcomentario integer,
                texto VARCHAR(255),
                id_local integer,
		id_usuario integer,
		constraint fk_localc foreign key(id_local) references local(idLocal),
		constraint fk_localu foreign key(id_usuario) references usuario(idUsuario) 
);


ALTER TABLE comentario ADD CONSTRAINT comentario_pkey
PRIMARY KEY (idComentario);



ALTER TABLE local ADD CONSTRAINT fk_descricao
FOREIGN KEY (idlocal)
REFERENCES public.descricaolocal (idDescricao);

ALTER TABLE comentario ADD CONSTRAINT fk_comentario
FOREIGN KEY (id_local)
REFERENCES public.local (idLocal);

ALTER TABLE local ADD CONSTRAINT fk_local
FOREIGN KEY (id_usuario)
REFERENCES public.usuario (idusuario);

create or replace function deletarDependencias() returns trigger as $$

declare
begin

	delete from Comentario where id_local = old.idLocal;
	return old;
end

$$ language 'plpgsql';

create or replace function deletarDependenciasAfter() returns trigger as $$

declare
begin
	
	delete from DescricaoLocal where idDescricao = old.id_descricao;
	return old;
end

$$ language 'plpgsql';


create trigger triggerDependencias
before delete on local 
for each row execute procedure deletarDependencias();

create trigger triggerDependenciasAfter
after delete on local 
for each row execute procedure deletarDependenciasAfter();
