package com.xiaotree.jinyuserver.handler;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.system.SystemUtil;
import cn.hutool.system.oshi.CpuInfo;
import cn.hutool.system.oshi.OshiUtil;
import com.xiaotree.jinyuserver.domain.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;

import java.util.LinkedHashMap;

@Slf4j
@Component
public class MyWebSocketHandler extends TextWebSocketHandler {

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("WebSocket 连接成功: {}", session.getId());
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        CentralProcessor.ProcessorIdentifier cpu = OshiUtil.getProcessor().getProcessorIdentifier();
        CpuInfo cpuInfo = OshiUtil.getCpuInfo();
        GlobalMemory memory = OshiUtil.getMemory();
        map.put("cpu", cpu.getName());
        map.put("cpuNum", cpuInfo.getCpuNum());
        map.put("cpuUsed", cpuInfo.getUsed());
        map.put("memory", NumberUtil.div(memory.getTotal(), 1024 * 1024 * 1024, 0));
        map.put("memoryUsed", NumberUtil.div(memory.getTotal() - memory.getAvailable(), memory.getTotal(), 2) * 100);
        map.put("os", SystemUtil.getOsInfo().getName());
        map.put("javaVersion",SystemUtil.getJavaInfo().getVersion());
        map.put("jvm",SystemUtil.getJvmInfo().getInfo());
        session.sendMessage(Message.json("info", map));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("收到消息: {}", message.getPayload());
        CpuInfo cpuInfo = OshiUtil.getCpuInfo();
        GlobalMemory memory = OshiUtil.getMemory();
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("cpuUsed", cpuInfo.getUsed());
        map.put("memoryUsed", NumberUtil.div(memory.getTotal() - memory.getAvailable(), memory.getTotal(), 2) * 100);
        session.sendMessage(Message.json("data", map));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("WebSocket 连接关闭: {} , {}", session.getId(), status);
    }
}
