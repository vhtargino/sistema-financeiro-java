package fin.service.api.domain.categoria;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "categoria")
@Entity(name = "Categoria")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")

public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private String nome;

    private boolean ativo;

    public Categoria(DadosCadastroCategoria dados) {
        this.ativo = true;
        this.nome = dados.nome();
    }

    public void atualizarInformacoes(DadosAtualizacaoCategoria dados) {
        if(dados.nome() != null) {
            this.nome = dados.nome();
        }
    }

    public void excluir() {
        this.ativo = false;
    }
}
