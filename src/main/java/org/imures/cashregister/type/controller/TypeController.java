package org.imures.cashregister.type.controller;


import lombok.RequiredArgsConstructor;
import org.imures.cashregister.type.controller.reponse.TypeResponse;
import org.imures.cashregister.type.controller.request.TypeRequest;
import org.imures.cashregister.type.service.TypeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/types")
@RequiredArgsConstructor
public class TypeController {

    private final TypeService typeService;

    @GetMapping
    public ResponseEntity<Page<TypeResponse>> getAllItems(
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
        Page<TypeResponse> response = typeService.getAllItems(pageRequest);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TypeResponse> createItem(@RequestBody TypeRequest typeRequest) {
        return new ResponseEntity<>(typeService.createType(typeRequest), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TypeResponse> getItem(@PathVariable Long id) {
        return new ResponseEntity<>(typeService.getType(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        typeService.deleteType(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
