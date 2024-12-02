package fin.service.api.controller;

import fin.service.api.domain.categoria.CategoriaRepository;
import fin.service.api.domain.lancamento.*;
import fin.service.api.domain.pessoa.PessoaRepository;
import fin.service.api.infra.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("lancamentos")
@SecurityRequirement(name = "bearer-key")
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

        boolean categoriaNaoExiste = categoria == null;
        boolean pessoaNaoExiste = pessoa == null;
        ErrorResponse erroCategoria = ErrorResponse.mensagemErroCategoria();
        ErrorResponse erroPessoa = ErrorResponse.mensagemErroPessoa();

        if(categoriaNaoExiste && pessoaNaoExiste) {
            List<ErrorResponse> erros = Arrays.asList(erroCategoria, erroPessoa);

            return ResponseEntity.badRequest().body(erros);
        }

        if(categoriaNaoExiste) {
            return ResponseEntity.badRequest().body(erroCategoria);
        }

        if(pessoaNaoExiste) {
            return ResponseEntity.badRequest().body(erroPessoa);
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

        var dto = new DadosDetalhamentoLancamento(lancamento);

        return ResponseEntity.ok(dto);
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
