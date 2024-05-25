package fin.service.api.domain.pessoa;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import fin.service.api.domain.endereco.DadosEndereco;

public record DadosCadastroPessoa(@NotBlank
                                  String nome,
                                  @NotNull @Valid
                                  DadosEndereco endereco) {
}
