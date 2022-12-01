package com.mainlab.model.content;

import io.seruco.encoding.base62.Base62;

import java.nio.charset.StandardCharsets;

public interface ContentHashable {

    String getContentKey();

    ContentType getContentType();

    default String getContentId() {
        Base62 base62 = Base62.createInstance();
        return new String(base62.encode(getContentKey().getBytes(StandardCharsets.UTF_8)));
    }

    default String getContentLink() {
        return getContentType().getContentUrl() + "/" + getContentId();
    }
}
