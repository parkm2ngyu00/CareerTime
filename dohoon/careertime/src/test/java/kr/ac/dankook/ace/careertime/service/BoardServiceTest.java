package kr.ac.dankook.ace.careertime.service;

import kr.ac.dankook.ace.careertime.domain.Board;
import kr.ac.dankook.ace.careertime.domain.Profile;
import kr.ac.dankook.ace.careertime.domain.User;
import kr.ac.dankook.ace.careertime.dto.BoardResponse;
import kr.ac.dankook.ace.careertime.dto.UserInfo;
import kr.ac.dankook.ace.careertime.repository.BoardRepository;
import kr.ac.dankook.ace.careertime.repository.ProfileRepository;
import kr.ac.dankook.ace.careertime.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ProfileRepository profileRepository;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setUsername("testuser");
        testUser.setPassword("password");
        testUser.setName("Test User");
        testUser.setEmail("testuser@example.com");
        testUser.setUser_type("USER");
        userRepository.save(testUser);

        Profile profile = new Profile();
        profile.setUser(testUser);
        profile.setCompany_name("Test Company");
        profile.setPosition("Developer");
        profile.setHashtags("#java, #spring");
        profile.setIntroduction("Hello, I'm a developer.");
        profileRepository.save(profile);
    }

    @Test
    void createBoard() {
        // Given
        String title = "Test Title";
        List<String> hashtags = Arrays.asList("#test", "#board");
        String content = "Test content for the board.";

        // When
        Board createdBoard = boardService.createBoard(testUser.getUser_id(), title, hashtags, content);

        // Then
        assertNotNull(createdBoard);
        assertEquals(title, createdBoard.getTitle());
        assertEquals("Test content for the board.", createdBoard.getContent());
        assertEquals("#test, #board", createdBoard.getHashtags());
        assertEquals(testUser.getUser_id(), createdBoard.getUser().getUser_id());

        // Verify the board is saved in the database
        Optional<Board> foundBoard = boardRepository.findById(createdBoard.getPost_id());
        assertTrue(foundBoard.isPresent());
        assertEquals(title, foundBoard.get().getTitle());
    }

    @Test
    void searchBoards() {
        // Given
        String title = "Search Title";
        List<String> hashtags = Arrays.asList("#search", "#test");
        String content = "Content for search test.";
        boardService.createBoard(testUser.getUser_id(), title, hashtags, content);

        // When
        List<Board> boards = boardService.searchBoards("Search");

        // Then
        assertFalse(boards.isEmpty());
        assertTrue(boards.stream().anyMatch(board -> board.getTitle().contains("Search")));
    }

    @Test
    void getAllBoards() {
        // Given
        String title1 = "First Board";
        List<String> hashtags1 = Arrays.asList("#first", "#board");
        String content1 = "First board content.";
        boardService.createBoard(testUser.getUser_id(), title1, hashtags1, content1);

        String title2 = "Second Board";
        List<String> hashtags2 = Arrays.asList("#second", "#board");
        String content2 = "Second board content.";
        boardService.createBoard(testUser.getUser_id(), title2, hashtags2, content2);

        // When
        List<Board> boards = boardService.getAllBoards();

        // Then
        assertFalse(boards.isEmpty());
        assertTrue(boards.size() >= 2);
    }

    @Test
    void getBoardById() {
        // Given
        String title = "Get Board";
        List<String> hashtags = Arrays.asList("#get", "#board");
        String content = "Get board content.";
        Board createdBoard = boardService.createBoard(testUser.getUser_id(), title, hashtags, content);

        // When
        BoardResponse foundBoard = boardService.getBoardById(createdBoard.getPost_id());

        // Then
        assertNotNull(foundBoard);
        assertEquals(title, foundBoard.getTitle());
        assertEquals(content, foundBoard.getContent());
        assertEquals(hashtags, foundBoard.getHashtags());

        // Check user information
        UserInfo userInfo = foundBoard.getUserinfo();
        assertNotNull(userInfo);
        assertEquals(testUser.getUsername(), userInfo.getUsername());
        assertEquals(testUser.getEmail(), userInfo.getUseremail());

        // Check profile information
        Optional<Profile> profileOpt = profileRepository.findByUser(testUser);
        profileOpt.ifPresent(profile -> {
            assertEquals(profile.getCompany_name(), userInfo.getUsercompany());
            assertEquals(Arrays.asList(profile.getHashtags().split(", ")), userInfo.getUserinterest());
        });
    }

    @Test
    void updateBoard() {
        // Given
        String title = "Update Board";
        List<String> hashtags = Arrays.asList("#update", "#board");
        String content = "Update board content.";
        Board createdBoard = boardService.createBoard(testUser.getUser_id(), title, hashtags, content);

        // When
        String updatedTitle = "Updated Board Title";
        List<String> updatedHashtags = Arrays.asList("#updated", "#board");
        String updatedContent = "Updated board content.";
        Board updatedBoard = boardService.updateBoard(createdBoard.getPost_id(), updatedTitle, updatedHashtags, updatedContent);

        // Then
        assertNotNull(updatedBoard);
        assertEquals(updatedTitle, updatedBoard.getTitle());
        assertEquals(updatedContent, updatedBoard.getContent());
        assertEquals("#updated, #board", updatedBoard.getHashtags());
    }

    @Test
    void deleteBoard() {
        // Given
        String title = "Delete Board";
        List<String> hashtags = Arrays.asList("#delete", "#board");
        String content = "Delete board content.";
        Board createdBoard = boardService.createBoard(testUser.getUser_id(), title, hashtags, content);

        // When
        boardService.deleteBoard(createdBoard.getPost_id());

        // Then
        Optional<Board> foundBoard = boardRepository.findById(createdBoard.getPost_id());
        assertFalse(foundBoard.isPresent());
    }

    @Test
    void getProfileByUser() {
        // Given
        Profile profile = new Profile();
        profile.setUser(testUser);
        profile.setCompany_name("Test Company");
        profile.setPosition("Developer");
        profile.setHashtags("#java, #spring");
        profile.setIntroduction("Hello, I'm a developer.");
        profileRepository.save(profile);

        // When
        Profile foundProfile = boardService.getProfileByUser(testUser);

        // Then
        assertNotNull(foundProfile);
        assertEquals(testUser.getUser_id(), foundProfile.getUser().getUser_id());
        assertEquals("Test Company", foundProfile.getCompany_name());
        assertEquals("Developer", foundProfile.getPosition());
    }
}