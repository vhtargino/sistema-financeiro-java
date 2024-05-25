package fin.service.api.controller;

import fin.service.api.domain.lancamento.DadosDetalhamentoLancamento;
import fin.service.api.domain.pessoa.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("pessoas")
public class PessoaController {

    @Autowired
    private PessoaRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroPessoa dados, UriComponentsBuilder uriBuilder) {
        var pessoa = new Pessoa(dados);
        repository.save(pessoa);

        var uri = uriBuilder.path("pessoas/{uri}").buildAndExpand(pessoa.getId()).toUri();
        var dto = new DadosDetalhamentoPessoa(pessoa);

        return ResponseEntity.created(uri).body(dto);
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemPessoa>> listar(@PageableDefault(size=10, sort={"nome"}) Pageable paginacao) {
        var page = repository.findByAtivoTrue(paginacao).map(DadosListagemPessoa::new);

        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoPessoa dados) {
        var pessoa = repository.getReferenceById(dados.id());
        pessoa.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosDetalhamentoPessoa(pessoa));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {
        var pessoa = repository.getReferenceById(id);
        pessoa.excluir();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        var pessoa = repository.getReferenceById(id);

        var dto = new DadosDetalhamentoPessoa(pessoa);

        return ResponseEntity.ok(dto);
    }
}
