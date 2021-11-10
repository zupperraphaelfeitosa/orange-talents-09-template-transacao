package br.com.zup.raphaelfeitosa.transacao.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class TransactionMessage {

    private String id;
    private BigDecimal valor;
    private Map<String, String> estabelecimento = new HashMap<>();
    private Map<String, String> cartao = new HashMap<>();
    private LocalDateTime efetivadaEm;

    @Deprecated
    public TransactionMessage() {
    }

    public TransactionMessage(String id, BigDecimal valor, Map<String, String> estabelecimento, Map<String, String> cartao, LocalDateTime efetivadaEm) {
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

    public TransactionModel toTransaction() {
        return new TransactionModel(id, valor, estabelecimento, cartao, efetivadaEm);
    }
}
