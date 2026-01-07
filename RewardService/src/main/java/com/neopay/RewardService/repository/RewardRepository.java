package com.neopay.RewardService.repository;

import com.neopay.RewardService.entity.Reward;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RewardRepository extends JpaRepository<Reward, Long> {
    boolean existsByTransactionId(Long transactionId);

    List<Reward> findByUserId(Long userId);
}
