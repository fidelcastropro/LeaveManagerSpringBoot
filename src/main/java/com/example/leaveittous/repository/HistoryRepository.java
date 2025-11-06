package com.example.leaveittous.repository;

import com.example.leaveittous.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<History, Long> {
}
