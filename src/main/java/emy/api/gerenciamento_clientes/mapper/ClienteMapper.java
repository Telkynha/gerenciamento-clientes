package emy.api.gerenciamento_clientes.mapper;

import emy.api.gerenciamento_clientes.dto.ClienteRequest;
import emy.api.gerenciamento_clientes.dto.ClienteResponse;
import emy.api.gerenciamento_clientes.entity.Cliente;

public class ClienteMapper {

    public static Cliente toEntity(ClienteRequest request){
        if(request == null){
            return null;
        }

        Cliente cliente = new Cliente();
        cliente.setNome(request.getNome());
        cliente.setEmail(request.getEmail());
        cliente.setSenha(request.getSenha());
        cliente.setCpf(request.getCpf());
        return cliente;
    }

    public static ClienteResponse toResponse(Cliente cliente){
        if(cliente == null){
            return null;
        }

        ClienteResponse.ContaResponse response = null;
        if(cliente.getConta() != null){
            response = new ClienteResponse.ContaResponse(
                    cliente.getConta().getId(),
                    cliente.getConta().getSaldo()
            );
        }

        return new ClienteResponse(
                cliente.getId(),
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getCpf(),
                response
        );
    }
}
