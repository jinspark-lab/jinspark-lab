package com.mainlab.model.log;

import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.firehose.FirehoseAsyncClient;

@Component("firehoseConnector")
public class FirehoseConnector {

    public software.amazon.awssdk.services.firehose.FirehoseClient getSyncClient() {
        return software.amazon.awssdk.services.firehose.FirehoseClient.create();
    }

    public FirehoseAsyncClient getAsyncClient() {
        return FirehoseAsyncClient.create();
    }
}
