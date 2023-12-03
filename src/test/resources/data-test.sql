DELETE FROM produto;
DELETE FROM marca;
DELETE FROM categoria;

INSERT INTO marca(id, nome, ativo) VALUES (1, 'Nike', true);
INSERT INTO marca(id, nome, ativo) VALUES (2, 'Apple', true);
INSERT INTO marca(id, nome, ativo) VALUES (3, 'Coca-Cola', false);

INSERT INTO categoria(id, nome, descricao, ativo) VALUES (1, 'Esportes', 'Categoria de esportes', false);
INSERT INTO categoria(id, nome, descricao, ativo) VALUES (2, 'Tecnologia', 'Categoria de tecnologia', true);
INSERT INTO categoria(id, nome, descricao, ativo) VALUES (3, 'Bebidas', 'Categoria de bebidas', true);

INSERT INTO produto(id, nome, descricao, preco, categoria_id, marca_id, ativo) VALUES (1, 'Tênis', 'Tênis de corrida', 100.00, 1, 1,  true);
INSERT INTO produto(id, nome, descricao, preco, categoria_id, marca_id, ativo) VALUES (2, 'iPhone', 'iPhone 12', 8000.00, 2, 2,  true);
INSERT INTO produto(id, nome, descricao, preco, categoria_id, marca_id, ativo) VALUES (3, 'Coca-Cola', 'Coca-Cola 2L', 10.00, 3, 3, false);