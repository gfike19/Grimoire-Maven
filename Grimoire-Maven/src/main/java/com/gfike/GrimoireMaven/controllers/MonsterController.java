package com.gfike.GrimoireMaven.controllers;

import com.gfike.GrimoireMaven.models.data.MonsterDao;
import com.gfike.GrimoireMaven.models.Monster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MonsterController {

    @Autowired
    MonsterDao monsterDao;

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String index(){
        return "index";
    }

    @RequestMapping(value = "/addMons",method = RequestMethod.GET)
    public String addMonsGet(Model model){
        model.addAttribute(new Monster());
        return "addMons";
    }

    @RequestMapping(value = "/addMons",method = RequestMethod.POST)
    public String addMonsPost(@ModelAttribute @Valid Monster newMons,
                              Errors errors, Model model){
        if (errors.hasErrors()) {
            return "addMons";
        }

        monsterDao.save(newMons);
        String msg = newMons.getName() + " has been added to the Grimoire!";
        model.addAttribute("msg", msg);
        return "redirect:addMons";
    }

    @RequestMapping(value = "/addSighting",method = RequestMethod.GET)
    public String addSightingGet(Model model, HttpSession session){
        if(session.getAttribute("msg") != null){
            String msg = (String) session.getAttribute("msg");
            model.addAttribute("msg", msg);
        }
        ArrayList<Monster> allMons = (ArrayList<Monster>) monsterDao.findAll();
        model.addAttribute("allMons", allMons);
        return "addSighting";
    }

    @RequestMapping(value = "/addSighting",method = RequestMethod.POST)
    public String addSightingPost(Model model, @RequestParam List<String> newCount,
                                  HttpSession session){
        ArrayList<Monster> allMons = (ArrayList<Monster>) monsterDao.findAll();

        for(int i = 0; i < newCount.size(); i++){
            int count = Integer.parseInt(newCount.get(i));
            if (count > 0) {
                Monster m = allMons.get(i);
                int currentCount = m.getCount();
                m.setCount(currentCount + count);
                monsterDao.save(m);
            }
        }
        return "redirect:addSighting";
    }

    @RequestMapping(value="/error", method = RequestMethod.GET)
    public String error () {
        return "error";
    }
}
