package br.com.zup.raphaelfeitosa.transacao.transaction;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class FindRecentPurchasesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    void deveriaRetornaAsDezUltimasTransacoesComSucessoComRetorno200() throws Exception {

        Map<String, String> cartao = new HashMap<>();
        cartao.put("id", "9999-9999-9999-9999");
        cartao.put("email", "jhondoe@gmail.com");

        Map<String, String> estabelecimento = new HashMap<>();
        estabelecimento.put("nome", "Freida Convict");
        estabelecimento.put("endereco", "Suite 521 164 Pablo Ranch, Waldoton, NE 85149-1084");
        estabelecimento.put("cidade", "Mannport");

        List<TransactionModel> getTransactions = new ArrayList<>();

        for (var i = 1; i <= 10; i++) {
            BigDecimal valorRandomico = new BigDecimal(i + Math.random());
            BigDecimal valorFormatado = valorRandomico.setScale(2, RoundingMode.DOWN);

            TransactionModel transactions = new TransactionModel(
                    Integer.toString(i),
                    valorFormatado,
                    estabelecimento,
                    cartao,
                    LocalDateTime.now().minusDays(i)
            );
            transactionRepository.save(transactions);
            getTransactions.add(transactions);
        }

        List<TransactionMessage> transactionMessages =
                getTransactions
                        .stream()
                        .map(TransactionMessage::new)
                        .collect(Collectors.toList());

        String response = mapper.writeValueAsString(transactionMessages);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/cartoes/9999-9999-9999-9999/transacoes"))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .isOk())
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.numberOfElements").value(10))
                .andReturn();
        assertTrue(result.getResponse().getContentAsString().contains(response));
    }

    @Test
    void naoDeveriaRetornaAsDezUltimasTransacoesIdCartaoInvalidoComRetorno404() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/cartoes/invalid-id/transacoes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .isNotFound());
    }
}