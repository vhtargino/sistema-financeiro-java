package fin.service.api.domain.lancamento;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DadosCadastroLancamento(
        @NotBlank String descricao,
        @NotNull @FutureOrPresent LocalDate dataVencimento,
        @NotNull @FutureOrPresent LocalDate dataPagamento,
        @NotNull @Positive BigDecimal valor,
        @Size(max = 200, message = "A observação não pode exceder 200 caracteres.") String observacao,
        @NotNull TipoLancamento tipo,
        @NotNull Long idCategoria,
        @NotNull Long idPessoa) {
}
