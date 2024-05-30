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

@CrossOrigin(origins = "http://localhost:3000") // CORS 허용(일반적으로 프론트엔드 개발 서버)에서 API 서버에 접근할 수 있도록 허용
//지정된 출처 외의 다른 출처에서는 API에 접근할 수 없도록 함
@RestController // RESTful 컨트롤러임을 선언
@RequestMapping("/api") // 모든 요청 URL의 기본 경로
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @PostMapping("/boards")
    public ResponseEntity<Board> createBoard(
            @RequestParam("userId") Long userId,
            @RequestBody BoardRequest boardRequest) {

        Board createdBoard = boardService.createBoard(
                userId,
                boardRequest.getTitle(),
                boardRequest.getHashtags(),
                boardRequest.getContent()
        );

        return new ResponseEntity<>(createdBoard, HttpStatus.CREATED);
    }

    @GetMapping("/boards/search")
    public ResponseEntity<List<BoardResponse>> searchBoards(@RequestParam("target") String target) {
        List<BoardResponse> boards = boardService.searchBoards(target);
        return new ResponseEntity<>(boards, HttpStatus.OK);
    }

    @GetMapping("/boards")
    public ResponseEntity<List<Board>> getAllBoards() {
        List<Board> boards = boardService.getAllBoards();
        return new ResponseEntity<>(boards, HttpStatus.OK);
    }

    @GetMapping("/boards/{boardId}")
    public ResponseEntity<BoardResponse> getBoardById(@PathVariable("boardId") Long boardId) {
        BoardResponse boardResponse = boardService.getBoardById(boardId);
        return new ResponseEntity<>(boardResponse, HttpStatus.OK);
    }

    @PutMapping("/boards/{boardId}")
    public ResponseEntity<Board> updateBoard(
            @PathVariable("boardId") Long boardId,
            @RequestBody BoardRequest boardRequest) {

        Board updatedBoard = boardService.updateBoard(
                boardId,
                boardRequest.getTitle(),
                boardRequest.getHashtags(),
                boardRequest.getContent()
        );

        return new ResponseEntity<>(updatedBoard, HttpStatus.OK);
    }

    @DeleteMapping("/boards/{boardId}")
    public ResponseEntity<Void> deleteBoard(@PathVariable("boardId") Long boardId) {
        boardService.deleteBoard(boardId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
