-- Table: public.usuario

-- DROP TABLE IF EXISTS public.usuario;

CREATE TABLE IF NOT EXISTS public.usuario
(
    matricula integer NOT NULL DEFAULT 'nextval('usuario_matricula_seq'::regclass)',
    nome character varying(100) COLLATE pg_catalog."default" NOT NULL,
    cpf character varying(20) COLLATE pg_catalog."default" NOT NULL,
    email character varying(100) COLLATE pg_catalog."default" NOT NULL,
    idade integer NOT NULL,
    senha character varying(50) COLLATE pg_catalog."default" NOT NULL,
    telefone character varying(20) COLLATE pg_catalog."default",
    bibliotecario boolean NOT NULL,
    CONSTRAINT usuario_pkey PRIMARY KEY (matricula)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.usuario
    OWNER to qaokqcmt;

-- Table: public.livro

-- DROP TABLE IF EXISTS public.livro;

CREATE TABLE IF NOT EXISTS public.livro
(
    id_livro integer NOT NULL DEFAULT 'nextval('livro_id_livro_seq'::regclass)',
    titulo character varying(100) COLLATE pg_catalog."default" NOT NULL,
    autor character varying(100) COLLATE pg_catalog."default" NOT NULL,
    editora character varying(100) COLLATE pg_catalog."default" NOT NULL,
    npaginas integer NOT NULL,
    quantidade integer NOT NULL,
    CONSTRAINT livro_pkey PRIMARY KEY (id_livro)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.livro
    OWNER to qaokqcmt;

-- Table: public.emprestimo

-- DROP TABLE IF EXISTS public.emprestimo;

CREATE TABLE IF NOT EXISTS public.emprestimo
(
    matricula integer NOT NULL,
    id_livro integer NOT NULL,
    data_inicial date,
    data_final date,
    data_devolucao date,
    status character varying(30) COLLATE pg_catalog."default",
    CONSTRAINT emprestimo_pkey PRIMARY KEY (matricula, id_livro),
    CONSTRAINT emprestimo_id_livro_fkey FOREIGN KEY (id_livro)
        REFERENCES public.livro (id_livro) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT emprestimo_matricula_fkey FOREIGN KEY (matricula)
        REFERENCES public.usuario (matricula) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.emprestimo
    OWNER to qaokqcmt;

-- Table: public.penalidade

-- DROP TABLE IF EXISTS public.penalidade;

CREATE TABLE IF NOT EXISTS public.penalidade
(
    matricula integer NOT NULL,
    fim_penalidade date,
    motivo character varying(300) COLLATE pg_catalog."default",
    CONSTRAINT penalidade_pkey PRIMARY KEY (matricula),
    CONSTRAINT penalidade_matricula_fkey FOREIGN KEY (matricula)
        REFERENCES public.usuario (matricula) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.penalidade
    OWNER to qaokqcmt;