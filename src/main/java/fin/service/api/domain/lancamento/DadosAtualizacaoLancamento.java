package fin.service.api.domain.lancamento;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

public record DadosAtualizacaoLancamento(
        @NotNull Long id,
        String descricao,
        @NotNull LocalDate dataVencimento,
        @NotNull LocalDate dataPagamento,
        @Positive BigDecimal valor,
        @Size(max = 200, message = "A observação não pode exceder 200 caracteres.") String observacao) {
}
