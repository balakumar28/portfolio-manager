package com.balakumar.pm.datamodel.rest;

import com.balakumar.pm.datamodel.Constants;
import com.balakumar.pm.datamodel.objects.Portfolio;
import com.balakumar.pm.datamodel.services.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.balakumar.pm.datamodel.Constants.REST_PORTFOLIO;
import static com.balakumar.pm.datamodel.Constants.REST_SAVE;

@RestController
@RequestMapping(REST_PORTFOLIO)
public class PortfolioResource {

    @Autowired
    private PortfolioService portfolioService;

    @PostMapping("/" + REST_SAVE)
    public Portfolio save(Portfolio portfolio) {
        return portfolioService.save(portfolio);
    }

    @GetMapping("/{id}")
    public Portfolio find(@PathVariable Long id) {
        return portfolioService.findById(id).get();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        portfolioService.deleteById(id);
    }
}
