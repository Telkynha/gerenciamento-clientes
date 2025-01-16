package emy.api.gerenciamento_clientes.mapper;

import emy.api.gerenciamento_clientes.dto.TransacaoDTO;
import emy.api.gerenciamento_clientes.entity.Transacao;
import emy.api.gerenciamento_clientes.entity.Conta;

public class TransacaoMapper {

    public static Transacao toEntity(TransacaoDTO request) {
        if (request == null) {
            return null;
        }

        Transacao transacao = new Transacao();
        Conta conta = new Conta();
        conta.setId(request.getContaId());
        transacao.setConta(conta);
        transacao.setValor(request.getValor());
        transacao.setDescricao(request.getDescricao());
        transacao.setDataHora(request.getDataHora());
        transacao.setTipo(request.getTipo());
        return transacao;
    }

    public static TransacaoDTO toResponse(Transacao transacao) {
        if (transacao == null) {
            return null;
        }

        return new TransacaoDTO(
                transacao.getConta().getId(),
                transacao.getValor(),
                transacao.getDescricao(),
                transacao.getDataHora(),
                transacao.getTipo()
        );
    }
}
