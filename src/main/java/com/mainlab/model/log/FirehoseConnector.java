package com.mainlab.model.log;

import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.firehose.FirehoseAsyncClient;
import software.amazon.awssdk.services.firehose.FirehoseClient;

@Component("firehoseConnector")
public class FirehoseConnector {

    public FirehoseClient getSyncClient() {
        return FirehoseClient.create();
    }

    public FirehoseAsyncClient getAsyncClient() {
        return FirehoseAsyncClient.create();
    }
}
