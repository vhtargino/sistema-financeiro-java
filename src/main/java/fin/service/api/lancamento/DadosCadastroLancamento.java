package fin.service.api.lancamento;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

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
