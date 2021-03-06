package com.woowacourse.gongcheck.core.application.response;

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

    public static Attachments of(final SubmissionCreatedResponse submissionCreatedResponse) {
        return new Attachments(List.of(Attachment.builder()
                .fallback("π μ²΄ν¬λ¦¬μ€νΈκ° μ μΆλμμ΅λλ€.")
                .color("#99CCFF")
                .pretext("π μ²΄ν¬λ¦¬μ€νΈκ° μ μΆλμμ΅λλ€.")
                .fields(List.of(
                        Field.builder().value("μ μΆμλͺ : " + submissionCreatedResponse.getAuthor()).build(),
                        Field.builder().value("κ³΅κ°μ΄λ¦ : " + submissionCreatedResponse.getSpaceName()).build(),
                        Field.builder().value("μμμ΄λ¦ : " + submissionCreatedResponse.getJobName()).build()))
                .footer("μ μΆμκ°")
                .ts(String.valueOf(Timestamp.valueOf(LocalDateTime.now()).getTime()))
                .build()));
    }
}
