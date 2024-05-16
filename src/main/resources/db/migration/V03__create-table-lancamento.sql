CREATE TABLE lancamento (

id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
descricao VARCHAR(50) NOT NULL,
data_vencimento DATE NOT NULL,
data_pagamento DATE NOT NULL,
valor DECIMAL(10,2) NOT NULL,
observacao VARCHAR(100),
tipo VARCHAR(20) NOT NULL,
id_categoria BIGINT(20) NOT NULL,
id_pessoa BIGINT(20) NOT NULL,
ativo TINYINT DEFAULT 1,
FOREIGN KEY (id_categoria) REFERENCES categoria(id),
FOREIGN KEY (id_pessoa) REFERENCES pessoa(id)

);