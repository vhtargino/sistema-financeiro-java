package fin.service.api.pessoa;

import fin.service.api.endereco.DadosEndereco;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoPessoa(
        @NotNull Long id,
        String nome,
        DadosEndereco endereco) {
}
