package com.xyz.controlador;

import com.xyz.modelo.Operador;
import com.xyz.repo.OperadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Controller
public class OperadorController {
    @Autowired
    private OperadorRepository operadorRepository;

    @GetMapping("/operador")
    ModelAndView operadorindex(){
        List<Operador> operadores = operadorRepository.findAll();
        return new ModelAndView("operadorIndex")
                .addObject("operador", operadores);
    }

    @GetMapping("/operador/add")
    ModelAndView add(){
        return new ModelAndView("operadorForm")
                .addObject("operador", new Operador());
    }

    @PostMapping("/operador/add")
    ModelAndView crear(@Validated Operador operador, BindingResult bindingResult, RedirectAttributes ra){
        if(bindingResult.hasErrors()){
            return new ModelAndView("operadorForm")
                    .addObject("operador", operador);
        }
        operadorRepository.save(operador);
        ra.addFlashAttribute("msgExito", "added");
        return new ModelAndView("redirect:/operadorIndex");
    }

    @GetMapping("/{id}/operador/edit")
    ModelAndView edit(@PathVariable Integer id){
        Operador operador = operadorRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        return new ModelAndView("operadorForm")
                .addObject("operador", operador);
    }

    @PostMapping("/{id}/operador/edit")
    ModelAndView actualizar(
            @PathVariable Integer id,
            @Validated  Operador operador,
            BindingResult bindingResult,
            RedirectAttributes ra
    ){
        operadorRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        if(bindingResult.hasErrors()){
            return new ModelAndView("operadorForm")
                    .addObject("operador", operador);
        }
        operador.setId(id);
        operadorRepository.save(operador);
        ra.addFlashAttribute("msgExito", "editado");
        return new ModelAndView("redirect:/operadorIndex");
    }

}
