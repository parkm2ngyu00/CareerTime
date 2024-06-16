package kr.ac.dankook.ace.careertime.controller;

import kr.ac.dankook.ace.careertime.domain.Board;
import kr.ac.dankook.ace.careertime.domain.Comment;
import kr.ac.dankook.ace.careertime.dto.CommentResponse;
import kr.ac.dankook.ace.careertime.dto.CommentSummaryResponse;
import kr.ac.dankook.ace.careertime.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/boards/{boardId}/comments")
    public ResponseEntity<CommentResponse> createComment(@PathVariable("boardId") Long boardId, @RequestBody Comment comment) {
        CommentResponse createdComment = commentService.createComment(boardId, comment);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @GetMapping("/boards/{boardId}/comments")
    public ResponseEntity<List<CommentResponse>> getCommentsByBoardId(@PathVariable("boardId") Long boardId) {
        List<CommentResponse> comments = commentService.findCommentsByBoardId(boardId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PutMapping("/boards/{boardId}/comments")
    public ResponseEntity<CommentResponse> updateComment(@PathVariable("boardId") Long id, @RequestBody Comment commentDetails) {
        CommentResponse updatedComment = commentService.updateComment(id, commentDetails);
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }

    @DeleteMapping("/boards/{boardId}/comments")
    public ResponseEntity<Void> deleteComment(@PathVariable("boardId") Long id) {
        commentService.deleteComment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/boards/{boardId}/comments/summary")
    public ResponseEntity<CommentSummaryResponse> getCommentSummaryByBoardId(@PathVariable("boardId") Long boardId) {
        CommentSummaryResponse summary = commentService.getCommentSummaryByBoardId(boardId);
        return new ResponseEntity<>(summary, HttpStatus.OK);
    }
}