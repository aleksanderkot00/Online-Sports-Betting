//package com.github.aleksanderkot00.onlinesportsbetting;
//
//import com.github.aleksanderkot00.onlinesportsbetting.domain.*;
//import com.github.aleksanderkot00.onlinesportsbetting.repository.BetRepository;
//import com.github.aleksanderkot00.onlinesportsbetting.repository.CategoryRepository;
//import com.github.aleksanderkot00.onlinesportsbetting.repository.EventRepository;
//import com.github.aleksanderkot00.onlinesportsbetting.repository.UserRepository;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class ExampleData {
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private CategoryRepository categoryRepository;
//
//    @Autowired
//    private EventRepository eventRepository;
//
//    @Autowired
//    private BetRepository betRepository;
//
//    @Test
//    public void addAddExampleData() {
//        Role roleAdmin = new Role();
//        roleAdmin.setRole("ADMIN");
//        User admin = new User();
//        admin.setLastName("Admin");
//        admin.setName("Admin");
//        admin.setEncryptedPassword(passwordEncoder.encode("admin"));
//        admin.setEmail("admin@admin");
//        admin.setBalance(new BigDecimal("1000000"));
//        admin.getRoles().add(roleAdmin);
//        userRepository.save(admin);
//
//        Role roleUser = new Role();
//        roleUser.setRole("USER");
//        User user = new User();
//        user.setLastName("testName");
//        user.setName("LastName");
//        user.setEncryptedPassword(passwordEncoder.encode("user"));
//        user.setEmail("user@user");
//        user.setBalance(new BigDecimal("2314"));
//        user.getRoles().add(roleUser);
//        userRepository.save(user);
//
//        Category premierLeague = new Category();
//        premierLeague.setName("Premier League");
//        Category bundesliga = new Category();
//        bundesliga.setName("Bundesliga");
//        Category laLiga = new Category();
//        laLiga.setName("La Liga");
//        Category serieA = new Category();
//        serieA.setName("Serie A");
//
//        categoryRepository.save(premierLeague);
//        categoryRepository.save(bundesliga);
//        categoryRepository.save(laLiga);
//        categoryRepository.save(serieA);
//
//        Event napSam = new Event();
//        napSam.setFinished(false);
//        napSam.setCategory(serieA);
//        napSam.setTeamOneName("Napoli");
//        napSam.setTeamTwoName("Sampdoria");
//        napSam.setDateTime(LocalDateTime.now().plusDays(40));
//
//        Event manUmanC = new Event();
//        manUmanC.setFinished(false);
//        manUmanC.setCategory(premierLeague);
//        manUmanC.setTeamOneName("Manchester United");
//        manUmanC.setTeamTwoName("Manchester City");
//        manUmanC.setDateTime(LocalDateTime.now().plusDays(35));
//
//        Event bayBor = new Event();
//        bayBor.setFinished(false);
//        bayBor.setCategory(bundesliga);
//        bayBor.setTeamOneName("Bayern");
//        bayBor.setTeamTwoName("Borussia");
//        bayBor.setDateTime(LocalDateTime.now().plusDays(38));
//
//        Event valBar = new Event();
//        valBar.setFinished(false);
//        valBar.setCategory(laLiga);
//        valBar.setTeamOneName("Valencia");
//        valBar.setTeamTwoName("Barcelona");
//        valBar.setDateTime(LocalDateTime.now().plusDays(33));
//
//        eventRepository.save(napSam);
//        eventRepository.save(bayBor);
//        eventRepository.save(manUmanC);
//        eventRepository.save(valBar);
//
//        Bet betMM1 = new Bet();
//        betMM1.setType(BetType.ONE);
//        betMM1.setOdds(new BigDecimal(4.11));
//        betMM1.setEvent(manUmanC);
//        Bet betMM2 = new Bet();
//        betMM2.setType(BetType.TWO);
//        betMM2.setOdds(new BigDecimal(1.4));
//        betMM2.setEvent(manUmanC);
//        Bet betMM0 = new Bet();
//        betMM0.setType(BetType.ZERO);
//        betMM0.setOdds(new BigDecimal(3.5));
//        betMM0.setEvent(manUmanC);
//        Bet betMM02 = new Bet();
//        betMM02.setType(BetType.TWO);
//        betMM02.setOdds(new BigDecimal(1.33));
//        betMM02.setEvent(manUmanC);
//        Bet betMM01 = new Bet();
//        betMM01.setType(BetType.ZERO);
//        betMM01.setOdds(new BigDecimal(2.8));
//        betMM01.setEvent(manUmanC);
//
//        betRepository.save(betMM0);
//        betRepository.save(betMM1);
//        betRepository.save(betMM2);
//        betRepository.save(betMM01);
//        betRepository.save(betMM02);
//
//        Bet betVB1 = new Bet();
//        betVB1.setType(BetType.ONE);
//        betVB1.setOdds(new BigDecimal(5.21));
//        betVB1.setEvent(valBar);
//        Bet betVB2 = new Bet();
//        betVB2.setType(BetType.TWO);
//        betVB2.setOdds(new BigDecimal(1.31));
//        betVB2.setEvent(valBar);
//        Bet betVB0 = new Bet();
//        betVB0.setType(BetType.ZERO);
//        betVB0.setOdds(new BigDecimal(3.8));
//        betVB0.setEvent(valBar);
//        Bet betVB02 = new Bet();
//        betVB02.setType(BetType.TWO);
//        betVB02.setOdds(new BigDecimal(1.15));
//        betVB02.setEvent(valBar);
//        Bet betVB01 = new Bet();
//        betVB01.setType(BetType.ZERO);
//        betVB01.setOdds(new BigDecimal(3.1));
//        betVB01.setEvent(valBar);
//
//        betRepository.save(betVB0);
//        betRepository.save(betVB1);
//        betRepository.save(betVB2);
//        betRepository.save(betVB01);
//        betRepository.save(betVB02);
//
//        Bet betNS1 = new Bet();
//        betNS1.setType(BetType.ONE);
//        betNS1.setOdds(new BigDecimal(1.91));
//        betNS1.setEvent(napSam);
//        Bet betNS2 = new Bet();
//        betNS2.setType(BetType.TWO);
//        betNS2.setOdds(new BigDecimal(2.51));
//        betNS2.setEvent(napSam);
//        Bet betNS0 = new Bet();
//        betNS0.setType(BetType.ZERO);
//        betNS0.setOdds(new BigDecimal(2.2));
//        betNS0.setEvent(napSam);
//        Bet betNS02 = new Bet();
//        betNS02.setType(BetType.TWO);
//        betNS02.setOdds(new BigDecimal(1.72));
//        betNS02.setEvent(napSam);
//        Bet betNS01 = new Bet();
//        betNS01.setType(BetType.ZERO);
//        betNS01.setOdds(new BigDecimal(2.21));
//        betNS01.setEvent(napSam);
//
//        betRepository.save(betNS0);
//        betRepository.save(betNS1);
//        betRepository.save(betNS2);
//        betRepository.save(betNS01);
//        betRepository.save(betNS02);
//
//        Bet betBB1 = new Bet();
//        betBB1.setType(BetType.ONE);
//        betBB1.setOdds(new BigDecimal(4.21));
//        betBB1.setEvent(bayBor);
//        Bet betBB2 = new Bet();
//        betBB2.setType(BetType.TWO);
//        betBB2.setOdds(new BigDecimal(1.61));
//        betBB2.setEvent(bayBor);
//        Bet betBB0 = new Bet();
//        betBB0.setType(BetType.ZERO);
//        betBB0.setOdds(new BigDecimal(2.5));
//        betBB0.setEvent(bayBor);
//        Bet betBB02 = new Bet();
//        betBB02.setType(BetType.TWO);
//        betBB02.setOdds(new BigDecimal(1.48));
//        betBB02.setEvent(bayBor);
//        Bet betBB01 = new Bet();
//        betBB01.setType(BetType.ZERO);
//        betBB01.setOdds(new BigDecimal(3.33));
//        betBB01.setEvent(bayBor);
//
//        betRepository.save(betBB0);
//        betRepository.save(betBB1);
//        betRepository.save(betBB2);
//        betRepository.save(betBB01);
//        betRepository.save(betBB02);
//
//        Event serie1 = new Event();
//        serie1.setFinished(true);
//        serie1.setCategory(serieA);
//        serie1.setTeamOneScore(BigDecimal.ONE);
//        serie1.setTeamTwoScore(BigDecimal.valueOf(2));
//        serie1.setTeamOneName("Juventus");
//        serie1.setTeamTwoName("Inter Mediolan");
//        serie1.setDateTime(LocalDateTime.now().minusDays(40));
//
//        Event serie2 = new Event();
//        serie2.setFinished(true);
//        serie2.setCategory(serieA);
//        serie2.setTeamOneScore(BigDecimal.ZERO);
//        serie2.setTeamTwoScore(BigDecimal.ZERO);
//        serie2.setTeamOneName("AC Milan");
//        serie2.setTeamTwoName("Lazio");
//        serie2.setDateTime(LocalDateTime.now().minusDays(42));
//
//        Event serie3 = new Event();
//        serie3.setFinished(true);
//        serie3.setCategory(serieA);
//        serie3.setTeamOneScore(BigDecimal.ZERO);
//        serie3.setTeamTwoScore(BigDecimal.valueOf(3));
//        serie3.setTeamOneName("Atalanta");
//        serie3.setTeamTwoName("Fiorentina");
//        serie3.setDateTime(LocalDateTime.now().minusDays(35));
//
//        Event bundesliga1 = new Event();
//        bundesliga1.setFinished(true);
//        bundesliga1.setCategory(bundesliga);
//        bundesliga1.setTeamOneScore(BigDecimal.valueOf(5));
//        bundesliga1.setTeamTwoScore(BigDecimal.valueOf(1));
//        bundesliga1.setTeamOneName("Bayern");
//        bundesliga1.setTeamTwoName("Fortuna Dusseldorf");
//        bundesliga1.setDateTime(LocalDateTime.now().minusDays(32));
//
//        Event bundesliga2 = new Event();
//        bundesliga2.setFinished(true);
//        bundesliga2.setCategory(bundesliga);
//        bundesliga2.setTeamOneScore(BigDecimal.valueOf(3));
//        bundesliga2.setTeamTwoScore(BigDecimal.valueOf(2));
//        bundesliga2.setTeamOneName("Borrusia Dortmund");
//        bundesliga2.setTeamTwoName("RB Lipsk");
//        bundesliga2.setDateTime(LocalDateTime.now().minusDays(45));
//
//        Event bundesliga3 = new Event();
//        bundesliga3.setFinished(true);
//        bundesliga3.setCategory(bundesliga);
//        bundesliga3.setTeamOneScore(BigDecimal.valueOf(0));
//        bundesliga3.setTeamTwoScore(BigDecimal.valueOf(1));
//        bundesliga3.setTeamOneName("Wintracht Frankfurt");
//        bundesliga3.setTeamTwoName("Bayer Leverkusen");
//        bundesliga3.setDateTime(LocalDateTime.now().minusDays(43));
//
//        Event premierLeague1 = new Event();
//        premierLeague1.setFinished(true);
//        premierLeague1.setCategory(premierLeague);
//        premierLeague1.setTeamOneScore(BigDecimal.valueOf(6));
//        premierLeague1.setTeamTwoScore(BigDecimal.valueOf(0));
//        premierLeague1.setTeamOneName("Manchester City");
//        premierLeague1.setTeamTwoName("Cheslsea");
//        premierLeague1.setDateTime(LocalDateTime.now().minusDays(34));
//
//        Event premierLeague2 = new Event();
//        premierLeague2.setFinished(true);
//        premierLeague2.setCategory(premierLeague);
//        premierLeague2.setTeamOneScore(BigDecimal.valueOf(4));
//        premierLeague2.setTeamTwoScore(BigDecimal.valueOf(2));
//        premierLeague2.setTeamOneName("Arsenal");
//        premierLeague2.setTeamTwoName("Tottenham");
//        premierLeague2.setDateTime(LocalDateTime.now().minusDays(45));
//
//        Event premierLeague3 = new Event();
//        premierLeague3.setFinished(true);
//        premierLeague3.setCategory(premierLeague);
//        premierLeague3.setTeamOneScore(BigDecimal.valueOf(2));
//        premierLeague3.setTeamTwoScore(BigDecimal.valueOf(1));
//        premierLeague3.setTeamOneName("Manchester city");
//        premierLeague3.setTeamTwoName("Liverpool");
//        premierLeague3.setDateTime(LocalDateTime.now().minusDays(41));
//
//        Event laLiga1 = new Event();
//        laLiga1.setFinished(true);
//        laLiga1.setCategory(laLiga);
//        laLiga1.setTeamOneScore(BigDecimal.valueOf(1));
//        laLiga1.setTeamTwoScore(BigDecimal.valueOf(5));
//        laLiga1.setTeamOneName("Real Madryt");
//        laLiga1.setTeamTwoName("Barcelona");
//        laLiga1.setDateTime(LocalDateTime.now().minusDays(54));
//
//        Event laLiga2 = new Event();
//        laLiga2.setFinished(true);
//        laLiga2.setCategory(laLiga);
//        laLiga2.setTeamOneScore(BigDecimal.valueOf(1));
//        laLiga2.setTeamTwoScore(BigDecimal.valueOf(2));
//        laLiga2.setTeamOneName("Levante");
//        laLiga2.setTeamTwoName("Girona");
//        laLiga2.setDateTime(LocalDateTime.now().minusDays(41));
//
//        Event laLiga3 = new Event();
//        laLiga3.setFinished(true);
//        laLiga3.setCategory(laLiga);
//        laLiga3.setTeamOneScore(BigDecimal.valueOf(3));
//        laLiga3.setTeamTwoScore(BigDecimal.valueOf(1));
//        laLiga3.setTeamOneName("Valencia");
//        laLiga3.setTeamTwoName("Atletico");
//        laLiga3.setDateTime(LocalDateTime.now().minusDays(41));
//
//        eventRepository.save(bundesliga1);
//        eventRepository.save(bundesliga2);
//        eventRepository.save(bundesliga3);
//
//        eventRepository.save(premierLeague1);
//        eventRepository.save(premierLeague2);
//        eventRepository.save(premierLeague3);
//
//        eventRepository.save(serie1);
//        eventRepository.save(serie2);
//        eventRepository.save(serie3);
//
//        eventRepository.save(laLiga1);
//        eventRepository.save(laLiga2);
//        eventRepository.save(laLiga3);
//
//        Bet betPL11 = new Bet();
//        betPL11.setType(BetType.ONE);
//        betPL11.setOdds(new BigDecimal(2.11));
//        betPL11.setActive(false);
//        betPL11.setResult(BetResult.WINNING);
//        betPL11.setEvent(premierLeague1);
//        Bet betPL12 = new Bet();
//        betPL12.setType(BetType.TWO);
//        betPL12.setOdds(new BigDecimal(3.4));
//        betPL12.setActive(false);
//        betPL12.setResult(BetResult.LOST);
//        betPL12.setEvent(premierLeague1);
//        Bet betPL10 = new Bet();
//        betPL10.setType(BetType.ZERO);
//        betPL10.setOdds(new BigDecimal(3.6));
//        betPL10.setActive(false);
//        betPL10.setResult(BetResult.LOST);
//        betPL10.setEvent(premierLeague1);
//
//        Bet betPL21 = new Bet();
//        betPL21.setType(BetType.ONE);
//        betPL21.setOdds(new BigDecimal(2.5));
//        betPL21.setActive(false);
//        betPL21.setResult(BetResult.WINNING);
//        betPL21.setEvent(premierLeague2);
//        Bet betPL22 = new Bet();
//        betPL22.setType(BetType.TWO);
//        betPL22.setOdds(new BigDecimal(2.4));
//        betPL22.setActive(false);
//        betPL22.setResult(BetResult.LOST);
//        betPL22.setEvent(premierLeague2);
//        Bet betPL20 = new Bet();
//        betPL20.setType(BetType.ZERO);
//        betPL20.setOdds(new BigDecimal(2.9));
//        betPL20.setActive(false);
//        betPL20.setResult(BetResult.LOST);
//        betPL20.setEvent(premierLeague2);
//
//        Bet betPL31 = new Bet();
//        betPL31.setType(BetType.ONE);
//        betPL31.setOdds(new BigDecimal(1.69));
//        betPL31.setActive(false);
//        betPL31.setResult(BetResult.WINNING);
//        betPL31.setEvent(premierLeague3);
//        Bet betPL32 = new Bet();
//        betPL32.setType(BetType.TWO);
//        betPL32.setOdds(new BigDecimal(1.90));
//        betPL32.setActive(false);
//        betPL32.setResult(BetResult.LOST);
//        betPL32.setEvent(premierLeague3);
//        Bet betPL30 = new Bet();
//        betPL30.setType(BetType.ZERO);
//        betPL30.setOdds(new BigDecimal(2.2));
//        betPL30.setActive(false);
//        betPL30.setResult(BetResult.LOST);
//        betPL30.setEvent(premierLeague3);
//
//        betRepository.save(betPL10);
//        betRepository.save(betPL11);
//        betRepository.save(betPL12);
//        betRepository.save(betPL20);
//        betRepository.save(betPL21);
//        betRepository.save(betPL22);
//        betRepository.save(betPL30);
//        betRepository.save(betPL31);
//        betRepository.save(betPL32);
//
//        Bet betBL11 = new Bet();
//        betBL11.setType(BetType.ONE);
//        betBL11.setOdds(new BigDecimal(2.11));
//        betBL11.setActive(false);
//        betBL11.setResult(BetResult.WINNING);
//        betBL11.setEvent(bundesliga1);
//        Bet betBL12 = new Bet();
//        betBL12.setType(BetType.TWO);
//        betBL12.setOdds(new BigDecimal(3.4));
//        betBL12.setActive(false);
//        betBL12.setResult(BetResult.LOST);
//        betBL12.setEvent(bundesliga1);
//        Bet betBL10 = new Bet();
//        betBL10.setType(BetType.ZERO);
//        betBL10.setOdds(new BigDecimal(3.6));
//        betBL10.setActive(false);
//        betBL10.setResult(BetResult.LOST);
//        betBL10.setEvent(bundesliga1);
//
//        Bet betBL21 = new Bet();
//        betBL21.setType(BetType.ONE);
//        betBL21.setOdds(new BigDecimal(2.5));
//        betBL21.setActive(false);
//        betBL21.setResult(BetResult.WINNING);
//        betBL21.setEvent(bundesliga2);
//        Bet betBL22 = new Bet();
//        betBL22.setType(BetType.TWO);
//        betBL22.setOdds(new BigDecimal(2.4));
//        betBL22.setActive(false);
//        betBL22.setResult(BetResult.LOST);
//        betBL22.setEvent(bundesliga2);
//        Bet betBL20 = new Bet();
//        betBL20.setType(BetType.ZERO);
//        betBL20.setOdds(new BigDecimal(2.9));
//        betBL20.setActive(false);
//        betBL20.setResult(BetResult.LOST);
//        betBL20.setEvent(bundesliga2);
//
//        Bet betBL31 = new Bet();
//        betBL31.setType(BetType.ONE);
//        betBL31.setOdds(new BigDecimal(1.69));
//        betBL31.setActive(false);
//        betBL31.setResult(BetResult.WINNING);
//        betBL31.setEvent(bundesliga3);
//        Bet betBL32 = new Bet();
//        betBL32.setType(BetType.TWO);
//        betBL32.setOdds(new BigDecimal(1.90));
//        betBL32.setActive(false);
//        betBL32.setResult(BetResult.LOST);
//        betBL32.setEvent(bundesliga3);
//        Bet betBL30 = new Bet();
//        betBL30.setType(BetType.ZERO);
//        betBL30.setOdds(new BigDecimal(2.2));
//        betBL30.setActive(false);
//        betBL30.setResult(BetResult.LOST);
//        betBL30.setEvent(bundesliga3);
//
//        betRepository.save(betBL10);
//        betRepository.save(betBL11);
//        betRepository.save(betBL12);
//        betRepository.save(betBL20);
//        betRepository.save(betBL21);
//        betRepository.save(betBL22);
//        betRepository.save(betBL30);
//        betRepository.save(betBL31);
//        betRepository.save(betBL32);
//
//        Bet betLL11 = new Bet();
//        betLL11.setType(BetType.ONE);
//        betLL11.setOdds(new BigDecimal(2.11));
//        betLL11.setActive(false);
//        betLL11.setResult(BetResult.WINNING);
//        betLL11.setEvent(laLiga1);
//        Bet betLL12 = new Bet();
//        betLL12.setType(BetType.TWO);
//        betLL12.setOdds(new BigDecimal(3.4));
//        betLL12.setActive(false);
//        betLL12.setResult(BetResult.LOST);
//        betLL12.setEvent(laLiga1);
//        Bet betLL10 = new Bet();
//        betLL10.setType(BetType.ZERO);
//        betLL10.setOdds(new BigDecimal(3.6));
//        betLL10.setActive(false);
//        betLL10.setResult(BetResult.LOST);
//        betLL10.setEvent(laLiga1);
//
//        Bet betLL21 = new Bet();
//        betLL21.setType(BetType.ONE);
//        betLL21.setOdds(new BigDecimal(2.5));
//        betLL21.setActive(false);
//        betLL21.setResult(BetResult.WINNING);
//        betLL21.setEvent(laLiga2);
//        Bet betLL22 = new Bet();
//        betLL22.setType(BetType.TWO);
//        betLL22.setOdds(new BigDecimal(2.4));
//        betLL22.setActive(false);
//        betLL22.setResult(BetResult.LOST);
//        betLL22.setEvent(laLiga2);
//        Bet betLL20 = new Bet();
//        betLL20.setType(BetType.ZERO);
//        betLL20.setOdds(new BigDecimal(2.9));
//        betLL20.setActive(false);
//        betLL20.setResult(BetResult.LOST);
//        betLL20.setEvent(laLiga2);
//
//        Bet betLL31 = new Bet();
//        betLL31.setType(BetType.ONE);
//        betLL31.setOdds(new BigDecimal(1.69));
//        betLL31.setActive(false);
//        betLL31.setResult(BetResult.WINNING);
//        betLL31.setEvent(laLiga3);
//        Bet betLL32 = new Bet();
//        betLL32.setType(BetType.TWO);
//        betLL32.setOdds(new BigDecimal(1.90));
//        betLL32.setActive(false);
//        betLL32.setResult(BetResult.LOST);
//        betLL32.setEvent(laLiga3);
//        Bet betLL30 = new Bet();
//        betLL30.setType(BetType.ZERO);
//        betLL30.setOdds(new BigDecimal(2.2));
//        betLL30.setActive(false);
//        betLL30.setResult(BetResult.LOST);
//        betLL30.setEvent(laLiga3);
//
//        betRepository.save(betLL10);
//        betRepository.save(betLL11);
//        betRepository.save(betLL12);
//        betRepository.save(betLL20);
//        betRepository.save(betLL21);
//        betRepository.save(betLL22);
//        betRepository.save(betLL30);
//        betRepository.save(betLL31);
//        betRepository.save(betLL32);
//
//
//        Bet betSA11 = new Bet();
//        betSA11.setType(BetType.ONE);
//        betSA11.setOdds(new BigDecimal(2.11));
//        betSA11.setActive(false);
//        betSA11.setResult(BetResult.WINNING);
//        betSA11.setEvent(serie1);
//        Bet betSA12 = new Bet();
//        betSA12.setType(BetType.TWO);
//        betSA12.setOdds(new BigDecimal(3.4));
//        betSA12.setActive(false);
//        betSA12.setResult(BetResult.LOST);
//        betSA12.setEvent(serie1);
//        Bet betSA10 = new Bet();
//        betSA10.setType(BetType.ZERO);
//        betSA10.setOdds(new BigDecimal(3.6));
//        betSA10.setActive(false);
//        betSA10.setResult(BetResult.LOST);
//        betSA10.setEvent(serie1);
//
//        Bet betSA21 = new Bet();
//        betSA21.setType(BetType.ONE);
//        betSA21.setOdds(new BigDecimal(2.5));
//        betSA21.setActive(false);
//        betSA21.setResult(BetResult.WINNING);
//        betSA21.setEvent(serie2);
//        Bet betSA22 = new Bet();
//        betSA22.setType(BetType.TWO);
//        betSA22.setOdds(new BigDecimal(2.4));
//        betSA22.setActive(false);
//        betSA22.setResult(BetResult.LOST);
//        betSA22.setEvent(serie2);
//        Bet betSA20 = new Bet();
//        betSA20.setType(BetType.ZERO);
//        betSA20.setOdds(new BigDecimal(2.9));
//        betSA20.setActive(false);
//        betSA20.setResult(BetResult.LOST);
//        betSA20.setEvent(serie2);
//
//        Bet betSA31 = new Bet();
//        betSA31.setType(BetType.ONE);
//        betSA31.setOdds(new BigDecimal(1.69));
//        betSA31.setActive(false);
//        betSA31.setResult(BetResult.WINNING);
//        betSA31.setEvent(serie3);
//        Bet betSA32 = new Bet();
//        betSA32.setType(BetType.TWO);
//        betSA32.setOdds(new BigDecimal(1.90));
//        betSA32.setActive(false);
//        betSA32.setResult(BetResult.LOST);
//        betSA32.setEvent(serie3);
//        Bet betSA30 = new Bet();
//        betSA30.setType(BetType.ZERO);
//        betSA30.setOdds(new BigDecimal(2.2));
//        betSA30.setActive(false);
//        betSA30.setResult(BetResult.LOST);
//        betSA30.setEvent(serie3);
//
//        betRepository.save(betSA10);
//        betRepository.save(betSA11);
//        betRepository.save(betSA12);
//        betRepository.save(betSA20);
//        betRepository.save(betSA21);
//        betRepository.save(betSA22);
//        betRepository.save(betSA30);
//        betRepository.save(betSA31);
//        betRepository.save(betSA32);
//
//        Slip orderedSlip = new Slip();
//        orderedSlip.setStake(new BigDecimal("28"));
//        orderedSlip.setState(SlipState.ORDERED);
//        orderedSlip.getBets().add(betPL12);
//        orderedSlip.getBets().add(betBL20);
//        orderedSlip.getBets().add(betLL21);
//        orderedSlip.refreshTotalOdds();
//
//        Slip lostSlip = new Slip();
//        lostSlip.setStake(new BigDecimal("53"));
//        lostSlip.setState(SlipState.LOST);
//        lostSlip.getBets().add(betSA11);
//        lostSlip.getBets().add(betBL20);
//        lostSlip.getBets().add(betLL32);
//        lostSlip.refreshTotalOdds();
//
//        user.getSlips().add(lostSlip);
//        user.getSlips().add(orderedSlip);
//        userRepository.save(user);
//    }
//
//}
