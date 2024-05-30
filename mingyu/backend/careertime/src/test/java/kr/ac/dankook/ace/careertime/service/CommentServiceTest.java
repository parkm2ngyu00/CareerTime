package kr.ac.dankook.ace.careertime.service;

import kr.ac.dankook.ace.careertime.domain.Board;
import kr.ac.dankook.ace.careertime.domain.Comment;
import kr.ac.dankook.ace.careertime.domain.User;
import kr.ac.dankook.ace.careertime.dto.CommentSummaryResponse;
import kr.ac.dankook.ace.careertime.repository.BoardRepository;
import kr.ac.dankook.ace.careertime.repository.CommentRepository;
import kr.ac.dankook.ace.careertime.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    void setUp() {
        testUser = new User();
        testUser.setUsername("testuser");
        testUser.setPassword("password");
        testUser.setName("Test User");
        testUser.setEmail("testuser@example.com");
        testUser.setUser_type("USER");
        userRepository.save(testUser);

        testBoard = new Board();
        testBoard.setUser(testUser);
        testBoard.setTitle("Test Board");
        testBoard.setContent("Test Content");
        testBoard.setHashtags("#test");
        testBoard.setPost_date(LocalDateTime.now());
        boardRepository.save(testBoard);
    }

    @Test
    void createComment() {
        // Given
        Comment comment = new Comment();
        comment.setBoard(testBoard);
        comment.setUser(testUser);
        comment.setComment_rate(5L);
        comment.setComment_text("Great post!");
        comment.setComment_date(LocalDateTime.now());

        // When
        Comment createdComment = commentService.createComment(comment);

        // Then
        assertNotNull(createdComment);
        assertEquals(testBoard.getPost_id(), createdComment.getBoard().getPost_id());
        assertEquals(testUser.getUser_id(), createdComment.getUser().getUser_id());
        assertEquals(5L, createdComment.getComment_rate());
        assertEquals("Great post!", createdComment.getComment_text());

        // Verify the comment is saved in the database
        Optional<Comment> foundComment = commentRepository.findById(createdComment.getComment_id());
        assertTrue(foundComment.isPresent());
        assertEquals("Great post!", foundComment.get().getComment_text());
    }

    @Test
    void findCommentsByBoardId() {
        // Given
        Comment comment1 = new Comment();
        comment1.setBoard(testBoard);
        comment1.setUser(testUser);
        comment1.setComment_rate(5L);
        comment1.setComment_text("Great post!");
        comment1.setComment_date(LocalDateTime.now());
        commentRepository.save(comment1);

        Comment comment2 = new Comment();
        comment2.setBoard(testBoard);
        comment2.setUser(testUser);
        comment2.setComment_rate(4L);
        comment2.setComment_text("Nice post!");
        comment2.setComment_date(LocalDateTime.now());
        commentRepository.save(comment2);

        // When
        List<Comment> comments = commentService.findCommentsByBoardId(testBoard.getPost_id());

        // Then
        assertNotNull(comments);
        assertEquals(2, comments.size());
    }

    @Test
    void updateComment() {
        // Given
        Comment comment = new Comment();
        comment.setBoard(testBoard);
        comment.setUser(testUser);
        comment.setComment_rate(5L);
        comment.setComment_text("Great post!");
        comment.setComment_date(LocalDateTime.now());
        commentRepository.save(comment);

        Long commentId = comment.getComment_id();
        Comment updatedCommentDetails = new Comment();
        updatedCommentDetails.setComment_text("Updated comment");
        updatedCommentDetails.setComment_date(LocalDateTime.now());

        // When
        Comment updatedComment = commentService.updateComment(commentId, updatedCommentDetails);

        // Then
        assertNotNull(updatedComment);
        assertEquals("Updated comment", updatedComment.getComment_text());
    }

    @Test
    void deleteComment() {
        // Given
        Comment comment = new Comment();
        comment.setBoard(testBoard);
        comment.setUser(testUser);
        comment.setComment_rate(5L);
        comment.setComment_text("Great post!");
        comment.setComment_date(LocalDateTime.now());
        commentRepository.save(comment);

        Long commentId = comment.getComment_id();

        // When
        ResponseEntity<Void> response = commentService.deleteComment(commentId);

        // Then
        assertEquals(200, response.getStatusCodeValue());
        Optional<Comment> foundComment = commentRepository.findById(commentId);
        assertFalse(foundComment.isPresent());
    }

    @Test
    void getCommentSummaryByBoardId() {
        // Given
        Comment comment1 = new Comment();
        comment1.setBoard(testBoard);
        comment1.setUser(testUser);
        comment1.setComment_rate(5L);
        comment1.setComment_text("Great post!");
        comment1.setComment_date(LocalDateTime.now());
        commentRepository.save(comment1);

        Comment comment2 = new Comment();
        comment2.setBoard(testBoard);
        comment2.setUser(testUser);
        comment2.setComment_rate(4L);
        comment2.setComment_text("Nice post!");
        comment2.setComment_date(LocalDateTime.now());
        commentRepository.save(comment2);

        // When
        CommentSummaryResponse summaryResponse = commentService.getCommentSummaryByBoardId(testBoard.getPost_id());

        // Then
        assertNotNull(summaryResponse);
        assertEquals(2, summaryResponse.getReviewCount());
        assertEquals(4.5, summaryResponse.getAverageRating());
        assertEquals(2, summaryResponse.getReviewList().size());
        assertEquals("Great post!", summaryResponse.getReviewList().get(0).getComment());
        assertEquals("Nice post!", summaryResponse.getReviewList().get(1).getComment());
    }
}