package kr.ac.dankook.ace.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service // 서비스 계층임을 선언
public class BoardService {
    @Autowired
    private BoardRepository boardRepository; // 리포지토리 의존성 주입

    // 게시글 생성
    public Board createBoard(Board board) {
        return boardRepository.save(board); // 게시글 저장
    }

    // 모든 게시글 조회
    public List<Board> findAllBoards() {
        return boardRepository.findAll(); // 모든 게시글 조회
    }

    // ID로 게시글 조회
    public ResponseEntity<Board> getBoardById(Integer id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Board not exist with id :" + id));
        return ResponseEntity.ok(board);
    }

    // 게시글 업데이트
    public ResponseEntity<Board> updateBoard(Integer id, Board boardDetails) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Board not exist with id :" + id));
        board.setTitle(boardDetails.getTitle());
        board.setContent(boardDetails.getContent());
        Board updatedBoard = boardRepository.save(board);
        return ResponseEntity.ok(updatedBoard);
    }

    // 게시글 삭제
    public ResponseEntity<Map<String, Boolean>> deleteBoard(Integer id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Board not exist with id :" + id));
        boardRepository.delete(board);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}