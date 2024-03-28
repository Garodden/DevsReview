package com.example.KeyboardArenaProject.service.freeBoard;


import com.example.KeyboardArenaProject.entity.Board;
import com.example.KeyboardArenaProject.entity.User;
import com.example.KeyboardArenaProject.repository.FreeBoardRepository;

import com.example.KeyboardArenaProject.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class FreeBoardService {
    private final FreeBoardRepository freeBoardRepository;
    private final UserRepository userRepository;

    public void writeFreeBoard(Board board){
        freeBoardRepository.save(board);
    }


    //    public String FindUserPkById(){
//        String user_id = principal.getName();
////        return userRepository.findByUser_id(user_id).orElseThrow().getId();
//    }
    public List<Board> findAllSortedFreeBoard(){
        return freeBoardRepository.findAllByBoardTypeOrderByLikeDesc(1);
    }

    public Board findByBoardId(String boardId){
        return freeBoardRepository.findById(boardId).orElseThrow();
    }

    public User findWriter(String boardId){
        Board board = findByBoardId(boardId);
        Optional<User> user = userRepository.findById(board.getId());

        return user.orElseGet(() -> new User(".", ".", ".", "unknown", 1, 0, "@", ".", ".",false));


    }

    public void updateBoard(String title, String content, Integer boardRank, String boardId){
        LocalDateTime localDateTime= LocalDateTime.now();
        freeBoardRepository.updateBoard(title,content,boardRank,boardId,localDateTime);
    }

    public void deleteBoard(String boardId){
        freeBoardRepository.deleteById(boardId);
    }

}