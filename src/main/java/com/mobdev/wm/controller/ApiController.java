package com.mobdev.wm.controller;
import com.mobdev.wm.entity.Resultado;
import com.mobdev.wm.service.ApiServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wm")
public class ApiController {
    @Autowired
    private ApiServiceImpl apiService;

    @GetMapping("/resultados/{id}")
    public Resultado findAndResultadoById(@PathVariable String id){
        return apiService.findAndResultadoById(id);
    }
}
