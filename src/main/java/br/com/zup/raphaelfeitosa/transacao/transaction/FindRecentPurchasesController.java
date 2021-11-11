package br.com.zup.raphaelfeitosa.transacao.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/cartoes")
public class FindRecentPurchasesController {

    @Autowired
    private TransactionRepository transactionRepository;

    @GetMapping("/{idCartao}/transacoes")
    public ResponseEntity<Page<TransactionMessage>> findTransactions(@PathVariable(name = "idCartao") String idCartao,
                                                                     @PageableDefault(sort = "efetivadaEm",
                                                                             page = 0, size = 10,
                                                                             direction = Sort.Direction.DESC)
                                                                             Pageable pageable) {
        Page<TransactionModel> transactions = transactionRepository.findByCartao(idCartao, pageable);
        if (transactions.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id do cartão invalido!");
        }
        return ResponseEntity.ok(TransactionMessage.converter(transactions));
    }
}
