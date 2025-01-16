package emy.api.gerenciamento_clientes.repository;

import emy.api.gerenciamento_clientes.entity.TipoTransacao;
import emy.api.gerenciamento_clientes.entity.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
    List<Transacao> findByContaIdAndDataHoraBetween(Long contaId, LocalDateTime dataInicial, LocalDateTime dataFinal);

    List<Transacao> findByContaIdAndTipo(Long contaId, TipoTransacao tipo);
}
