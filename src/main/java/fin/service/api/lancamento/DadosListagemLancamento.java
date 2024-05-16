package fin.service.api.lancamento;

import fin.service.api.pessoa.Pessoa;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

public record DadosListagemLancamento(Long id,
                                          String descricao,
                                          LocalDate dataVencimento,
                                          LocalDate dataPagamento,
                                          BigDecimal valor,
                                          String observacao,
                                          TipoLancamento tipo) {

    public DadosListagemLancamento(Lancamento lancamento) {
        this(lancamento. getId(),
                lancamento.getDescricao(),
                lancamento.getDataVencimento(),
                lancamento.getDataPagamento(),
                lancamento.getValor(),
                lancamento.getObservacao(),
                lancamento.getTipo());
    }
}
