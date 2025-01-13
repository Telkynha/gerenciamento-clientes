package emy.api.gerenciamento_clientes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContaResponse {
    private Long id;
    private BigDecimal saldo;
    private Titular titular;
    private List<TransacaoDTO> historico;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Titular{
        private Long id;
        private String nome;
        private String email;
    }
}