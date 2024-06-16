package kr.ac.dankook.ace.careertime.service;

import kr.ac.dankook.ace.careertime.dto.BoardResponse;
import kr.ac.dankook.ace.careertime.dto.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.ac.dankook.ace.careertime.repository.ProfileRepository;
import kr.ac.dankook.ace.careertime.repository.BoardRepository;
import kr.ac.dankook.ace.careertime.repository.UserRepository;
import kr.ac.dankook.ace.careertime.domain.Board;
import kr.ac.dankook.ace.careertime.domain.Profile;
import kr.ac.dankook.ace.careertime.domain.User;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final ProfileRepository profileRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public List<BoardResponse> recommendBoardsForUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            return new ArrayList<>();
        }

        Optional<Profile> profile = profileRepository.findByUser(user.get());
        if (!profile.isPresent()) {
            return new ArrayList<>();
        }

        String[] userHashtags = profile.get().getHashtags().split(",");

        Map<Board, Long> boardMatchCount = new HashMap<>();
        for (String hashtag : userHashtags) {
            List<Board> boards = boardRepository.findByHashtagsContaining(hashtag.trim());
            for (Board board : boards) {
                boardMatchCount.put(board, boardMatchCount.getOrDefault(board, 0L) + 1);
            }
        }

        return boardMatchCount.entrySet().stream()
                .sorted(Map.Entry.<Board, Long>comparingByValue().reversed())
                .limit(10)
                .map(Map.Entry::getKey)
                .map(this::mapToBoardResponse)
                .collect(Collectors.toList());
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
        userInfo.setUserimage(profile.getProfilePicture());
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