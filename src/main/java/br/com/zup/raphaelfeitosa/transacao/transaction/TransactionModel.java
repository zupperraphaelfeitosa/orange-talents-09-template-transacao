package br.com.zup.raphaelfeitosa.transacao.transaction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "tb_transacoes")
public class TransactionModel {

    @Id
    private String id;

    private BigDecimal valor;

    @ElementCollection
    @CollectionTable(name = "tb_estabelecimentos",
            joinColumns = @JoinColumn(name = "id_transacao"))
    @MapKeyColumn(name = "tipo")
    private Map<String, String> estabelecimento = new HashMap<>();

    @ElementCollection
    @CollectionTable(name = "tb_cartoes",
            joinColumns = @JoinColumn(name = "id_transacao"))
    @MapKeyColumn(name = "tipo")
    private Map<String, String> cartao = new HashMap<>();

    private LocalDateTime efetivadaEm;

    @Deprecated
    public TransactionModel() {
    }

    public TransactionModel(String id, BigDecimal valor, Map<String, String> estabelecimento, Map<String, String> cartao, LocalDateTime efetivadaEm) {
        this.id = id;
        this.valor = valor;
        this.estabelecimento = estabelecimento;
        this.cartao = cartao;
        this.efetivadaEm = efetivadaEm;
    }

    public String getId() {
        return id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Map<String, String> getEstabelecimento() {
        return estabelecimento;
    }

    public Map<String, String> getCartao() {
        return cartao;
    }

    public LocalDateTime getEfetivadaEm() {
        return efetivadaEm;
    }
}
