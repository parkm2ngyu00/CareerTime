//package kr.ac.dankook.ace.careertime.service;
//
//import kr.ac.dankook.ace.careertime.domain.Board;
//import kr.ac.dankook.ace.careertime.domain.Comment;
//import kr.ac.dankook.ace.careertime.domain.Profile;
//import kr.ac.dankook.ace.careertime.domain.User;
//import kr.ac.dankook.ace.careertime.dto.BoardResponse;
//import kr.ac.dankook.ace.careertime.dto.UserInfo;
//import kr.ac.dankook.ace.careertime.repository.BoardRepository;
//import kr.ac.dankook.ace.careertime.repository.CommentRepository;
//import kr.ac.dankook.ace.careertime.repository.ProfileRepository;
//import kr.ac.dankook.ace.careertime.repository.UserRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@Transactional
//class BoardServiceTest {
//
//    @Autowired
//    private BoardService boardService;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private BoardRepository boardRepository;
//
//    @Autowired
//    private ProfileRepository profileRepository;
//
//    @Autowired
//    private CommentRepository commentRepository;
//
//    private User testUser;
//
//    @BeforeEach
//    void setUp() {
//        testUser = new User();
//        testUser.setUsername("testuser");
//        testUser.setPassword("password");
//        testUser.setName("Test User");
//        testUser.setEmail("testuser@example.com");
//        testUser.setUser_type("USER");
//        userRepository.save(testUser);
//
//        Profile profile = new Profile();
//        profile.setUser(testUser);
//        profile.setCompany_name("Test Company");
//        profile.setPosition("Developer");
//        profile.setHashtags("#java, #spring");
//        profile.setIntroduction("Hello, I'm a developer.");
//        profileRepository.save(profile);
//    }
//
//    @Test
//    void createBoard() {
//        // Given
//        String title = "Test Title";
//        List<String> hashtags = Arrays.asList("#test", "#board");
//        String content = "Test content for the board.";
//
//        // When
//        Board createdBoard = boardService.createBoard(testUser.getUser_id(), title, hashtags, content);
//
//        // Then
//        assertNotNull(createdBoard);
//        assertEquals(title, createdBoard.getTitle());
//        assertEquals("Test content for the board.", createdBoard.getContent());
//        assertEquals("#test, #board", createdBoard.getHashtags());
//        assertEquals(testUser.getUser_id(), createdBoard.getUser().getUser_id());
//
//        // Verify the board is saved in the database
//        Optional<Board> foundBoard = boardRepository.findById(createdBoard.getPost_id());
//        assertTrue(foundBoard.isPresent());
//        assertEquals(title, foundBoard.get().getTitle());
//    }
//
//    @Test
//    void searchBoards() {
//        // Given
//        String title1 = "Search Title One";
//        List<String> hashtags1 = Arrays.asList("#search", "#test1");
//        String content1 = "Content for search test one.";
//        boardService.createBoard(testUser.getUser_id(), title1, hashtags1, content1);
//
//        String title2 = "Another Title";
//        List<String> hashtags2 = Arrays.asList("#search", "#test2");
//        String content2 = "Content for search test two.";
//        boardService.createBoard(testUser.getUser_id(), title2, hashtags2, content2);
//
//        String title3 = "Different Title";
//        List<String> hashtags3 = Arrays.asList("#different", "#test3");
//        String content3 = "Different content.";
//        boardService.createBoard(testUser.getUser_id(), title3, hashtags3, content3);
//
//        // When
//        List<BoardResponse> searchResults = boardService.searchBoards("search");
//
//        // Then
//        assertFalse(searchResults.isEmpty());
//        assertTrue(searchResults.stream().anyMatch(board -> board.getTitle().contains("Search Title One")));
//        assertTrue(searchResults.stream().anyMatch(board -> board.getTitle().contains("Another Title")));
//        assertFalse(searchResults.stream().anyMatch(board -> board.getTitle().contains("Different Title")));
//
//        // Check that the correct boards were found
//        searchResults.forEach(board -> {
//            assertTrue(board.getTitle().contains("search") || board.getContent().contains("search") || board.getHashtags().contains("#search"));
//        });
//    }
//
//    @Test
//    void getAllBoards() {
//        // Given
//        String title1 = "First Board";
//        List<String> hashtags1 = Arrays.asList("#first", "#board");
//        String content1 = "First board content.";
//        boardService.createBoard(testUser.getUser_id(), title1, hashtags1, content1);
//
//        String title2 = "Second Board";
//        List<String> hashtags2 = Arrays.asList("#second", "#board");
//        String content2 = "Second board content.";
//        boardService.createBoard(testUser.getUser_id(), title2, hashtags2, content2);
//
////        // When
////        List<Board> boards = boardService.getAllBoards();
////
////        // Then
////        assertFalse(boards.isEmpty());
////        assertTrue(boards.size() >= 2);
//    }
//
//    @Test
//    void getBoardById() {
//        // Given
//        String title = "Get Board";
//        List<String> hashtags = Arrays.asList("#get", "#board");
//        String content = "Get board content.";
//        Board createdBoard = boardService.createBoard(testUser.getUser_id(), title, hashtags, content);
//
//        // When
//        BoardResponse foundBoard = boardService.getBoardById(createdBoard.getPost_id());
//
//        // Then
//        assertNotNull(foundBoard);
//        assertEquals(title, foundBoard.getTitle());
//        assertEquals(content, foundBoard.getContent());
//        assertEquals(hashtags, foundBoard.getHashtags());
//
//        // Check user information
//        UserInfo userInfo = foundBoard.getUserinfo();
//        assertNotNull(userInfo);
//        assertEquals(testUser.getUsername(), userInfo.getUsername());
//        assertEquals(testUser.getEmail(), userInfo.getUseremail());
//
//        // Check profile information
//        Optional<Profile> profileOpt = profileRepository.findByUser(testUser);
//        profileOpt.ifPresent(profile -> {
//            assertEquals(profile.getCompany_name(), userInfo.getUsercompany());
//            assertEquals(Arrays.asList(profile.getHashtags().split(", ")), userInfo.getUserinterest());
//        });
//    }
//
//    @Test
//    void updateBoard() {
//        // Given
//        String title = "Update Board";
//        List<String> hashtags = Arrays.asList("#update", "#board");
//        String content = "Update board content.";
//        Board createdBoard = boardService.createBoard(testUser.getUser_id(), title, hashtags, content);
//
//        // When
//        String updatedTitle = "Updated Board Title";
//        List<String> updatedHashtags = Arrays.asList("#updated", "#board");
//        String updatedContent = "Updated board content.";
//        Board updatedBoard = boardService.updateBoard(createdBoard.getPost_id(), updatedTitle, updatedHashtags, updatedContent);
//
//        // Then
//        assertNotNull(updatedBoard);
//        assertEquals(updatedTitle, updatedBoard.getTitle());
//        assertEquals(updatedContent, updatedBoard.getContent());
//        assertEquals("#updated, #board", updatedBoard.getHashtags());
//    }
//
//    @Test
//    void deleteBoard() {
//        // Given
//        Board board = new Board();
//        board.setUser(testUser);
//        board.setTitle("Test Board");
//        board.setContent("Test Content");
//        board.setHashtags("#test");
//        boardRepository.save(board);
//
//        Comment comment1 = new Comment();
//        comment1.setBoard(board);
//        comment1.setUser(testUser);
//        comment1.setComment_rate(5L);
//        comment1.setComment_text("Great post!");
//        comment1.setComment_date(LocalDateTime.now());
//        commentRepository.save(comment1);
//
//        Comment comment2 = new Comment();
//        comment2.setBoard(board);
//        comment2.setUser(testUser);
//        comment2.setComment_rate(4L);
//        comment2.setComment_text("Nice post!");
//        comment2.setComment_date(LocalDateTime.now());
//        commentRepository.save(comment2);
//
//        Long boardId = board.getPost_id();
//
//        // When
//        boardService.deleteBoard(boardId);
//
//        // Then
//        Optional<Board> foundBoard = boardRepository.findById(boardId);
//        assertFalse(foundBoard.isPresent());
//
//        List<Comment> comments = commentRepository.findAll().stream()
//                .filter(c -> c.getBoard().getPost_id().equals(boardId))
//                .collect(Collectors.toList());
//        assertFalse(foundBoard.isPresent());
//    }
//}