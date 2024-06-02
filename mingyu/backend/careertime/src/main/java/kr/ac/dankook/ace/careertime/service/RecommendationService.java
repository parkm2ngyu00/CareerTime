package kr.ac.dankook.ace.careertime.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.ac.dankook.ace.careertime.repository.ProfileRepository;
import kr.ac.dankook.ace.careertime.repository.BoardRepository;
import kr.ac.dankook.ace.careertime.repository.UserRepository;
import kr.ac.dankook.ace.careertime.domain.Board;
import kr.ac.dankook.ace.careertime.domain.Profile;
import kr.ac.dankook.ace.careertime.domain.User;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    private final ProfileRepository profileRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Autowired
    public RecommendationService(ProfileRepository profileRepository, BoardRepository boardRepository, UserRepository userRepository) {
        this.profileRepository = profileRepository;
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
    }

    public List<Board> recommendBoardsForUser(Long userId) {
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
                .collect(Collectors.toList());
    }
}