package com.innosync.hook.service;

import com.innosync.hook.dto.ContestDto;
import com.innosync.hook.entity.ContestEntity;
import com.innosync.hook.repository.ContestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ContestService {
    private final ContestRepository contestRepository;

    private static String CONTEST_URL = "https://www.onoffmix.com/event/main?s=%ED%95%B4%EC%BB%A4%ED%86%A4";


    @PostConstruct
    @Scheduled(cron = "0 0 6 * * *") // 초 분 시 날 달 년 (매일 오전 6시에 시작)
    public void getContestInfo() throws IOException {

        Document doc = Jsoup.connect(CONTEST_URL).get();
        for (int i=1; i<=20; i++) {
            String selector1 = String.format("#content > div > section.event_main_area > ul > li:nth-child(%d) > article.event_area.event_main > a > div.event_thumbnail > img", i);
            Elements contents = doc.select(selector1);
            // li:nth-child(1) () 안에 값 바꿔서 게시물 순서 조정가능
            String selector2 = String.format("#content > div > section.event_main_area > ul > li:nth-child(%d) > article.event_area.event_main > a > div.event_info_area > div.event_info > div.date", i);
            Elements dateContents = doc.select(selector2);
            if (!contents.isEmpty() && !dateContents.isEmpty()) {
                Element imgElement = contents.get(0);
                String altText = imgElement.attr("alt");
                String srcUrl = imgElement.attr("src");

                Element dateElement = dateContents.get(0);
                String dateInfo = dateElement.text();

                System.out.println("Alt Text: " + altText);
                System.out.println("Image URL: " + srcUrl);
                System.out.println("Date Info: " + dateInfo);

                ContestDto contestDto = ContestDto.builder()
                        .title(altText)
                        .imgUrl(srcUrl)
                        .dateTime(dateInfo)
                        .build();

                ContestEntity contestEntity = ContestEntity.builder()
                        .title(contestDto.getTitle())
                        .imgUrl(contestDto.getImgUrl())
                        .dateTime(contestDto.getDateTime())
                        .timestamp(System.currentTimeMillis())
                        .build();
                List<ContestEntity> matchingContests = contestRepository.findAllByTitleContaining(contestDto.getTitle());
                if (matchingContests.isEmpty()) {
                    contestRepository.save(contestEntity);
                }
            } else {
                System.out.println("No content found.");
            }
        }
    }
    public Map<String, List<ContestDto>> getAllContests() {
        List<ContestEntity> contestEntities = contestRepository.findAll();

        if (contestEntities.size() <= 20) {
            // 결과가 20개 이하인 경우, 그대로 반환
            return Map.of("data", contestEntities.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList()));
        }

        contestEntities.sort((a, b) -> Math.toIntExact(b.getTimestamp() - a.getTimestamp()));

        List<ContestEntity> contestDtos = contestEntities.stream()
                .limit(20)
                .toList();

        // 새로운 리스트를 만들어 데이터베이스에서 삭제할 데이터를 선택
        List<ContestEntity> toDeleteList = contestEntities.stream()
                .skip(20)
                .toList();

        contestRepository.deleteAll(toDeleteList);

        Map<String, List<ContestDto>> resultMap = new HashMap<>();
        resultMap.put("data", contestDtos.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList()));

        return resultMap;
    }




    private ContestDto convertToDto(ContestEntity contestEntity) {
        return ContestDto.builder()
                .id(contestEntity.getId())
                .title(contestEntity.getTitle())
                .imgUrl(contestEntity.getImgUrl())
                .dateTime(contestEntity.getDateTime())
                .build();
    }

}
