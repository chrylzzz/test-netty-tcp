package com.sdsoon.netty5;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 类定义
 * <p>
 * Created By Chr on 2019/4/29.
 */
public class NettyMessage implements Serializable {

    private static final long serialVersionUID = 943011770871630076L;

    private Handler handler;
    private Object body;

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    /**
     * 消息头
     */
    public final class Handler {
        private int crcCode = 0xabef0101;
        private int length;//消息长度
        private long sessionID;//回话id
        private byte type;//消息类型
        private byte priority;//消息优先级
        private Map<String, Object> attachment = new HashMap<>();//附件

        public int getCrcCode() {
            return crcCode;
        }

        public void setCrcCode(int crcCode) {
            this.crcCode = crcCode;
        }

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }

        public long getSessionID() {
            return sessionID;
        }

        public void setSessionID(long sessionID) {
            this.sessionID = sessionID;
        }

        public byte getType() {
            return type;
        }

        public void setType(byte type) {
            this.type = type;
        }

        public byte getPriority() {
            return priority;
        }

        public void setPriority(byte priority) {
            this.priority = priority;
        }

        public Map<String, Object> getAttachment() {
            return attachment;
        }

        public void setAttachment(Map<String, Object> attachment) {
            this.attachment = attachment;
        }

        @Override
        public String toString() {
            return "Handler{" +
                    "crcCode=" + crcCode +
                    ", length=" + length +
                    ", sessionID=" + sessionID +
                    ", type=" + type +
                    ", priority=" + priority +
                    ", attachment=" + attachment +
                    '}';
        }
    }
}
