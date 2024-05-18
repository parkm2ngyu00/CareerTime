package kr.ac.dankook.ace.careertime.controller;

import kr.ac.dankook.ace.careertime.domain.Board;
import kr.ac.dankook.ace.careertime.domain.Comment;
import kr.ac.dankook.ace.careertime.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/boards/{boardId}/comments")
    public Comment createComment(@PathVariable Integer boardId, @RequestBody Comment comment) {
        Board board = new Board(); // 예시로 생성, 실제로는 ID에 해당하는 Board 조회 필요
        board.setPostId(boardId);
//        comment.setBoard(board);
        return commentService.createComment(comment);
    }

    @GetMapping("/boards/{boardId}/comments")
    public List<Comment> getCommentsByBoardId(@PathVariable Integer boardId) {
        return commentService.findCommentsByBoardId(boardId);
    }

    @PutMapping("/comments/{id}")
    public Comment updateComment(@PathVariable Integer id, @RequestBody Comment commentDetails) {
        return commentService.updateComment(id, commentDetails);
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Integer id) {
        return commentService.deleteComment(id);
    }
}