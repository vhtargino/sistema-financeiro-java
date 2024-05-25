package fin.service.api.domain.pessoa;

import fin.service.api.domain.endereco.DadosEndereco;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoPessoa(
        @NotNull Long id,
        String nome,
        DadosEndereco endereco) {
}
