package com.simran.consultantmanagement.controller;

import com.simran.consultantmanagement.entity.Consultant;
import com.simran.consultantmanagement.service.ConsultantService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/consultants")
public class ConsultantController {

    private final ConsultantService consultantService;

    public ConsultantController(ConsultantService consultantService) {
        this.consultantService = consultantService;
    }


    // ============================================================
    // LIST CONSULTANTS
    // SEARCH + FILTER + SORTING + PAGINATION
    // ============================================================

    @GetMapping
    public String listConsultants(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection,
            Model model) {

        var consultants = consultantService.getConsultants(
                keyword,
                status,
                page,
                size,
                sortBy,
                sortDirection
        );

        model.addAttribute("consultants", consultants);
        model.addAttribute("keyword", keyword);
        model.addAttribute("status", status);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDirection", sortDirection);

        return "consultants";
    }


    // ============================================================
    // SHOW ADD CONSULTANT FORM
    // ============================================================

    @GetMapping("/new")
    public String showCreateForm(Model model) {

        model.addAttribute(
                "consultant",
                new Consultant()
        );

        return "consultant-form";
    }


    // ============================================================
    // SAVE / UPDATE CONSULTANT
    // ============================================================

    @PostMapping("/save")
    public String saveConsultant(
            @Valid @ModelAttribute("consultant") Consultant consultant,
            BindingResult result,
            RedirectAttributes redirectAttributes) {

        // If validation fails, return to form
        if (result.hasErrors()) {
            return "consultant-form";
        }

        // Check whether this is ADD or UPDATE
        boolean isNew = consultant.getId() == null;

        // Save consultant
        consultantService.saveConsultant(consultant);

        // Notification
        if (isNew) {

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Consultant added successfully!"
            );

        } else {

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Consultant updated successfully!"
            );
        }

        return "redirect:/consultants";
    }


    // ============================================================
    // SHOW EDIT CONSULTANT FORM
    // ============================================================

    @GetMapping("/edit/{id}")
    public String showEditForm(
            @PathVariable Long id,
            Model model) {

        Consultant consultant =
                consultantService.getConsultantById(id);

        // If consultant doesn't exist
        if (consultant == null) {
            return "redirect:/consultants";
        }

        model.addAttribute(
                "consultant",
                consultant
        );

        return "consultant-form";
    }


    // ============================================================
    // DELETE CONSULTANT
    // ============================================================

    @GetMapping("/delete/{id}")
    public String deleteConsultant(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        consultantService.deleteConsultant(id);

        // Notification
        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Consultant deleted successfully!"
        );

        return "redirect:/consultants";
    }


    // ============================================================
    // DASHBOARD
    // ============================================================

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        long totalConsultants =
                consultantService.getTotalConsultants();

        long activeConsultants =
                consultantService.getActiveConsultants();

        long availableConsultants =
                consultantService.getAvailableConsultants();

        long onProjectConsultants =
                consultantService.getOnProjectConsultants();

        long newConsultants =
                consultantService.getNewConsultants();

        long inactiveConsultants =
                consultantService.getInactiveConsultants();


        model.addAttribute(
                "totalConsultants",
                totalConsultants
        );

        model.addAttribute(
                "activeConsultants",
                activeConsultants
        );

        model.addAttribute(
                "availableConsultants",
                availableConsultants
        );

        model.addAttribute(
                "onProjectConsultants",
                onProjectConsultants
        );

        model.addAttribute(
                "newConsultants",
                newConsultants
        );

        model.addAttribute(
                "inactiveConsultants",
                inactiveConsultants
        );


        return "dashboard";
    }


    // ============================================================
    // REPORTS
    // ============================================================

    @GetMapping("/reports")
    public String reports(Model model) {

        long totalConsultants =
                consultantService.getTotalConsultants();

        long activeConsultants =
                consultantService.getActiveConsultants();

        long availableConsultants =
                consultantService.getAvailableConsultants();

        long onProjectConsultants =
                consultantService.getOnProjectConsultants();

        long inactiveConsultants =
                consultantService.getInactiveConsultants();

        long newConsultants =
                consultantService.getNewConsultants();


        // Average experience
        double averageExperience =
                consultantService.getAllConsultants()
                        .stream()
                        .filter(c -> c.getExperience() != null)
                        .mapToInt(Consultant::getExperience)
                        .average()
                        .orElse(0.0);


        model.addAttribute(
                "totalConsultants",
                totalConsultants
        );

        model.addAttribute(
                "activeConsultants",
                activeConsultants
        );

        model.addAttribute(
                "availableConsultants",
                availableConsultants
        );

        model.addAttribute(
                "onProjectConsultants",
                onProjectConsultants
        );

        model.addAttribute(
                "inactiveConsultants",
                inactiveConsultants
        );

        model.addAttribute(
                "newConsultants",
                newConsultants
        );

        model.addAttribute(
                "averageExperience",
                averageExperience
        );


        return "reports";
    }
}