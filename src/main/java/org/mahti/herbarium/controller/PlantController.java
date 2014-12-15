package org.mahti.herbarium.controller;

import java.io.IOException;
import java.util.Date;
import javax.transaction.Transactional;
import org.mahti.herbarium.domain.Plant;
import org.mahti.herbarium.repository.PlantRepository;
import org.mahti.herbarium.service.PlantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    private PlantService plantService;

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

    @RequestMapping(method = RequestMethod.POST)
    public String postImage(@RequestParam("file") MultipartFile file
            , @RequestParam("username") String username
            , @RequestParam(required = false, defaultValue = "") String family
            , @RequestParam(required = false, defaultValue = "") String genus
            , @RequestParam(required = false, defaultValue = "") String species
            , @RequestParam("name") String name) throws IOException {
        if (file.getContentType().equals("image/gif")
                || file.getContentType().equals("image/png")
                || file.getContentType().equals("image/jpg")
                || file.getContentType().equals("image/jpeg")) {
            Plant plant = new Plant();
			plant.setIdentified(true);
            plant.setContent(file.getBytes());
            plant.setUser(username);
            plant.setFamily(family);
            plant.setGenus(genus);
            plant.setSpecies(species);
            plant.setName(name);

            if (true) {
                
            }

            plantRepository.save(plant);
        }
        return "redirect:/user";
    }

    @Transactional
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
    public String delete(@PathVariable Long id) {
        plantRepository.delete(id);
        return "redirect:/user";
    }
    
    @RequestMapping(value = "/{id}/comment", method = RequestMethod.POST)
    public String postComment(
            Model model, 
            @PathVariable Long id, 
            @RequestParam("userId") long commenterUserId,
            @RequestParam("comment") String commentString) {
        
        plantService.addComment(id, commentString, new Date(), commenterUserId);
        
        Plant plant = plantRepository.findOne(id);        
        model.addAttribute("plant", plant);
        model.addAttribute("binomial", plant.getGenus() + " " + plant.getSpecies());
        
        return "plant";
        
    }
    
}
