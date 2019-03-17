package com.balakumar.pm.datamodel.test;

import com.balakumar.pm.datamodel.objects.MutualFund;
import com.balakumar.pm.datamodel.objects.Portfolio;
import com.balakumar.pm.datamodel.objects.Stock;
import com.balakumar.pm.datamodel.objects.User;
import com.balakumar.pm.datamodel.services.ServiceFactory;
import com.balakumar.pm.datamodel.services.UserService;

import java.util.Calendar;
import java.util.Set;

import static com.balakumar.pm.datamodel.spring.Constants.DEFAULT_PORTFOLIO;

public class Main {

    private static UserService userService = ServiceFactory.getUserService();

    public static void main(String[] args) {
        createUser();
        createPortfolios();
        updatePortfolio();
        printUsers();
    }

    private static void updatePortfolio() {
        User user = userService.findByEmail("balakumar@pm.com");
        Stock stock = new Stock();
        stock.setCode("TCS");
        stock.setName("Tata Consultancy Services");
        stock.setClose(2050);
        stock.setLastUpdated(Calendar.getInstance().getTime());

        MutualFund mutualFund = new MutualFund();
        mutualFund.setCode("AXIS-LTE-FUND");
        mutualFund.setName("Axis Long Term Equity Fund");
        mutualFund.setNav(26);
        mutualFund.setLastUpdated(Calendar.getInstance().getTime());

        Portfolio portfolio = user.getDefaultPortfolio();
        portfolio.getScrips().add(stock);
        portfolio.getScrips().add(mutualFund);

        Set<Portfolio> portfolioSet = user.getPortfolios();
        for (Portfolio p : portfolioSet) {
            if (p.getName().equals("Stocks")) {
                p.getScrips().add(stock);
            }
        }
        userService.save(user);
    }

    private static void createPortfolios() {
        User user = userService.findByEmail("balakumar@pm.com");
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

    private static void printUsers() {
        System.out.println(userService.findAll());
    }

    private static void createUser() {
        User user = new User();
        user.setFirstName("Bala");
        user.setLastName("Kumar");
        user.setEmail("balakumar@pm.com");
        user.setPassword("bala123");
        userService.save(user);
    }
}
