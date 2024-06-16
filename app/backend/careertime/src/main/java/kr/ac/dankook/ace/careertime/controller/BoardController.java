package kr.ac.dankook.ace.careertime.controller;

import java.util.Arrays;
import java.util.List;

import kr.ac.dankook.ace.careertime.domain.Board;
import kr.ac.dankook.ace.careertime.domain.Profile;
import kr.ac.dankook.ace.careertime.domain.User;
import kr.ac.dankook.ace.careertime.dto.BoardRequest;
import kr.ac.dankook.ace.careertime.dto.BoardResponse;
import kr.ac.dankook.ace.careertime.dto.UserInfo;
import kr.ac.dankook.ace.careertime.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @PostMapping("/{userId}")
    public ResponseEntity<BoardResponse> createBoard(
            @PathVariable("userId") Long userId,
            @RequestBody BoardRequest boardRequest) {

        BoardResponse createdBoard = boardService.createBoard(
                userId,
                boardRequest.getTitle(),
                boardRequest.getHashtags(),
                boardRequest.getContent()
        );

        return new ResponseEntity<>(createdBoard, HttpStatus.CREATED);
    }

    @GetMapping("/search")
    public ResponseEntity<List<BoardResponse>> searchBoards(@RequestParam("target") String target) {
        List<BoardResponse> boards = boardService.searchBoards(target);
        return new ResponseEntity<>(boards, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<BoardResponse>> getAllBoards() {
        List<BoardResponse> boards = boardService.getAllBoards();
        return new ResponseEntity<>(boards, HttpStatus.OK);
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<BoardResponse> getBoardById(@PathVariable("boardId") Long boardId) {
        BoardResponse boardResponse = boardService.getBoardById(boardId);
        return new ResponseEntity<>(boardResponse, HttpStatus.OK);
    }

    @PutMapping("/{boardId}")
    public ResponseEntity<BoardResponse> updateBoard(
            @PathVariable("boardId") Long boardId,
            @RequestBody BoardRequest boardRequest) {

        BoardResponse updatedBoard = boardService.updateBoard(
                boardId,
                boardRequest.getTitle(),
                boardRequest.getHashtags(),
                boardRequest.getContent()
        );

        return new ResponseEntity<>(updatedBoard, HttpStatus.OK);
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> deleteBoard(@PathVariable("boardId") Long boardId) {
        boardService.deleteBoard(boardId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}