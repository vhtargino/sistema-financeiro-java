package fin.service.api.controller;

import fin.service.api.domain.categoria.*;
import fin.service.api.domain.lancamento.DadosDetalhamentoLancamento;
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

@RestController
@RequestMapping("categorias")
@SecurityRequirement(name = "bearer-key")
public class CategoriaController {

    @Autowired
    private CategoriaRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroCategoria dados, UriComponentsBuilder uriBuilder) {
        var categoria = new Categoria(dados);
        repository.save(categoria);

        var uri = uriBuilder.path("categorias/{id}").buildAndExpand(categoria.getId()).toUri();
        var dto = new DadosDetalhamentoCategoria(categoria);

        return ResponseEntity.created(uri).body(dto);
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemCategoria>> listar(@PageableDefault(size=10, sort={"nome"}) Pageable paginacao) {
        var page = repository.findByAtivoTrue(paginacao).map(DadosListagemCategoria::new);

        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoCategoria dados) {
        var categoria = repository.getReferenceById(dados.id());
        categoria.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosDetalhamentoCategoria(categoria));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {
        var categoria = repository.getReferenceById(id);
        categoria.excluir();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        var categoria = repository.getReferenceById(id);

        var dto = new DadosDetalhamentoCategoria(categoria);

        return ResponseEntity.ok(dto);
    }
}
