package com.xyz.controlador;


import com.xyz.modelo.Incidencia;
import com.xyz.repo.IncidentesRepository;
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
public class IncidenteController {
        @Autowired
        private IncidentesRepository incidentesRepository;

        @GetMapping("/incidencia")
        ModelAndView operadorindex(){
            List<Incidencia> incidencia = incidentesRepository.findAll();
            return new ModelAndView("incidenciaIndex")
                    .addObject("incidencia", incidencia);
        }

        @GetMapping("/incidente/add")
        ModelAndView add(){
            return new ModelAndView("incidenciaForm")
                    .addObject("incidencia", new Incidencia());
        }

        @PostMapping("/incidente/add")
        ModelAndView crear(@Validated Incidencia incidencia, BindingResult bindingResult, RedirectAttributes ra){
            if(bindingResult.hasErrors()){
                return new ModelAndView("incidenciaForm")
                        .addObject("incidencia", incidencia);
            }
            incidentesRepository.save(incidencia);
            ra.addFlashAttribute("msgExito", "added");
            return new ModelAndView("redirect:/incidenciaIndex");
        }

        @GetMapping("/{id}/incidente/edit")
        ModelAndView edit(@PathVariable Integer id){
            Incidencia incidencia = incidentesRepository.findById(id)
                    .orElseThrow(EntityNotFoundException::new);
            return new ModelAndView("incidenciaForm")
                    .addObject("incidencia", incidencia);
        }

        @PostMapping("/{id}/incidente/edit")
        ModelAndView actualizar(
                @PathVariable Integer id,
                @Validated  Incidencia incidencia,
                BindingResult bindingResult,
                RedirectAttributes ra
        ){
            incidentesRepository.findById(id)
                    .orElseThrow(EntityNotFoundException::new);

            if(bindingResult.hasErrors()){
                return new ModelAndView("incidenciaForm")
                        .addObject("incidencia", incidencia);
            }
            incidencia.setId(id);
            incidentesRepository.save(incidencia);
            ra.addFlashAttribute("msgExito", "editado");
            return new ModelAndView("redirect:/incidenciaIndex");
        }
}
