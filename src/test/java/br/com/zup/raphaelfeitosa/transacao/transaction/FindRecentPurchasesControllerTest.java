package br.com.zup.raphaelfeitosa.transacao.transaction;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@Transactional
class FindRecentPurchasesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    void deveriaRetornaAsDezUltimasTransacoesComSucessoComRetorno200() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();

        Map<String, String> cartao = new HashMap<>();
        cartao.put("id", "9999-9999-9999-9999");
        cartao.put("email", "jhondoe@gmail.com");

        Map<String, String> estabelecimento = new HashMap<>();
        estabelecimento.put("nome", "Freida Convict");
        estabelecimento.put("endereco", "Suite 521 164 Pablo Ranch, Waldoton, NE 85149-1084");
        estabelecimento.put("cidade", "Mannport");

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
        }

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/cartoes/9999-9999-9999-9999/transacoes"))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .isOk())
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.numberOfElements").value(10));
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