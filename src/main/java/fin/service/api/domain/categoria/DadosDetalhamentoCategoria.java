package fin.service.api.domain.categoria;

public record DadosDetalhamentoCategoria(Long id, String nome) {

    public DadosDetalhamentoCategoria(Categoria categoria) {
        this(categoria.getId(), categoria.getNome());
    }
}
