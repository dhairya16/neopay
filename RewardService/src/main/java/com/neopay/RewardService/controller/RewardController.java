package com.neopay.RewardService.controller;

import com.neopay.RewardService.entity.Reward;
import com.neopay.RewardService.repository.RewardRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/rewards")
public class RewardController {
    private final RewardRepository rewardRepository;

    public RewardController(RewardRepository rewardRepository) {
        this.rewardRepository = rewardRepository;
    }

    @GetMapping("/{id}")
    public List<Reward> getRewardsByUserId(@PathVariable Long id) {
        return rewardRepository.findByUserId(id);
    }

}
