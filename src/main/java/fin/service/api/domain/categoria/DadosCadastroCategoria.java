package fin.service.api.domain.categoria;

import jakarta.validation.constraints.NotBlank;

public record DadosCadastroCategoria(@NotBlank String nome) {
}
