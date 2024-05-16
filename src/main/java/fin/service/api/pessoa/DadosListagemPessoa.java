package fin.service.api.pessoa;

import fin.service.api.endereco.Endereco;

public record DadosListagemPessoa(Long id,
                                  String nome,
                                  Endereco endereco) {

    public DadosListagemPessoa(Pessoa pessoa) {
        this(pessoa.getId(),
             pessoa.getNome(),
             pessoa.getEndereco());
    }
}
