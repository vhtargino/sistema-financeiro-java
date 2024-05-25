package fin.service.api.controller;

import fin.service.api.domain.categoria.CategoriaRepository;
import fin.service.api.domain.lancamento.*;
import fin.service.api.domain.pessoa.PessoaRepository;
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
@RequestMapping("lancamentos")
public class LancamentoController {

    @Autowired
    private LancamentoRepository repository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroLancamento dados, UriComponentsBuilder uriBuilder) {
        var categoria = categoriaRepository.findByIdAndAtivoTrue(dados.idCategoria());
                //.orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada"));

        var pessoa = pessoaRepository.findByIdAndAtivoTrue(dados.idPessoa());

        if(categoria == null || !categoria.isAtivo()) {
            return ResponseEntity.badRequest().body("Categoria não encontrada.");
        }

        if(pessoa == null || !pessoa.isAtivo()) {
            return ResponseEntity.badRequest().body("Pessoa não encontrada.");
        }

        var lancamento = new Lancamento(dados);
        lancamento.setCategoria(categoria);
        lancamento.setPessoa(pessoa);

        repository.save(lancamento);

        var uri = uriBuilder.path("/lancamentos/{id}").buildAndExpand(lancamento.getId()).toUri();
        var dto = new DadosDetalhamentoLancamento(lancamento);

        return ResponseEntity.created(uri).body(dto);
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemLancamento>> listar(@PageableDefault(size=10, sort={"id"}) Pageable paginacao) {
        var page = repository.findByAtivoTrue(paginacao).map(DadosListagemLancamento::new);

        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoLancamento dados) {
        var lancamento = repository.getReferenceById(dados.id());
        lancamento.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosDetalhamentoLancamento(lancamento));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {
        var lancamento = repository.getReferenceById(id);
        lancamento.excluir();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        var lancamento = repository.getReferenceById(id);

        var dto = new DadosDetalhamentoLancamento(lancamento);

        return ResponseEntity.ok(dto);
    }
}
