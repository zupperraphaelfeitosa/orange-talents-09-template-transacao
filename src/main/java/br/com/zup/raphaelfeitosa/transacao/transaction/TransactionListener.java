package br.com.zup.raphaelfeitosa.transacao.transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TransactionListener {
    private final Logger logger = LoggerFactory.getLogger(TransactionListener.class);

    @Autowired
    private TransactionRepository transactionRepository;

    @KafkaListener(topics = "${spring.kafka.topic.transactions}")
    public void ouvir(TransactionMessage transactionMessage) {
        logger.info("Nova transação recebida, id: {}", transactionMessage.getId());
        transactionRepository.save(transactionMessage.toTransaction());
    }
}
