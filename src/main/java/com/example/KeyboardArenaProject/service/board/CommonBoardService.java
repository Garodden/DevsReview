package com.example.KeyboardArenaProject.service.board;

import com.example.KeyboardArenaProject.entity.Board;
import com.example.KeyboardArenaProject.entity.IP;
import com.example.KeyboardArenaProject.entity.User;
import com.example.KeyboardArenaProject.entity.compositeKey.IpCompositeKey;
import com.example.KeyboardArenaProject.repository.CommonBoardRepository;
import com.example.KeyboardArenaProject.repository.IpRepository;
import com.example.KeyboardArenaProject.repository.MyPageRepository;
import com.example.KeyboardArenaProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommonBoardService {
    private final CommonBoardRepository boardRepository;
    private final UserRepository userRepository;
    private final IpRepository ipRepository;
    private final MyPageRepository myPageRepository;

    public List<Board> findAllRankArena(){
        return boardRepository.findAllByBoardType(2);
    }

    public Board findByBoardId(String boardId){
        return boardRepository.findByBoardId( boardId);
    }

    public List<Board> findTop3ArenaOrderByLikes(){
        Pageable topThree = PageRequest.of(0, 3);
        return boardRepository.findArenasOrderByLikeDesc(topThree);

    }

    public List<Board> findNormalArenaOrderByCreatedDate(List<String> topThreeBoardIds){
        return boardRepository.findActiveArenasOrderByCreatedDateDesc(3, topThreeBoardIds);
    }
    @Transactional
    public void saveArena(Board Arena){
        boardRepository.save(Arena);
    }
    public void writeFreeBoard(Board board){
        boardRepository.save(board);
    }

    @Transactional
    public void updateActive(String boardId) {
        Board board = boardRepository.findByBoardId(boardId);
        board.updateToActive();
    }

    @Transactional
    public void deleteBy(String boardId) {
        boardRepository.deleteByBoardId(boardId);
    }

    @Transactional
    public void updateParticipates(String boardId) {
        Board board = boardRepository.findByBoardId(boardId);
        board.updateParticipates();
    }

//
    public List<Board> findAllLikeSortedFreeBoard(){
        return boardRepository.findAllByBoardTypeOrderByLikesDesc(1);
    }

    public List<Board> findAllCreatedSortedBoard(){
        return boardRepository.findAllByBoardTypeOrderByCreatedDateDesc(1);
    }



    public User findWriter(String boardId){
        Board board = findByBoardId(boardId);
        Optional<User> user = userRepository.findById(board.getId());

        return user.orElseGet(() -> new User(".", ".", ".", "unknown", 1, 0, "@", ".", ".",false));


    }

    public void deleteBoard(String boardId){
        boardRepository.deleteById(boardId);
    }

    public void updateBoard(String title, String content, Integer boardRank, String boardId){
        LocalDateTime localDateTime= LocalDateTime.now();
        boardRepository.updateBoard(title,content,boardRank,boardId,localDateTime);
    }

    @Transactional
    public void updateBoardLikes(Integer likes,String boardId){
        boardRepository.updateBoardLikes(likes,boardId);
    }

    //뷰에 관련된 메소드
    public void plusView(String boardId){
        boardRepository.plusView(boardId);
    }

    public boolean isContainsIpAndId(String ip,String boardId,String id){
        IpCompositeKey compositeKey = new IpCompositeKey(ip, boardId,id);
        return ipRepository.findById(compositeKey).orElse(null) != null;
    }

    public void saveIpAndId(String ip,String boardId,String id){
        IpCompositeKey compositeKey = new IpCompositeKey(ip,boardId,id);
        ipRepository.save(new IP(compositeKey));
    }

    //댓글수 올리고 내리는 메소드
    @Transactional
    public void plusCommentsCount(String boardId){
        boardRepository.plusCommentsCount(boardId);
    }

    @Transactional
    public void minusCommentsCount(String boardId){
        boardRepository.minusCommentsCount(boardId);
    }


    //내가 작성한 보드 가져오는 메소드
    public List<Board> getMyBoards(String id){
        return myPageRepository.findAllByIdOrderByCreatedDateDesc(id);
    }

    @Transactional
    public void setBoardRankMax(Board board,Integer max){
        board.setBoardRankMax(max);
    }


}
