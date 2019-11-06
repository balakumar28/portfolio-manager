package com.balakumar.pm.datamodel.test;

import com.balakumar.pm.datamodel.exeptions.DataModelException;
import com.balakumar.pm.datamodel.objects.*;
import com.balakumar.pm.datamodel.services.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Optional;
import java.util.Set;

import static com.balakumar.pm.datamodel.Constants.DEFAULT_PORTFOLIO;


public class Main {

    private static UserService userService;
    private static ScripService scripService;
    private static ScripHistoryService scripHistoryService;

    static {
        try {
            userService = ServiceFactory.getService(Service.USER);
            scripService = ServiceFactory.getService(Service.SCRIP);
            scripHistoryService = ServiceFactory.getService(Service.SCRIP_HISTORY);
        } catch (DataModelException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.createUser();
        main.createScrips();
        main.addScriptHistory();
        main.createPortfolios();
        main.updatePortfolio();
        main.printUsers();
        main.printScrips();

        main.cleanData();
    }

    private void addScriptHistory() {
        Scrip tcs = scripService.findByCode("TCS").get();
        ScripHistory scripHistory = new ScripHistory();
        scripHistory.setDate(Date.valueOf(LocalDate.now()));
        scripHistory.setClose(2200);
        scripHistory.setScrip(tcs);
        scripHistoryService.save(scripHistory);

        scripHistory = new ScripHistory();
        scripHistory.setScrip(tcs);
        scripHistory.setDate(Date.valueOf(LocalDate.of(2019, 11, 5)));
        scripHistory.setClose(2186);
        scripHistoryService.save(scripHistory);
    }

    private void createScrips() {
        Stock stock = new Stock();
        stock.setCode("TCS");
        stock.setName("Tata Consultancy Services");
        stock.setClose(2050);
        stock.setLastUpdated(Calendar.getInstance().getTime());
        scripService.save(stock);

        MutualFund mutualFund = new MutualFund();
        mutualFund.setCode("AXIS-LTE-FUND");
        mutualFund.setName("Axis Long Term Equity Fund");
        mutualFund.setNav(26);
        mutualFund.setLastUpdated(Calendar.getInstance().getTime());
        scripService.save(mutualFund);
    }


    //    @Transactional
    private void cleanData() {
        Optional<User> user = userService.findByEmail("balakumar@pm.com");
        user.ifPresent(value -> userService.delete(value));

        Optional<Scrip> tcs = scripService.findByCode("TCS");
        tcs.ifPresent(scrip -> scripService.delete(scrip));

        Optional<Scrip> axisLteFund = scripService.findByCode("AXIS-LTE-FUND");
        axisLteFund.ifPresent(scrip -> scripService.delete(scrip));
    }

    private void updatePortfolio() {
        User user = userService.findByEmail("balakumar@pm.com").get();
        Portfolio portfolio = user.getDefaultPortfolio();
        Transaction transaction = new Transaction();
        transaction.setScrip(scripService.findByCode("AXIS-LTE-FUND").get());
        transaction.setDate(Date.valueOf("2019-11-06"));
        transaction.setQty(1000);
        transaction.setCostPerUnit(19.58);
        portfolio.getTransactions().add(transaction);

        transaction = new Transaction();
        transaction.setScrip(scripService.findByCode("TCS").get());
        transaction.setDate(Date.valueOf("2019-11-06"));
        transaction.setQty(10);
        transaction.setCostPerUnit(1940);
        portfolio.getTransactions().add(transaction);

        Set<Portfolio> portfolioSet = user.getPortfolios();
        for (Portfolio p : portfolioSet) {
            if (p.getName().equals("Stocks")) {
                p.getTransactions().add(transaction);
            }
        }
        userService.save(user);
    }

    private void createPortfolios() {
        User user = userService.findByEmail("balakumar@pm.com").get();
        Portfolio portfolio = new Portfolio();
        portfolio.setName(DEFAULT_PORTFOLIO);
        portfolio.setUser(user);
        user.getPortfolios().add(portfolio);
        portfolio = new Portfolio();
        portfolio.setName("Stocks");
        portfolio.setUser(user);
        user.getPortfolios().add(portfolio);
        userService.save(user);
    }

    private void printUsers() {
        System.out.println(userService.findAll());
    }

    private void createUser() {
        User user = new User();
        user.setFirstName("Bala");
        user.setLastName("Kumar");
        user.setEmail("balakumar@pm.com");
        user.setPassword("bala123");
        userService.save(user);
    }

    private void printScrips() {
        System.out.println(scripService.findAll());
    }
}
