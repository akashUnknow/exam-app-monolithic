package com.examapp.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examapp.model.UserProfile;
import com.examapp.repository.UserProfileRepository;

@Service
public class UserService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    /**
     * ✅ Get existing user profile — throw error if not found
     * Profiles are now created in AuthService during registration.
     */
    public UserProfile getUserProfile(Long userId) {
        return userProfileRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User profile not found for userId: " + userId));
    }

    /**
     * ✅ Update user profile fields (name, phone, image)
     * Email stays same as in main User table — should not be changed here.
     */
    public UserProfile updateUserProfile(Long userId, UserProfile updatedProfile) {
        UserProfile profile = getUserProfile(userId);

        if (updatedProfile.getName() != null) {
            profile.setName(updatedProfile.getName());
        }
        if (updatedProfile.getPhoneNumber() != null) {
            profile.setPhoneNumber(updatedProfile.getPhoneNumber());
        }
        if (updatedProfile.getProfileImageUrl() != null) {
            profile.setProfileImageUrl(updatedProfile.getProfileImageUrl());
        }

        return userProfileRepository.save(profile);
    }

    /**
     * ✅ Get user’s friends
     */
    public List<UserProfile> getUserFriends(Long userId) {
        UserProfile profile = getUserProfile(userId);
        return profile.getFriends();
    }

    /**
     * ✅ Add a friend (mutual)
     */
    public void addFriend(Long userId, Long friendId) {
        UserProfile user = getUserProfile(userId);
        UserProfile friend = getUserProfile(friendId);

        if (!user.getFriends().contains(friend)) {
            user.getFriends().add(friend);
            userProfileRepository.save(user);
        }

        if (!friend.getFriends().contains(user)) {
            friend.getFriends().add(user);
            userProfileRepository.save(friend);
        }
    }

    /**
     * ✅ Remove a friend (mutual)
     */
    public void removeFriend(Long userId, Long friendId) {
        UserProfile user = getUserProfile(userId);
        UserProfile friend = getUserProfile(friendId);

        user.getFriends().remove(friend);
        friend.getFriends().remove(user);

        userProfileRepository.save(user);
        userProfileRepository.save(friend);
    }

    /**
     * ✅ Update user statistics (score, tests, rank)
     */
    public void updateUserStats(Long userId, Integer score) {
        UserProfile profile = getUserProfile(userId);
        profile.setTotalScore(profile.getTotalScore() + score);
        profile.setTestsCompleted(profile.getTestsCompleted() + 1);
        userProfileRepository.save(profile);

        updateRankings();
    }

    /**
     * ✅ Recalculate all ranks based on total score
     */
    private void updateRankings() {
        List<UserProfile> allUsers = userProfileRepository.findAllByOrderByTotalScoreDesc();
        int rank = 1;
        for (UserProfile user : allUsers) {
            user.setRank(rank++);
            userProfileRepository.save(user);
        }
    }

    /**
     * ✅ Get global leaderboard
     */
    public List<UserProfile> getLeaderboard() {
        return userProfileRepository.findAllByOrderByTotalScoreDesc();
    }

    /**
     * ✅ Search users by a list of IDs
     */
    public List<UserProfile> searchUsers(List<Long> userIds) {
        return userProfileRepository.findByUserIdIn(userIds);
    }
}
