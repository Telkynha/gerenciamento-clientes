package emy.api.gerenciamento_clientes.repository;

import emy.api.gerenciamento_clientes.entity.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {}
