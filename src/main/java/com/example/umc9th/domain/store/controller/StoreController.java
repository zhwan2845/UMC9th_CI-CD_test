package com.example.umc9th.domain.store.controller;

import com.example.umc9th.domain.store.dto.StoreResponseDTO;
import com.example.umc9th.domain.store.service.StoreQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stores")
public class StoreController {

    private final StoreQueryService storeQueryService;

    // GET /stores/search?region=강남구&keyword=민트%20초코&sort=name&page=0&size=10
    @GetMapping("/search")
    public StoreResponseDTO.SearchPage searchStores(
            @RequestParam(required = false) String region,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false, defaultValue = "latest") String sort,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        return storeQueryService.searchStores(region, keyword, sort, page, size);
    }
}
