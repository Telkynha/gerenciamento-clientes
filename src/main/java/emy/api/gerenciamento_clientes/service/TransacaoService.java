package emy.api.gerenciamento_clientes.service;

import emy.api.gerenciamento_clientes.entity.Conta;
import emy.api.gerenciamento_clientes.entity.TipoTransacao;
import emy.api.gerenciamento_clientes.entity.Transacao;
import emy.api.gerenciamento_clientes.exception.DataIllegalException;
import emy.api.gerenciamento_clientes.exception.SaldoInsuficienteException;
import emy.api.gerenciamento_clientes.exception.TransacaoIllegalException;
import emy.api.gerenciamento_clientes.exception.TransacaoNotFoundException;
import emy.api.gerenciamento_clientes.repository.TransacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransacaoService {

    private final TransacaoRepository repository;
    private final ContaService contaService;

    public Transacao adicionarTransacao(Transacao transacao) {
        Conta conta = contaService.buscarContaPorId(transacao.getConta().getId());

        if (transacao.getValor().compareTo(BigDecimal.ZERO) < 0) {
            throw new TransacaoIllegalException("O valor da transação deve ser positivo");
        }

        BigDecimal novoSaldo = transacao.getTipo() == TipoTransacao.RECEITA
                ? transacao.getValor()
                : transacao.getValor().negate();

        if (novoSaldo.compareTo(BigDecimal.ZERO) < 0) {
            throw new SaldoInsuficienteException();
        }

        contaService.atualizarSaldo(conta, novoSaldo);
        return repository.save(transacao);
    }

    public void deletarTransacao(Long id) {
        Transacao transacao = repository.findById(id)
                .orElseThrow(() -> new TransacaoNotFoundException());

        // Reverte o valor no saldo da conta
        BigDecimal saldoRevertido = transacao.getTipo() == TipoTransacao.RECEITA
                ? transacao.getValor().negate()
                : transacao.getValor();

        contaService.atualizarSaldo(transacao.getConta(), saldoRevertido);
        repository.deleteById(id);
    }

    public Transacao buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new TransacaoNotFoundException());
    }

    public List<Transacao> buscarPorPeriodo(Long contaId, LocalDateTime dataInicial, LocalDateTime dataFinal) {
        if (dataInicial.isAfter(dataFinal)) {
            throw new DataIllegalException();
        }

        return repository.findByContaIdAndDataHoraBetween(contaId, dataInicial, dataFinal);
    }
}
