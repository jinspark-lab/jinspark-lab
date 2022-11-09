package com.mainlab.model.log;

import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.firehose.FirehoseAsyncClient;

@Component("firehoseClient")
public class FirehoseClient {

    public FirehoseAsyncClient getAsyncClient() {
        return FirehoseAsyncClient.create();
    }
}
