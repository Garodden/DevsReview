package com.example.KeyboardArenaProject.service.freeBoard;

import com.example.KeyboardArenaProject.entity.Board;
import com.example.KeyboardArenaProject.entity.UserEntity;
import com.example.KeyboardArenaProject.repository.FreeBoardRepository;
import com.example.KeyboardArenaProject.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public Board findByBoardId(String id){
        return freeBoardRepository.findById(id).orElseThrow();
    }

    public UserEntity findWriter(String boardId){
        Board board = findByBoardId(boardId);
        Optional<UserEntity> user = userRepository.findById(board.getId());

        return user.orElseGet(() -> new UserEntity(".", ".", ".", "unknown", 1, 0, "@", ".", ".", false));


    }

}
