package io.github.manoelcampos.apirestvendas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @NotBlank @NotNull
    private String titulo;
    private String descricao;

    /**
     * Código de barras em formato <a href="https://pt.wikipedia.org/wiki/EAN-13">EAN 13</a>.
     * TODO Validar usando expressão regular usando anotação {@link Pattern}.
     */
    @NotBlank @NotNull
    private String ean;

    @DecimalMin("0.1")
    private double preco;
    private int estoque;

    @ManyToOne
    @NotNull
    private Marca marca;
}
