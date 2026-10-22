package com.suporte.chamados.repository;

import com.suporte.chamados.model.Chamado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * CAMADA REPOSITORY: acesso ao banco de dados.
 * Ao estender JpaRepository, o Spring gera em tempo de execucao
 * save, findById, findAll, deleteById, existsById etc.
 */
@Repository
public interface ChamadoRepository extends JpaRepository<Chamado, Long> {
}
