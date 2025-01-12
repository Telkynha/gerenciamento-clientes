package emy.api.gerenciamento_clientes.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "remetente_id", nullable = false)
    private Conta remetente;

    @ManyToOne
    @JoinColumn(name = "destinatario_id", nullable = false)
    private Conta destinatario;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(nullable = false)
    private LocalDateTime dataHora;

    @Column(length = 300)
    private String descricao;

}

// Usada para representar tanto as despesas como as receitas