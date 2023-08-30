package com.innosync.hook.Service;

import com.innosync.hook.dto.ContestDto;
import com.innosync.hook.entity.ContestEntity;
import com.innosync.hook.repository.ContestRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ContestService {
    private final ContestRepository contestRepository;
    private final ModelMapper modelMapper;

    private static String CONTEST_URL = "https://www.onoffmix.com/event/main?s=%ED%95%B4%EC%BB%A4%ED%86%A4";

    @PostConstruct
    public void getContestInfo() throws IOException {

        Document doc = Jsoup.connect(CONTEST_URL).get();
        Elements contents = doc.select("#content > div > section.event_main_area > ul > li:nth-child(3) > article.event_area.event_main > a > div.event_thumbnail > img");
        // li:nth-child(1) () 안에 값 바꿔서 게시물 순서 조정가능
        Elements dateContents = doc.select("#content > div > section.event_main_area > ul > li:nth-child(3) > article.event_area.event_main > a > div.event_info_area > div.event_info > div.date");

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
                    .build();


            contestRepository.save(contestEntity);

        } else {
            System.out.println("No content found.");
        }
    }
}
