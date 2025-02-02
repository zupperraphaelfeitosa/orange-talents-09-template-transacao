package br.com.zup.raphaelfeitosa.transacao.transaction;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class TransactionMessage {

    private String id;
    private BigDecimal valor;
    private Map<String, String> estabelecimento = new HashMap<>();
    private Map<String, String> cartao = new HashMap<>();
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime efetivadaEm;

    @Deprecated
    public TransactionMessage() {
    }

    public TransactionMessage(TransactionModel transactionModel) {
        this.id = transactionModel.getId();
        this.valor = transactionModel.getValor();
        this.estabelecimento = transactionModel.getEstabelecimento();
        this.cartao = transactionModel.getCartao();
        this.efetivadaEm = transactionModel.getEfetivadaEm();
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

    public static Page<TransactionMessage> converter(Page<TransactionModel> transactions) {
        return transactions.map(TransactionMessage::new);
    }
}
