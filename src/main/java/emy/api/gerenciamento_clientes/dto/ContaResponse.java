package emy.api.gerenciamento_clientes.dto;

import emy.api.gerenciamento_clientes.entity.TipoTransacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContaResponse {
    private Long id;
    private BigDecimal saldo;
    private Titular titular;
    private List<TransacaoResponse> historico;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Titular{
        private Long id;
        private String nome;
        private String email;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TransacaoResponse{
        private Long id;
        private BigDecimal valor;
        private String descricao;
        private LocalDateTime dataHora;
        private TipoTransacao tipo;
    }
}