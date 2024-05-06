package kr.ac.dankook.ace.board;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000") // CORS 허용(일반적으로 프론트엔드 개발 서버)에서 API 서버에 접근할 수 있도록 허용
//지정된 출처 외의 다른 출처에서는 API에 접근할 수 없도록 함
@RestController // RESTful 컨트롤러임을 선언
@RequestMapping("/api") // 모든 요청 URL의 기본 경로
public class BoardController {
    @Autowired
    private BoardService boardService;

    @PostMapping("/boards") // 게시글 생성
    public Board createBoard(@RequestBody Board board) {
        return boardService.createBoard(board);
    }

    @GetMapping("/boards") // 모든 게시글 조회
    public List<Board> findAllBoards() {
        return boardService.findAllBoards();
    }

    @GetMapping("/boards/{id}") // 특정 ID의 게시글 조회
    public ResponseEntity<Board> getBoardById(@PathVariable Integer id) {
        return boardService.getBoardById(id);
    }

    @PutMapping("/boards/{id}") // 게시글 업데이트
    public ResponseEntity<Board> updateBoard(@PathVariable Integer id, @RequestBody Board boardDetails) {
        return boardService.updateBoard(id, boardDetails);
    }

    @DeleteMapping("/boards/{id}") // 게시글 삭제
    public ResponseEntity<Map<String, Boolean>> deleteBoard(@PathVariable Integer id) {
        return boardService.deleteBoard(id);
    }
}
