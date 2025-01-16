package emy.api.gerenciamento_clientes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteRequest {
    private String nome;
    private String email;
    private String senha;
    private String cpf;
}