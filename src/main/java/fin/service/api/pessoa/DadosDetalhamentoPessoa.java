package fin.service.api.pessoa;

import fin.service.api.endereco.Endereco;

public record DadosDetalhamentoPessoa(Long id,
                                      String nome,
                                      Endereco endereco) {

    public DadosDetalhamentoPessoa(Pessoa pessoa) {
        this(pessoa. getId(),
             pessoa.getNome(),
             pessoa.getEndereco());
    }
}
