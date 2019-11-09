package com.balakumar.pm.web;

import com.balakumar.pm.datamodel.objects.Portfolio;
import com.balakumar.pm.datamodel.objects.Transaction;
import com.balakumar.pm.datamodel.objects.User;
import com.balakumar.pm.datamodel.services.PortfolioService;
import com.balakumar.pm.datamodel.services.UserService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.login.AbstractLogin;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.Route;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Route
public class MainView extends VerticalLayout {

    private UserService userService;
    private PortfolioService portfolioService;
    private LoginOverlay loginOverlay;

    private Select<String> portfolioSelect;
    private Grid<Transaction> dataGrid;

    private User user;
    private List<Portfolio> portfolios;
    private boolean isAuthenticated = false;

    public MainView(UserService userResource, PortfolioService portfolioResource) {
        this.userService = userResource;
        this.portfolioService = portfolioResource;
        portfolioSelect = new Select<>();
        portfolioSelect.setLabel("Portfolio");
        validateLogin();
        add(portfolioSelect);
        defineGrid();
    }

    private void defineGrid() {
        dataGrid = new Grid<>(Transaction.class);
        dataGrid.removeAllColumns();
        dataGrid.addColumns("date");
        dataGrid.addColumn(t -> t.getScrip().getName()).setHeader("Name").setKey("name");
        dataGrid.addColumns("qty", "costPerUnit", "value");
//        dataGrid.addColumn(Transaction::getCostPerUnit);
//        dataGrid.addColumn(Transaction::getValue);
        add(dataGrid);
    }

    private void showLogin() {
        if(loginOverlay == null) {
            loginOverlay = new LoginOverlay();
            loginOverlay.addLoginListener(e -> {
                isAuthenticated = authenticate(e);
                refresh();
            });
        }
        loginOverlay.setOpened(true);
    }

    private boolean authenticate(AbstractLogin.LoginEvent e) {
        try {
            Optional<User> user = userService.findByEmail(e.getUsername());
            if(user.isPresent() && Objects.equals(user.get().getPassword(), e.getPassword())) {
                loginOverlay.setOpened(false);
                this.user = user.get();
                return true;
            }
        } catch (Exception ex) {
            loginOverlay.setI18n(createErrorMessage("Server Error", ex.toString()));
        }
        loginOverlay.setError(true);
        return false;
    }

    private LoginI18n createErrorMessage(String title, String message) {
        LoginI18n loginI18n = new LoginI18n();
        LoginI18n.ErrorMessage errorMessage = new LoginI18n.ErrorMessage();
        errorMessage.setTitle(title);
        errorMessage.setMessage(message);
        loginI18n.setErrorMessage(errorMessage);
        return loginI18n;
    }

    private void refresh() {
        validateLogin();
        if(user != null) {
            portfolios = portfolioService.findAllByUser(user);
            portfolioSelect.setPlaceholder(Portfolio.DEFAULT_PORTFOLIO);
            portfolioSelect.setItems(portfolios.stream().map(p -> p.getName()).collect(Collectors.toList()));
            portfolioSelect.addValueChangeListener(e -> populateGrid(e.getValue()));
            populateGrid(Portfolio.DEFAULT_PORTFOLIO);
        }
    }

    private void populateGrid(String portfolioName) {
        if(portfolios != null) {
            Optional<Portfolio> portfolio = portfolios.stream().filter(p -> p.getName().equals(portfolioName)).findFirst();
            portfolio.ifPresent(p -> {
                dataGrid.setItems(p.getTransactions());
            });
        }
    }

    private void validateLogin() {
        if(!isAuthenticated) {
            showLogin();
        }
    }


}
