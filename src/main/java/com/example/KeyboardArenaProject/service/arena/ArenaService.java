package com.example.KeyboardArenaProject.service.arena;

import com.example.KeyboardArenaProject.entity.BoardEntity;
import com.example.KeyboardArenaProject.repository.BoardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;

@Service
public class ArenaService {
    private BoardRepository boardRepository;

    public ArenaService(BoardRepository boardRepository){
        this.boardRepository = boardRepository;
    }

    public List<BoardEntity> findAllRankArena(){
        return boardRepository.findAllByBoardType(2);
    }

    public List<BoardEntity> findTop3ArenaOrderByLikes(){
        return boardRepository.findTop3ArenaByOrderByLikeDesc();
    }

    public List<BoardEntity> findRestArenaOrderByDate(){
        return boardRepository.findRestArenaOrderByCreatedDateDesc();
    }

    /*
    @Transactional
    public void saveArena(String id,
                          String boardId,
                          String title,
                          String content,
                          int boardType,
                          LocalDateTime created_date,
                          LocalDateTime updated_date,
                          int boardRank,
                          int like,
                          Boolean active,
                          int view,
                          int comment
                          ){
        BoardEntity board = new BoardEntity(id, boardId,
                                            title,
                                            content,
                                            boardType,
                                            created_date,
                                            updated_date,
                                            boardRank,
                                            like,
                                            active,
                                            view,
                                            comment);
        boardRepository.save(board);
    }
     */

}
