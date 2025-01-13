package emy.api.gerenciamento_clientes.dto;

import emy.api.gerenciamento_clientes.entity.TipoTransacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransacaoDTO {
    private BigDecimal valor;
    private String descricao;
    private LocalDateTime dataHora;
    private TipoTransacao tipo;
}