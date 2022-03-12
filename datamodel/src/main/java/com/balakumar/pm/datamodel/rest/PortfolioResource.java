package com.balakumar.pm.datamodel.rest;

import com.balakumar.pm.datamodel.objects.Portfolio;
import com.balakumar.pm.datamodel.objects.User;
import com.balakumar.pm.datamodel.services.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/getByUserId/{userId}")
    public List<Portfolio> getPortfolios(@PathVariable Long userId) {
        User tempUser = new User();
        tempUser.setId(userId);
        return portfolioService.findAllByUser(tempUser);
    }
}
