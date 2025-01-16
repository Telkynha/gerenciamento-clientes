package emy.api.gerenciamento_clientes.mapper;

import emy.api.gerenciamento_clientes.dto.ContaResponse;
import emy.api.gerenciamento_clientes.dto.TransacaoDTO;
import emy.api.gerenciamento_clientes.entity.Cliente;
import emy.api.gerenciamento_clientes.entity.Conta;

import java.util.List;
import java.util.stream.Collectors;

public class ContaMapper {

    public static ContaResponse toResponse(Conta conta) {
        if (conta == null) {
            return null;
        }

        ContaResponse.Titular titular = null;
        if (conta.getTitular() != null) {
            Cliente cliente = conta.getTitular();
            titular = new ContaResponse.Titular(
                    cliente.getId(),
                    cliente.getNome(),
                    cliente.getEmail()
            );
        }

        List<TransacaoDTO> historico = conta.getHistorico().stream()
                .map(transacao -> new TransacaoDTO(
                        transacao.getConta().getId(),
                        transacao.getValor(),
                        transacao.getDescricao(),
                        transacao.getDataHora(),
                        transacao.getTipo()
                ))
                .collect(Collectors.toList());

        return new ContaResponse(
                conta.getId(),
                conta.getSaldo(),
                titular,
                historico
        );
    }
}