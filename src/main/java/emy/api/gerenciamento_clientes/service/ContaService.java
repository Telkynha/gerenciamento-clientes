package emy.api.gerenciamento_clientes.service;

import emy.api.gerenciamento_clientes.entity.Conta;
import emy.api.gerenciamento_clientes.exception.ContaNotFoundException;
import emy.api.gerenciamento_clientes.exception.TransacaoIllegalException;
import emy.api.gerenciamento_clientes.repository.ContaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ContaService {

    private final ContaRepository repository;

    public Conta buscarContaPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ContaNotFoundException(id));
    }

    public void atualizarSaldo(Conta conta, BigDecimal valor) {
        BigDecimal saldoAtualizado = conta.getSaldo().add(valor);

        if (saldoAtualizado.compareTo(BigDecimal.ZERO) < 0) {
            throw new TransacaoIllegalException();
        }

        conta.setSaldo(saldoAtualizado);
        repository.save(conta);
    }

    public String imprimirSaldo(Long contaId) {
        Conta conta = buscarContaPorId(contaId);
        return String.format("Saldo da conta: R$ %.2f", conta.getSaldo());
    }
}
