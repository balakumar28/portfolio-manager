package com.balakumar.pm.web;

import com.balakumar.pm.datamodel.objects.Portfolio;
import com.balakumar.pm.datamodel.objects.Transaction;
import com.balakumar.pm.datamodel.objects.User;
import com.balakumar.pm.datamodel.services.PortfolioService;
import com.balakumar.pm.datamodel.services.UserService;
import com.balakumar.pm.utils.Constants;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.login.AbstractLogin;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Route
@Transactional
public class MainView extends VerticalLayout {

    private static Logger logger = LoggerFactory.getLogger(MainView.class);

    private UserService userService;
    private PortfolioService portfolioService;
    private LoginOverlay loginOverlay;

    private Label errorLabel;
    private Div portfolioSelectDiv;
    private Select<String> portfolioSelect;
    private TextField portfolioInput;
    private Button deletePortfolioButton;
    private Button createPortfolioButton;
    private Grid<Transaction> dataGrid;

    private User user;
    private List<Portfolio> portfolios;
    private boolean isAuthenticated = false;

    public MainView(UserService userResource, PortfolioService portfolioResource) {
        this.userService = userResource;
        this.portfolioService = portfolioResource;
        validateLogin();
        defineErrorLabel();
        definePortfolioSelect();
        defineGrid();
    }

    private void defineErrorLabel() {
        errorLabel = new Label();
        errorLabel.setVisible(false);
        add(errorLabel);
    }

    private void definePortfolioSelect() {
        portfolioSelect = new Select<>();
        portfolioSelect.setLabel(Constants.PORTFOLIO);

        portfolioInput = new TextField();
        portfolioInput.setVisible(false);


        createPortfolioButton = new Button(Constants.CREATE);
        createPortfolioButton.addClickListener(event -> {
            if (portfolioInput.isVisible()) {
                try {
                    createPortfolio(portfolioInput.getValue());
                    portfolioInput.setVisible(false);
                    createPortfolioButton.setText(Constants.CREATE);
                    errorLabel.setVisible(false);
                    refresh();
                    portfolioSelect.setValue(portfolioInput.getValue());
                    portfolioInput.clear();
                } catch (Exception e) {
                    errorLabel.setText("Cannot create portfolio with name " + portfolioInput.getValue());
                    errorLabel.setVisible(true);
                    e.printStackTrace();
                }
            } else {
                portfolioInput.setVisible(true);
                createPortfolioButton.setText(Constants.SUBMIT);
            }
        });

        deletePortfolioButton = new Button(Constants.DELETE);
        deletePortfolioButton.addClickListener(event -> {
            try {
                System.out.println("select portfolio " + portfolioSelect.getValue());
                Portfolio portfolioToDelete = getPortfolioByName(portfolioSelect.getValue());
                if (portfolioToDelete == null || portfolioToDelete.getName().equals(Portfolio.DEFAULT_PORTFOLIO)) {
                    throw new Exception("Cannot delete default portfolio");
                }
                System.out.println("deleting portfolio " + portfolioToDelete.getName());
                portfolioService.deleteById(portfolioToDelete.getId());
                errorLabel.setVisible(false);
//               refresh();
            } catch (Exception e) {
                errorLabel.setText("Failed to delete portfolio. " + e.getMessage());
                errorLabel.setVisible(true);
                e.printStackTrace();
            }
        });

        portfolioSelectDiv = new Div();
        portfolioSelectDiv.add(portfolioSelect);
        portfolioSelectDiv.add(deletePortfolioButton);
        portfolioSelectDiv.add(portfolioInput);
        portfolioSelectDiv.add(createPortfolioButton);
        add(portfolioSelectDiv);

    }

    private Portfolio getPortfolioByName(String name) {
        return portfolios.stream().filter(p -> p.getName().equals(name)).findFirst().get();
    }

    private void createPortfolio(String value) {
        Portfolio portfolio = new Portfolio();
        portfolio.setName(value);
        portfolio.setUser(user);
        portfolioService.save(portfolio);
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
            portfolioSelect.setItems(portfolios.stream().map(p -> p.getName()).collect(Collectors.toList()));
            portfolioSelect.setValue(Portfolio.DEFAULT_PORTFOLIO);
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
