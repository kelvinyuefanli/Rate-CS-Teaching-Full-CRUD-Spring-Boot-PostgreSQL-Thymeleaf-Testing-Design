package ca.sfu.cmpt276.staffrating.controller;

import ca.sfu.cmpt276.staffrating.design.StaffProfileResolver;
import ca.sfu.cmpt276.staffrating.model.RoleType;
import ca.sfu.cmpt276.staffrating.model.StaffRating;
import ca.sfu.cmpt276.staffrating.service.StaffRatingService;
import ca.sfu.cmpt276.staffrating.validation.StaffRatingValidator;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/ratings")
public class StaffRatingController {
    private final StaffRatingService service;
    private final StaffProfileResolver profileResolver;
    private final StaffRatingValidator validator;

    public StaffRatingController(StaffRatingService service,
                                 StaffProfileResolver profileResolver,
                                 StaffRatingValidator validator) {
        this.service = service;
        this.profileResolver = profileResolver;
        this.validator = validator;
    }

    @GetMapping
    public String listRatings(Model model) {
        model.addAttribute("ratings", service.listAll());
        model.addAttribute("profileResolver", profileResolver);
        return "ratings/index";
    }

    @GetMapping("/{id}")
    public String showRating(@PathVariable Long id, Model model) {
        model.addAttribute("rating", service.getById(id));
        model.addAttribute("profileResolver", profileResolver);
        return "ratings/show";
    }

    @GetMapping("/new")
    public String newRatingForm(Model model) {
        return renderForm(model, new StaffRating());
    }

    @PostMapping
    public String createRating(@Valid @ModelAttribute("rating") StaffRating rating,
                               BindingResult bindingResult,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        validator.rejectDuplicateEmail(null, rating, bindingResult);
        if (bindingResult.hasErrors()) {
            return renderForm(model, rating);
        }
        StaffRating saved = service.saveRating(rating);
        return redirectToDetail(saved, redirectAttributes, "Rating created");
    }

    @GetMapping("/{id}/edit")
    public String editRatingForm(@PathVariable Long id, Model model) {
        return renderForm(model, service.getById(id));
    }

    @PostMapping("/{id}")
    public String updateRating(@PathVariable Long id,
                               @Valid @ModelAttribute("rating") StaffRating rating,
                               BindingResult bindingResult,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        validator.rejectDuplicateEmail(id, rating, bindingResult);
        if (bindingResult.hasErrors()) {
            return renderForm(model, rating);
        }
        rating.setId(id);
        StaffRating saved = service.saveRating(rating);
        return redirectToDetail(saved, redirectAttributes, "Rating updated");
    }

    @PostMapping("/{id}/delete")
    public String deleteRating(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        service.deleteRatingById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Rating deleted");
        return "redirect:/ratings";
    }

    private String renderForm(Model model, StaffRating rating) {
        model.addAttribute("rating", rating);
        model.addAttribute("roleTypes", RoleType.values());
        return "ratings/form";
    }

    private String redirectToDetail(StaffRating saved,
                                    RedirectAttributes redirectAttributes,
                                    String message) {
        redirectAttributes.addFlashAttribute("successMessage", message);
        return "redirect:/ratings/" + saved.getId();
    }
}
