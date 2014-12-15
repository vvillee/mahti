package org.mahti.herbarium.controller;

import java.io.IOException;
import java.math.BigInteger;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.security.Principal;
import org.mahti.herbarium.domain.Plant;
import org.mahti.herbarium.domain.User;
import org.mahti.herbarium.repository.PlantRepository;
import org.mahti.herbarium.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/plants")
public class PlantController {

    @Autowired
    private PlantRepository plantRepository;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET)
    public String showAllPlantsWithFilter() {
        return "redirect:/";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String viewSinglePlant(Model model, @PathVariable Long id) {
		Plant plant = plantRepository.findOne(id);
        model.addAttribute("plant", plant);
		model.addAttribute("binomial", plant.getGenus() + " " + plant.getSpecies());
        return "plant";
    }

    @ResponseBody
    @RequestMapping(value = "/{id}/content", produces = "image/*")
    public byte[] showImage(@PathVariable Long id) {
        return plantRepository.findOne(id).getContent();
    }

    @Transactional
    @RequestMapping(value = "/{userId}/upload", method = RequestMethod.POST)
    public String postImage(Model model
            , @PathVariable Long userId
            , @RequestParam("file") MultipartFile file
            , @RequestParam(required = false, defaultValue = "") String family
            , @RequestParam(required = false, defaultValue = "") String genus
            , @RequestParam(required = false, defaultValue = "") String species
            , @Valid @RequestParam("name") String name
            ) throws IOException {

        if (file.getContentType().equals("image/gif")
                || file.getContentType().equals("image/png")
                || file.getContentType().equals("image/jpg")
                || file.getContentType().equals("image/jpeg")) {

            Plant plant = new Plant();

            if (family.trim().length() == 0
                    || genus.trim().length() == 0
                    || species.trim().length() == 0) {
                plant.setIdentified(false);
                    } else {
                        plant.setIdentified(true);
                    }

            plant.setContent(file.getBytes());
            plant.setFamily(family);
            plant.setGenus(genus);
            plant.setSpecies(species);
            plant.setName(name);
            plant = plantRepository.save(plant);

            userRepository.findOne(userId).getPlants().add(plant);
        }

        return "redirect:/upload";
    }

    @Transactional
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
    public String delete(@PathVariable Long id) {
        plantRepository.delete(id);
        return "redirect:/user";
    }
}
