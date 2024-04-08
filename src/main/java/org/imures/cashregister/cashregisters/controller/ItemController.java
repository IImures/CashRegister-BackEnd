package org.imures.cashregister.cashregisters.controller;

import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.imures.cashregister.cashregisters.controller.request.ItemRequest;
import org.imures.cashregister.cashregisters.controller.response.ItemResponse;
import org.imures.cashregister.cashregisters.service.ItemService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    @GetMapping
    public ResponseEntity<Page<ItemResponse>> getAllItems(
             @RequestParam(
                     value = "page",
                     required = false,
                     defaultValue = "0"
             ) int page,
             @RequestParam(
                     value = "limit",
                     required = false,
                     defaultValue = "10" // 10 - default 100 - max
             ) int limit,
             @RequestParam(
                     value = "sort",
                     required = false,
                     defaultValue = "id") String sortBy,
             @RequestParam(
                     value = "direction",
                     required = false) boolean isDesc

     ){
         if(limit > 100) {
             //throw new TooManyEntities("Limit must be less than 100");
             throw new IllegalArgumentException("Limit must be less than 100");
         }
         Pageable pageRequest;
         if(!isDesc){
             pageRequest = PageRequest.of(page, limit, Sort.by(sortBy).ascending());
         } else {
             pageRequest = PageRequest.of(page, limit, Sort.by(sortBy).descending());
         }
         Page<ItemResponse> response = itemService.getAllItems(pageRequest);

         return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<ItemResponse> createItem(@RequestBody ItemRequest itemRequest) {
        return new ResponseEntity<>(itemService.createItem(itemRequest), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemResponse> getItem(@PathVariable Long id) {
        return new ResponseEntity<>(itemService.getItem(id), HttpStatus.OK);
    }
//
//     @PutMapping("/{id}")
//     public ItemResponse updateItem(@PathVariable Long id, @RequestBody ItemRequest itemRequest) {
//         return itemService.updateItem(id, itemRequest);
//     }
//
     @DeleteMapping("/{id}")
     public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
         itemService.deleteItem(id);
         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
     }

}
