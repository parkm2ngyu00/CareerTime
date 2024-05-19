package kr.ac.dankook.ace.careertime.controller;

import java.util.List;
import java.util.Map;

import kr.ac.dankook.ace.careertime.domain.Board;
import kr.ac.dankook.ace.careertime.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000") // CORS 허용(일반적으로 프론트엔드 개발 서버)에서 API 서버에 접근할 수 있도록 허용
//지정된 출처 외의 다른 출처에서는 API에 접근할 수 없도록 함
@RestController // RESTful 컨트롤러임을 선언
@RequestMapping("/api/boards") // 모든 요청 URL의 기본 경로
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @PostMapping // 게시글 생성
    public Board createBoard(@RequestBody Board board) {
        return boardService.createBoard(board);
    }

    @GetMapping // 모든 게시글 조회
    public List<Board> findAllBoards() {
        return boardService.findAllBoards();
    }

    @GetMapping("/{id}") // 특정 ID의 게시글 조회
    public ResponseEntity<Board> getBoardById(@PathVariable Long id) {
        return boardService.getBoardById(id);
    }

    @PutMapping("/{id}") // 게시글 업데이트
    public ResponseEntity<Board> updateBoard(@PathVariable Long id, @RequestBody Board boardDetails) {
        return boardService.updateBoard(id, boardDetails);
    }

    @DeleteMapping("/{id}") // 게시글 삭제
    public ResponseEntity<Map<String, Boolean>> deleteBoard(@PathVariable Long id) {
        return boardService.deleteBoard(id);
    }
}
