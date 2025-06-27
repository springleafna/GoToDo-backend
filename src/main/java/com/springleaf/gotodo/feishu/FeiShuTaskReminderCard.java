package com.springleaf.gotodo.feishu;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * 飞书任务提醒卡片实体类
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FeiShuTaskReminderCard {

    @JsonProperty("msg_type")
    private final String msgType = "interactive";

    private final Card card;

    public FeiShuTaskReminderCard(String title, String categoryName, String remark, String reminderTime, String endTime) {
        this.card = new Card(title, categoryName, remark, reminderTime, endTime);
    }

    @Data
    private static class Card {
        private final String schema = "2.0";
        private final Config config = new Config();
        private final Header header = new Header();
        private final Body body;

        public Card(String title, String categoryName, String remark, String reminderTime, String endTime) {
            this.body = new Body(title, categoryName, remark, reminderTime, endTime);
        }
    }

    @Data
    private static class Config {
        @JsonProperty("update_multi")
        private final Boolean updateMulti = true;
        private final Style style = new Style();
    }

    @Data
    private static class Style {
        @JsonProperty("text_size")
        private final TextSize textSize = new TextSize();
    }

    @Data
    private static class TextSize {
        @JsonProperty("normal_v2")
        private final NormalV2 normalV2 = new NormalV2();
    }

    @Data
    private static class NormalV2 {
        private final String pc = "normal";
        private final String mobile = "heading";
        @JsonProperty("default")
        private final String defaults = "normal";
    }

    @Data
    private static class Header {
        private final Title title = new Title("⏰ 任务提醒");
        private final String template = "blue";
        private final String padding = "12px 12px 12px 12px";
    }

    @Data
    private static class Title {
        private final String tag = "plain_text";
        private final String content;
    }

    @Data
    private static class Body {
        private final String direction = "vertical";
        private final String padding = "12px 12px 12px 12px";
        private final List<Element> elements;

        public Body(String title, String categoryName, String remark, String reminderTime, String endTime) {
            this.elements = List.of(
                    createMarkdownElement(title, categoryName, remark, reminderTime, endTime)
            );
        }

        private Element createMarkdownElement(String title, String categoryName, String remark, String reminderTime, String endTime) {
            Element element = new Element();
            element.setTag("markdown");
            element.setContent(String.format(
                    """
                            **任务名称**: %s
                            
                            **所属分类**: %s
                            
                            **任务备注**: %s
                            
                            **提醒时间**: %s
                            
                            **结束时间**: %s""",
                    title, categoryName, remark, reminderTime, endTime
            ));
            element.setTextAlign("left");
            element.setTextSize("normal_v2");
            return element;
        }
    }

    @Data
    private static class Element {
        private String tag;
        private String content;
        @JsonProperty("text_align")
        private String textAlign;
        @JsonProperty("text_size")
        private String textSize;
    }
}