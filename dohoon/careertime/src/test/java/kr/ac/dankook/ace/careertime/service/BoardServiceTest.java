package kr.ac.dankook.ace.careertime.service;

import kr.ac.dankook.ace.careertime.domain.Board;
import kr.ac.dankook.ace.careertime.domain.User;
import kr.ac.dankook.ace.careertime.repository.BoardRepository;
import kr.ac.dankook.ace.careertime.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardRepository boardRepository;

    private User testUser;

    @BeforeEach
    void setup() {
        // 사용자 데이터 생성
        testUser = new User();
        testUser.setUsername("testUser");
        testUser.setPassword("testPass");
        testUser.setName("Test Name");
        testUser.setEmail("test@example.com");
        testUser.setUser_type("NORMAL");
        userRepository.save(testUser);
    }

    @Test
    void createBoard() {
        Board newBoard = new Board();
        newBoard.setTitle("새 게시글");
        newBoard.setContent("새 게시글 내용 #해시태그");
        newBoard.setUser(testUser); // 테스트를 위해 임의의 User 객체 설정. 실제 코드에 맞게 수정 필요

        Board createdBoard = boardService.createBoard(newBoard);
        assertThat(createdBoard).isNotNull();
        assertThat(createdBoard.getTitle()).isEqualTo("새 게시글");
    }

    @Test
    void findBoardsByTitleAndHashtag() {
//        String title = "게시글";
//        String hashtag = "#해시태그";
//
//        List<Board> boards = boardService.findBoardsByTitleAndHashtag(title, hashtag);
//        assertThat(boards).isNotEmpty();
    }

    @Test
    void findAllBoards() {
        Board newBoard1 = new Board();
        newBoard1.setTitle("첫 번째 게시글");
        newBoard1.setContent("첫 번째 게시글 내용 #해시태그1");
        newBoard1.setUser(testUser);
        boardService.createBoard(newBoard1);

        Board newBoard2 = new Board();
        newBoard2.setTitle("두 번째 게시글");
        newBoard2.setContent("두 번째 게시글 내용 #해시태그2");
        newBoard2.setUser(testUser);
        boardService.createBoard(newBoard2);

        List<Board> boards = boardService.findAllBoards();
        assertThat(boards).isNotEmpty();
        assertThat(boards.size()).isEqualTo(2);
    }

    @Test
    void getBoardById() {
        Board newBoard = new Board();
        newBoard.setTitle("게시글 제목");
        newBoard.setContent("게시글 내용 #해시태그");
        newBoard.setUser(testUser);
        Board createdBoard = boardService.createBoard(newBoard);

        ResponseEntity<Board> foundBoardResponse = boardService.getBoardById(createdBoard.getPost_id());
        Board foundBoard = foundBoardResponse.getBody();
        assertThat(foundBoard).isNotNull();
        assertThat(foundBoard.getTitle()).isEqualTo(createdBoard.getTitle());
        assertThat(foundBoard.getContent()).isEqualTo(createdBoard.getContent());
    }

    @Test
    void updateBoard() {
        Board newBoard = new Board();
        newBoard.setTitle("게시글 제목");
        newBoard.setContent("게시글 내용 #해시태그");
        newBoard.setUser(testUser);
        Board createdBoard = boardService.createBoard(newBoard);

        Board updatedDetails = new Board();
        updatedDetails.setTitle("수정된 제목");
        updatedDetails.setContent("수정된 내용 #수정된해시태그");

        ResponseEntity<Board> updatedBoardResponse = boardService.updateBoard(createdBoard.getPost_id(), updatedDetails);
        Board updatedBoard = updatedBoardResponse.getBody();

        assertThat(updatedBoard).isNotNull();
        assertThat(updatedBoard.getTitle()).isEqualTo("수정된 제목");
        assertThat(updatedBoard.getContent()).isEqualTo("수정된 내용 #수정된해시태그");
    }

    @Test
    void deleteBoard() {
        Board newBoard = new Board();
        newBoard.setTitle("게시글 제목");
        newBoard.setContent("게시글 내용 #해시태그");
        newBoard.setUser(testUser);
        Board createdBoard = boardService.createBoard(newBoard);

        ResponseEntity<Map<String, Boolean>> response = boardService.deleteBoard(createdBoard.getPost_id());
        assertThat(response.getBody()).containsEntry("deleted", Boolean.TRUE);

        Optional<Board> deletedBoard = boardRepository.findById(createdBoard.getPost_id());
        assertThat(deletedBoard).isEmpty();
    }
}