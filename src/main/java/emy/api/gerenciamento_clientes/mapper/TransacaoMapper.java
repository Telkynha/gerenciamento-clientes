package emy.api.gerenciamento_clientes.mapper;

import emy.api.gerenciamento_clientes.dto.TransacaoDTO;
import emy.api.gerenciamento_clientes.entity.Transacao;

public class TransacaoMapper {

    public static Transacao toEntity(TransacaoDTO dto){
        if(dto == null){
            return null;
        }

        Transacao transacao = new Transacao();
        transacao.setValor(dto.getValor());
        transacao.setDescricao(dto.getDescricao());
        transacao.setDataHora(dto.getDataHora());
        transacao.setTipo(dto.getTipo());
        return transacao;
    }

    public static TransacaoDTO toResponse(Transacao transacao){
        if(transacao == null){
            return null;
        }

        return new TransacaoDTO(
                transacao.getValor(),
                transacao.getDescricao(),
                transacao.getDataHora(),
                transacao.getTipo()
        );
    }
}
