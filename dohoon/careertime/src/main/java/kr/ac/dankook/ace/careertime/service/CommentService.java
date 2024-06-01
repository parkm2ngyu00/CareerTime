package kr.ac.dankook.ace.careertime.service;

import kr.ac.dankook.ace.careertime.domain.Board;
import kr.ac.dankook.ace.careertime.domain.Comment;
import kr.ac.dankook.ace.careertime.domain.User;
import kr.ac.dankook.ace.careertime.dto.CommentResponse;
import kr.ac.dankook.ace.careertime.dto.CommentSummaryResponse;
import kr.ac.dankook.ace.careertime.repository.BoardRepository;
import kr.ac.dankook.ace.careertime.repository.CommentRepository;
import kr.ac.dankook.ace.careertime.config.ResourceNotFoundException;
import kr.ac.dankook.ace.careertime.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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

    // CommentResponse 객체를 반환하도록 변경
    public CommentResponse createComment(Long boardId, Comment comment) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid board ID: " + boardId));
        comment.setBoard(board);
        User user = userRepository.findById(comment.getUser().getUser_id())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + comment.getUser().getUser_id()));
        comment.setUser(user);
        Comment savedComment = commentRepository.save(comment);
        return mapToCommentResponse(savedComment); // CommentResponse로 매핑
    }

    public List<CommentResponse> findCommentsByBoardId(Long boardId) {
        return commentRepository.findAll()
                .stream()
                .filter(c -> c.getBoard().getPost_id().equals(boardId))
                .map(this::mapToCommentResponse)
                .collect(Collectors.toList());
    }

    private CommentResponse mapToCommentResponse(Comment comment) {
        User user = comment.getUser();
        return new CommentResponse(
                comment.getComment_id(),
                user.getUsername(),
                user.getEmail(),
                user.getName(),
                user.getUser_type(),
                user.getUser_id(),
                comment.getComment_rate().intValue(),
                comment.getComment_text()
        );
    }

    // CommentResponse 객체를 반환하도록 변경
    public CommentResponse updateComment(Long id, Comment commentDetails) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found with id: " + id));
        comment.setComment_text(commentDetails.getComment_text());
        comment.setComment_date(commentDetails.getComment_date());
        comment.setComment_rate(commentDetails.getComment_rate());
        Comment updatedComment = commentRepository.save(comment);
        return mapToCommentResponse(updatedComment); // CommentResponse로 매핑
    }

    public void deleteComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found with id: " + id));
        commentRepository.delete(comment);
    }

    public CommentSummaryResponse getCommentSummaryByBoardId(Long boardId) {
        List<CommentResponse> comments = findCommentsByBoardId(boardId);
        int reviewCount = comments.size();
        double averageRating = comments.stream().mapToDouble(CommentResponse::getRating).average().orElse(0.0);
        return new CommentSummaryResponse(reviewCount, averageRating, comments);
    }
}