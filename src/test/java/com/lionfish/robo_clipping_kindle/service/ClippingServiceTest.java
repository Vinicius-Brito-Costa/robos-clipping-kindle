package com.lionfish.robo_clipping_kindle.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ClippingServiceTest {

    String clippingSample = "Trabalhe 4 horas por semana (Timothy Ferriss)\n" +
            "- Seu destaque na página 15 | posição 225-229 | Adicionado: segunda-feira, 24 de janeiro de 2022 22:23:54\n" +
            "\n" +
            "Suas decisões se alterariam se a aposentadoria não fosse uma opção? Que tal se você pudesse ter uma miniaposentadoria para experimentar o seu plano de vida adiada antes de trabalhar 40 anos em favor dele? É realmente necessário trabalhar como um escravo para viver como um milionário?\n" +
            "==========\n" +
            "\uFEFFTrabalhe 4 horas por semana (Timothy Ferriss)\n" +
            "- Seu destaque na página 28 | posição 421-425 | Adicionado: terça-feira, 25 de janeiro de 2022 06:39:45\n" +
            "\n" +
            "Ter mais qualidade e menos bagunça. Ter grandes reservas financeiras, mas reconhecer que a maior parte dos desejos materiais é apenas justificativa para gastar tempo com coisas que, na verdade, não importam, inclusive comprar coisas e se preparar para comprar coisas. Você passou duas semanas negociando seu novo carro importado com a concessionária e conseguiu 10 mil dólares de desconto? Que ótimo! Sua vida tem algum sentido? Você contribui com algo útil para o mundo ou está apenas embaralhando papéis, batucando num teclado e voltando para casa para passar os fins de semana bêbado em frente à televisão?\n" +
            "==========\n" +
            "\uFEFFTrabalhe 4 horas por semana (Timothy Ferriss)\n" +
            "- Seu destaque na página 40 | posição 608-608 | Adicionado: terça-feira, 25 de janeiro de 2022 07:03:44\n" +
            "\n" +
            "Foque-se em ser produtivo em vez de focar-se em estar ocupado.";

    @Test
    void clippingsRetrievalIsOK(){
        List<List<String>> clippings = ClippingService.getClippings(clippingSample);
        Assertions.assertEquals(3, clippings.size());
        Assertions.assertEquals(4, clippings.get(0).size());
        Assertions.assertEquals(5, clippings.get(1).size());
        Assertions.assertEquals(5, clippings.get(2).size());
    }

    @Test
    void removingEmptyClippingsIsOK(){
        List<List<String>> clippings = ClippingService.getClippings(clippingSample);
        clippings.forEach(clips -> {
            Assertions.assertEquals(3, ClippingService.removeEmptyBlankAndInvalid(clips).size());
        });
    }
}
