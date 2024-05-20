package kr.ac.dankook.ace.careertime.service;

import kr.ac.dankook.ace.careertime.config.ResourceNotFoundException;
import kr.ac.dankook.ace.careertime.domain.Board;
import kr.ac.dankook.ace.careertime.domain.Comment;
import kr.ac.dankook.ace.careertime.domain.User;
import kr.ac.dankook.ace.careertime.repository.BoardRepository;
import kr.ac.dankook.ace.careertime.repository.CommentRepository;
import kr.ac.dankook.ace.careertime.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardRepository boardRepository;

    private User testUser;
    private Board testBoard;

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

        testBoard = new Board();
        testBoard.setTitle("새 게시글");
        testBoard.setContent("새 게시글 내용 #해시태그");
        testBoard.setUser(testUser); // 테스트를 위해 임의의 User 객체 설정. 실제 코드에 맞게 수정 필요
        boardRepository.save(testBoard);
    }

    @Test
    void createComment() {
        Comment comment = new Comment();
        comment.setBoard(testBoard);
        comment.setUser(testUser);
        comment.setComment_text("새 댓글");
        comment.setComment_date(LocalDateTime.now());

        Comment createdComment = commentService.createComment(comment);

        assertNotNull(createdComment);
        assertEquals(testBoard, createdComment.getBoard());
        assertEquals(testUser, createdComment.getUser());
        assertEquals("새 댓글", createdComment.getComment_text());
    }

    @Test
    void findCommentsByBoardId() {
        Comment comment = new Comment();
        comment.setBoard(testBoard);
        comment.setUser(testUser);
        comment.setComment_text("새 댓글");
        comment.setComment_date(LocalDateTime.now());
        commentService.createComment(comment);

        List<Comment> comments = commentService.findCommentsByBoardId(testBoard.getPost_id());

        assertFalse(comments.isEmpty());
        assertEquals(1, comments.size());
        assertEquals("새 댓글", comments.get(0).getComment_text());
    }

    @Test
    void updateComment() {
        Comment comment = new Comment();
        comment.setBoard(testBoard);
        comment.setUser(testUser);
        comment.setComment_text("새 댓글 1");
        comment.setComment_date(LocalDateTime.now());
        comment = commentService.createComment(comment);

        Comment updatedDetails = new Comment();
        updatedDetails.setComment_text("새 댓글 2");
        updatedDetails.setComment_date(LocalDateTime.now());

        Comment updatedComment = commentService.updateComment(comment.getComment_id(), updatedDetails);

        assertNotNull(updatedComment);
        assertEquals("새 댓글 2", updatedComment.getComment_text());
    }

    @Test
    void deleteComment() {
        Comment comment = new Comment();
        comment.setBoard(testBoard);
        comment.setUser(testUser);
        comment.setComment_text("새 댓글");
        comment.setComment_date(LocalDateTime.now());
        comment = commentService.createComment(comment);

        Long commentId = comment.getComment_id();
        commentService.deleteComment(commentId);

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            commentService.deleteComment(commentId);
        });

        String expectedMessage = "Comment not found with id: " + commentId;
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}