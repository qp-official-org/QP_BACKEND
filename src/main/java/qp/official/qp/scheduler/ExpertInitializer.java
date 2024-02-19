package qp.official.qp.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import qp.official.qp.repository.ExpertRepository;

@Component
@RequiredArgsConstructor
public class ExpertInitializer {
    private final ExpertRepository expertRepository;

    // 새벽 3시마다 실행
    @Scheduled(cron = "0 0 3 * * *")
    public void exec() {
        expertRepository.deleteAll();
    }
}
