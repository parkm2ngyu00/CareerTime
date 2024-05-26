package kr.ac.dankook.ace.careertime.service;

import kr.ac.dankook.ace.careertime.domain.Comment;
import kr.ac.dankook.ace.careertime.domain.User;
import kr.ac.dankook.ace.careertime.dto.CommentResponse;
import kr.ac.dankook.ace.careertime.dto.CommentSummaryResponse;
import kr.ac.dankook.ace.careertime.repository.BoardRepository;
import kr.ac.dankook.ace.careertime.repository.CommentRepository;
import kr.ac.dankook.ace.careertime.config.ResourceNotFoundException;
import kr.ac.dankook.ace.careertime.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public List<Comment> findCommentsByBoardId(Long boardId) {
        return commentRepository.findAll()
                .stream()
                .filter(c -> c.getBoard().getPost_id().equals(boardId))
                .collect(Collectors.toList());
    }

    public Comment updateComment(Long id, Comment commentDetails) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + id));
        comment.setComment_text(commentDetails.getComment_text());
        comment.setComment_date(commentDetails.getComment_date());
        return commentRepository.save(comment);
    }

    public ResponseEntity<Void> deleteComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + id));
        commentRepository.delete(comment);
        return ResponseEntity.ok().build();
    }

    public CommentSummaryResponse getCommentSummaryByBoardId(Long boardId) {
        List<Comment> comments = findCommentsByBoardId(boardId);
        int reviewCount = comments.size();
        double averageRating = comments.stream().mapToDouble(Comment::getComment_rate).average().orElse(0.0);
        List<CommentResponse> reviewList = comments.stream()
                .map(comment -> {
                    User user = comment.getUser();
                    return new CommentResponse(user.getName(), comment.getComment_rate().intValue(), comment.getComment_text());
                })
                .collect(Collectors.toList());
        return new CommentSummaryResponse(reviewCount, averageRating, reviewList);
    }
}