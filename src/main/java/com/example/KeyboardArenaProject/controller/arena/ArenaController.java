package com.example.KeyboardArenaProject.controller.arena;

import com.example.KeyboardArenaProject.dto.arena.ArenaResponse;
import com.example.KeyboardArenaProject.entity.Board;
import com.example.KeyboardArenaProject.service.arena.ArenaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "아레나 CRUD")
@RestController
public class ArenaController {
    private final ArenaService arenaService;
    public ArenaController(ArenaService arenaService){ this.arenaService = arenaService;}
    @GetMapping("/arenas")
    public List<ArenaResponse> showArena() {

        List<Board> arenaList = arenaService.findAllRankArena();
        arenaList.addAll(arenaService.findTop3ArenaOrderByLikes());
        arenaList.addAll(arenaService.findNormalArenaOrderByCreatedDate());
        
        List<ArenaResponse> ArenaResponseList = arenaList.stream()
                .map(ArenaResponse::new)
                .toList();

        return ArenaResponseList;
        
    }



}


