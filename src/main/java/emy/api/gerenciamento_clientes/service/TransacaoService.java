package emy.api.gerenciamento_clientes.service;

import emy.api.gerenciamento_clientes.dto.TransacaoDTO;
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
                ? conta.getSaldo().add(transacao.getValor())
                : conta.getSaldo().subtract(transacao.getValor());

        if (novoSaldo.compareTo(BigDecimal.ZERO) < 0) {
            throw new SaldoInsuficienteException();
        }

        conta.setSaldo(novoSaldo);
        transacao.setConta(conta);

        repository.save(transacao);
        contaService.atualizarSaldo(conta);

        return transacao;
    }

    public Transacao editarTransacao(Long id, TransacaoDTO request) {
        Transacao transacao = repository.findById(id)
                .orElseThrow(TransacaoNotFoundException::new);

        BigDecimal saldoRevertido = transacao.getTipo() == TipoTransacao.RECEITA
                ? transacao.getValor().negate()
                : transacao.getValor();
        contaService.atualizarSaldo(transacao.getConta(), saldoRevertido);

        BigDecimal novoSaldo = transacao.getTipo() == TipoTransacao.RECEITA
                ? request.getValor()
                : request.getValor().negate();
        BigDecimal saldoFinal = transacao.getConta().getSaldo().add(novoSaldo);

        if (saldoFinal.compareTo(BigDecimal.ZERO) < 0) {
            contaService.atualizarSaldo(transacao.getConta(), saldoRevertido.negate());
            throw new SaldoInsuficienteException();
        }

        transacao.setValor(request.getValor());
        transacao.setDescricao(request.getDescricao());
        transacao.setTipo(request.getTipo());
        transacao.setDataHora(request.getDataHora());

        contaService.atualizarSaldo(transacao.getConta(), novoSaldo);

        return repository.save(transacao);
    }

    public void deletarTransacao(Long id) {
        Transacao transacao = repository.findById(id)
                .orElseThrow(TransacaoNotFoundException::new);

        BigDecimal saldoRevertido = transacao.getTipo() == TipoTransacao.RECEITA
                ? transacao.getValor().negate()
                : transacao.getValor();

        contaService.atualizarSaldo(transacao.getConta(), saldoRevertido);
        repository.deleteById(id);
    }

    public Transacao buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(TransacaoNotFoundException::new);
    }

    public List<Transacao> buscarPorPeriodo(Long contaId, LocalDateTime dataInicial, LocalDateTime dataFinal) {
        if (dataInicial.isAfter(dataFinal)) {
            throw new DataIllegalException();
        }

        return repository.findByContaIdAndDataHoraBetween(contaId, dataInicial, dataFinal);
    }

    public String listarTransacoes(Long contaId, LocalDateTime dataInicial, LocalDateTime dataFinal) {
        List<Transacao> historico = repository.findByContaIdAndDataHoraBetween(contaId, dataInicial, dataFinal);

        long receitas = historico.stream().filter(h -> h.getTipo() == TipoTransacao.RECEITA).count();
        long despesas = historico.stream().filter(h -> h.getTipo() == TipoTransacao.DESPESA).count();

        String periodo = String.format("De %s até %s", dataInicial.toLocalDate(), dataFinal.toLocalDate());

        return String.format("%s você teve %d receitas e %d despesas", periodo, receitas, despesas);
    }
}
