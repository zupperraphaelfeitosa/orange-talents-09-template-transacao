package br.com.zup.raphaelfeitosa.transacao.transaction;

import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<TransactionModel, Long> {
}
