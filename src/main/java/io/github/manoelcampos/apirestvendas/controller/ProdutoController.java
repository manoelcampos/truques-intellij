package io.github.manoelcampos.apirestvendas.controller;

import io.github.manoelcampos.apirestvendas.model.Produto;
import io.github.manoelcampos.apirestvendas.repository.ProdutoRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * @author Manoel Campos
 */
@RestController
@RequestMapping("/produto")
@AllArgsConstructor
public class ProdutoController {
    private final ProdutoRepository repository;

    @PutMapping("/{id}")
    public void update(@Valid @PathVariable long id, @RequestBody Produto produto){
        /*
         * TODO: Usar condição afirmativa para simplificidade e maior clareza.
         * Usar refatoração para inverter if.
         * O código do IF fica menor e não temos que usar negação.
         */
        if(id != produto.getId())
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "O id indicado na URL não corresponde com o ID do objeto a ser alterado");

        repository.save(produto);
    }

    @PostMapping
    public long insert(@Valid @RequestBody Produto produto){
        return repository.save(produto).getId();
    }

    @GetMapping
    public List<Produto> findAll(){
        return repository.findAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id){
        repository.deleteById(id);
    }

    /**
     * TODO Código do orElseThrow duplicado com o o método {@link #findByEan(String)}
     */
    @GetMapping("/{id}")
    public Produto findById(@PathVariable final long id){
        return repository.findById(id)
                         .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/ean/{ean}")
    public Produto findByEan(@PathVariable final String ean){
        return repository.findByEan(ean)
                         .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
