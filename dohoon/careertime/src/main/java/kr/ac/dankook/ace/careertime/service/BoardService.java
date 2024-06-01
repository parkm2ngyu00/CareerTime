package kr.ac.dankook.ace.careertime.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import kr.ac.dankook.ace.careertime.domain.Board;
import kr.ac.dankook.ace.careertime.domain.Profile;
import kr.ac.dankook.ace.careertime.domain.User;
import kr.ac.dankook.ace.careertime.dto.BoardResponse;
import kr.ac.dankook.ace.careertime.dto.UserInfo;
import kr.ac.dankook.ace.careertime.repository.BoardRepository;
import kr.ac.dankook.ace.careertime.repository.ProfileRepository;
import kr.ac.dankook.ace.careertime.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service // 서비스 계층임을 선언
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    public Board createBoard(Long userId, String title, List<String> hashtags, String content) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + userId));

        String hashtagString = String.join(", ", hashtags);

        Board board = new Board();
        board.setUser(user);
        board.setTitle(title);
        board.setContent(content);
        board.setHashtags(hashtagString);
        board.setPost_date(LocalDateTime.now());

        return boardRepository.save(board);
    }

    public List<BoardResponse> searchBoards(String query) {
        List<Board> boards = boardRepository.findByTitleContainingOrContentContainingOrHashtagsContaining(query, query, query);
        return boards.stream().map(this::mapToBoardResponse).collect(Collectors.toList());
    }

    public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }

    public BoardResponse getBoardById(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid board ID: " + boardId));

        return mapToBoardResponse(board);
    }

    public Board updateBoard(Long boardId, String title, List<String> hashtags, String content) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid board ID: " + boardId));

        String hashtagString = String.join(", ", hashtags);

        board.setTitle(title);
        board.setContent(content);
        board.setHashtags(hashtagString);

        return boardRepository.save(board);
    }

    public void deleteBoard(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid board ID: " + boardId));
        boardRepository.delete(board);
    }

    public Profile getProfileByUser(User user) {
        return profileRepository.findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("Profile not found for user: " + user.getUsername()));
    }

    private BoardResponse mapToBoardResponse(Board board) {
        User user = board.getUser();
        Profile profile = getProfileByUser(user);

        UserInfo userInfo = new UserInfo();
        userInfo.setUser_id(user.getUser_id());
        userInfo.setUsername(user.getUsername());
        userInfo.setUsercompany(profile.getCompany_name());
        userInfo.setUseremail(user.getEmail());
        userInfo.setUserinterest(Arrays.asList(profile.getHashtags().split(", ")));

        BoardResponse boardResponse = new BoardResponse();
        boardResponse.setPost_id(board.getPost_id());
        boardResponse.setTitle(board.getTitle());
        boardResponse.setHashtags(Arrays.asList(board.getHashtags().split(", ")));
        boardResponse.setContent(board.getContent());
        boardResponse.setPostdate(board.getPost_date().format(DateTimeFormatter.ISO_LOCAL_DATE));
        boardResponse.setUserinfo(userInfo);

        return boardResponse;
    }
}