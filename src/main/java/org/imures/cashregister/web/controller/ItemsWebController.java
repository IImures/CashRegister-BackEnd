package org.imures.cashregister.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.imures.cashregister.items.controller.response.ItemResponse;
import org.imures.cashregister.items.service.ItemService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Controller
@RequiredArgsConstructor
public class ItemsWebController {

    private final ItemService itemService;

    @PreAuthorize("permitAll()")
    @GetMapping("/items")
    public String items(HttpServletRequest request, Model model) {
        if(request.isUserInRole("ADMIN")){
            model.addAttribute("name", "Admin");
        }
        Page<ItemResponse> page = itemService.getAllItems(PageRequest.of(0, 10));
        ArrayList<ItemResponse> fromPageToArray = new ArrayList<>(page.getContent());
        model.addAttribute("items",fromPageToArray);
        return "items";
    }



}
