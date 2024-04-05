package com.example.KeyboardArenaProject.service;


import com.example.KeyboardArenaProject.dto.CommentResponse;
import com.example.KeyboardArenaProject.entity.Comment;
import com.example.KeyboardArenaProject.repository.CommentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    CommentRepository commentRepository;
    public CommentService(CommentRepository commentRepository){
        this.commentRepository = commentRepository;
    }

    public List<Comment> findCommentsByBoardId(String BoardId){
        return commentRepository.findAllByBoardIdOrderByCreatedDateDesc(BoardId);
    }

    public void saveComment(Comment comment){
        commentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(String commentId) {
        commentRepository.deleteById(commentId);
    }

    public Comment findCommentById(String commentId){
        return commentRepository.findById(commentId).orElseThrow();
    }

}
