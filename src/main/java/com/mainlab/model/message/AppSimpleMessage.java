package com.mainlab.model.message;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppSimpleMessage extends BaseAppMessage {
    private static final long serialVersionUID = -3490338668167199731L;

    private String message;

    public AppSimpleMessage(AppMessageType appMessageType) {
        super(appMessageType);
    }

    @Override
    public Object getMessageBody() {
        return message;
    }
}
