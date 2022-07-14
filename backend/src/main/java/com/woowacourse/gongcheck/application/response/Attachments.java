package com.woowacourse.gongcheck.application.response;

import com.slack.api.model.Attachment;
import com.slack.api.model.Field;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

@Getter
public class Attachments {

    private List<Attachment> attachments;

    private Attachments() {
    }

    private Attachments(final List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public static Attachments of(final SubmissionResponse submissionResponse) {
        return new Attachments(List.of(Attachment.builder()
                .fallback("📝 체크리스트가 제출되었습니다.")
                .color("#99CCFF")
                .pretext("📝 체크리스트가 제출되었습니다.")
                .fields(List.of(
                        Field.builder().value("제출자명 : " + submissionResponse.getAuthor()).build(),
                        Field.builder().value("공간이름 : " + submissionResponse.getSpaceName()).build(),
                        Field.builder().value("작업이름 : " + submissionResponse.getJobName()).build()))
                .footer("제출시간")
                .ts(String.valueOf(Timestamp.valueOf(LocalDateTime.now()).getTime()))
                .build()));
    }
}
