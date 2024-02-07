package com.example.ReviewZIP.domain.searchHistory;

import com.example.ReviewZIP.domain.user.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SearchHistoriesRepository extends JpaRepository<SearchHistories, Long> {
}
