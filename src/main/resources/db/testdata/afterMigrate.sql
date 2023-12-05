DELETE FROM produto;
DELETE FROM marca;
DELETE FROM categoria;

alter table produto auto_increment = 1;

INSERT INTO marca(id, nome, ativo) VALUES (1, 'Nike', true);
INSERT INTO marca(id, nome, ativo) VALUES (2, 'Apple', true);
INSERT INTO marca(id, nome, ativo) VALUES (3, 'Coca-Cola', true);

INSERT INTO categoria(id, nome, descricao, ativo) VALUES (1, 'Esportes', 'Categoria de esportes', true);
INSERT INTO categoria(id, nome, descricao, ativo) VALUES (2, 'Tecnologia', 'Categoria de tecnologia', true);
INSERT INTO categoria(id, nome, descricao, ativo) VALUES (3, 'Bebidas', 'Categoria de bebidas', true);