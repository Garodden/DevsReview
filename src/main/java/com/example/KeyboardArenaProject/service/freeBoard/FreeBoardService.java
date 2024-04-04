package com.example.KeyboardArenaProject.service.freeBoard;


import com.example.KeyboardArenaProject.entity.Board;
import com.example.KeyboardArenaProject.entity.IP;
import com.example.KeyboardArenaProject.entity.User;
import com.example.KeyboardArenaProject.entity.compositeKey.IpCompositeKey;
import com.example.KeyboardArenaProject.repository.FreeBoardRepository;

import com.example.KeyboardArenaProject.repository.IpRepository;
import com.example.KeyboardArenaProject.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class FreeBoardService {
    private final FreeBoardRepository freeBoardRepository;
    private final UserRepository userRepository;
    private final IpRepository ipRepository;

    public void writeFreeBoard(Board board){
        freeBoardRepository.save(board);
    }


    public List<Board> findAllLikeSortedFreeBoard(){
        return freeBoardRepository.findAllByBoardTypeOrderByLikesDesc(1);
    }

    public List<Board> findAllCreatedSortedBoard(){
        return freeBoardRepository.findAllByBoardTypeOrderByCreatedDateDesc(1);
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

    public void plusView(String boardId){
        freeBoardRepository.plusView(boardId);
    }

    public boolean isContainsIpAndId(String ip,String boardId,String id){
        IpCompositeKey compositeKey = new IpCompositeKey(ip, boardId,id);
        return ipRepository.findById(compositeKey).orElse(null) != null;
    }

    public void saveIpAndId(String ip,String boardId,String id){
        IpCompositeKey compositeKey = new IpCompositeKey(ip,boardId,id);
        ipRepository.save(new IP(compositeKey));
    }

    @Transactional
    public void updateBoardLikes(Integer likes,String boardId){
        freeBoardRepository.updateBoardLikes(likes,boardId);
    }

    @Transactional
    public void plusCommentsCount(String boardId){
        freeBoardRepository.plusCommentsCount(boardId);
    }

    @Transactional
    public void minusCommentsCount(String boardId){
        freeBoardRepository.minusCommentsCount(boardId);
    }


}