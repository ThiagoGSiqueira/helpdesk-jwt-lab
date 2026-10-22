package com.suporte.chamados.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * CAMADA ENTITY: representa a tabela "chamados" no banco.
 * O JPA/Hibernate mapeia esta classe para a tabela automaticamente.
 */
@Entity
@Table(name = "chamados")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Chamado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID auto-incremento
    private Long id;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(nullable = false, length = 2000)
    private String descricao;

    // EnumType.STRING grava o nome do enum (ex: "ABERTO") e nao a posicao (0, 1, 2),
    // o que evita bugs se a ordem do enum mudar no futuro
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Categoria categoria;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusChamado status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Prioridade prioridade;

    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    // Callback do JPA: roda antes do INSERT. Garante a data de criacao
    // e o status inicial mesmo que o service esqueca de definir.
    @PrePersist
    void aoCriar() {
        if (dataCriacao == null) {
            dataCriacao = LocalDateTime.now();
        }
        if (status == null) {
            status = StatusChamado.ABERTO;
        }
    }
}
