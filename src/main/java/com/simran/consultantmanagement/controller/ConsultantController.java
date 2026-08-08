package com.simran.consultantmanagement.controller;

import com.simran.consultantmanagement.entity.Consultant;
import com.simran.consultantmanagement.service.ConsultantService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/consultants")
public class ConsultantController {

    private final ConsultantService consultantService;

    public ConsultantController(ConsultantService consultantService) {
        this.consultantService = consultantService;
    }

    // Display all consultants and search consultants
    @GetMapping
    public String listConsultants(
            @RequestParam(required = false) String keyword,
            Model model) {

        if (keyword != null && !keyword.isBlank()) {
            model.addAttribute(
                    "consultants",
                    consultantService.searchConsultants(keyword)
            );
        } else {
            model.addAttribute(
                    "consultants",
                    consultantService.getAllConsultants()
            );
        }

        model.addAttribute("keyword", keyword);

        return "consultants";
    }

    // Show Add Consultant form
    @GetMapping("/new")
    public String showCreateForm(Model model) {

        model.addAttribute("consultant", new Consultant());

        return "consultant-form";
    }

    // Save Consultant
    @PostMapping("/save")
    public String saveConsultant(
            @Valid @ModelAttribute("consultant") Consultant consultant,
            BindingResult result) {

        if (result.hasErrors()) {
            return "consultant-form";
        }

        consultantService.saveConsultant(consultant);

        return "redirect:/consultants";
    }

    // Show Edit Consultant form
    @GetMapping("/edit/{id}")
    public String showEditForm(
            @PathVariable Long id,
            Model model) {

        Consultant consultant = consultantService.getConsultantById(id);

        model.addAttribute("consultant", consultant);

        return "consultant-form";
    }

    // Delete Consultant
    @GetMapping("/delete/{id}")
    public String deleteConsultant(@PathVariable Long id) {

        consultantService.deleteConsultant(id);

        return "redirect:/consultants";
    }

    // Dashboard
    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        long totalConsultants =
                consultantService.getAllConsultants().size();

        model.addAttribute("totalConsultants", totalConsultants);

        return "dashboard";
    }

}