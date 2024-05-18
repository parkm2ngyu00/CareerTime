package kr.ac.dankook.ace.careertime.service;

import kr.ac.dankook.ace.careertime.domain.Comment;
import kr.ac.dankook.ace.careertime.repository.CommentRepository;
import kr.ac.dankook.ace.careertime.config.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public List<Comment> findCommentsByBoardId(Integer boardId) {
        return commentRepository.findAll()
                .stream()
                .filter(c -> c.getBoard().getPostId().equals(boardId))
                .collect(Collectors.toList());
    }

    public Comment updateComment(Integer id, Comment commentDetails) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + id));
        comment.setComment_date(commentDetails.getComment_date()); // 필드명 변경에 따른 메서드 수정
        // 추가로 변경해야 할 필드가 있다면 여기에 로직 추가
        comment.setUser_id(commentDetails.getUser_id()); // userId 업데이트
        return commentRepository.save(comment);
    }

    public ResponseEntity<Void> deleteComment(Integer id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + id));
        commentRepository.delete(comment);
        return ResponseEntity.ok().build();
    }
}
