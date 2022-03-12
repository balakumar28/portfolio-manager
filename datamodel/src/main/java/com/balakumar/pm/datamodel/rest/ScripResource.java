package com.balakumar.pm.datamodel.rest;

import com.balakumar.pm.datamodel.objects.Scrip;
import com.balakumar.pm.datamodel.services.ScripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.balakumar.pm.datamodel.Constants.*;

@RestController
@RequestMapping(REST_SCRIP)
public class ScripResource {

    @Autowired
    private ScripService scripService;

    @PostMapping("/" + REST_SAVE)
    public Scrip save(Scrip scrip) {
        return scripService.save(scrip);
    }

    @GetMapping("/{id}")
    public Scrip find(@PathVariable Long id) {
        return scripService.findById(id).get();
    }

    @GetMapping("/" + REST_CODE + "/{code}")
    public Scrip findByCode(@PathVariable String code) {
        return scripService.findByCode(code).get();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        scripService.deleteById(id);
    }

    @DeleteMapping("/")
    public void delete(@RequestParam String code) {
        scripService.deleteByCode(code);
    }
}
