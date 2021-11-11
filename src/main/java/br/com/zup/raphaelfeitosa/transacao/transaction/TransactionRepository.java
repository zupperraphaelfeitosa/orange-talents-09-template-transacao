package br.com.zup.raphaelfeitosa.transacao.transaction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<TransactionModel, Long> {
    Page<TransactionModel> findByCartao(String idCartao, Pageable pageable);
}
