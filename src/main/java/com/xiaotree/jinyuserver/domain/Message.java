package com.xiaotree.jinyuserver.domain;

import cn.hutool.core.date.DateTime;
import com.xiaotree.jinyuserver.util.Tools;
import lombok.Getter;
import org.springframework.web.socket.TextMessage;

@Getter
public class Message {
    private String type;
    private DateTime sendTime;
    private Object data;

    public Message(String type, Object data) {
        this.type = type;
        this.sendTime=DateTime.now();
        this.data = data;
    }

    public static TextMessage json(String type, Object data) {
        Message msg = new Message(type, data);
        return new TextMessage(Tools.objectToJson(msg));
    }
}
