package emy.api.gerenciamento_clientes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResponse {
    private Long id;
    private String nome;
    private String email;
    private String cpf;
    private ContaResponse conta;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ContaResponse {
        private Long id;
        private BigDecimal saldo;
    }
}
