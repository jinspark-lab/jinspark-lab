package com.mainlab.service;

import com.mainlab.common.ObjectConvertService;
import com.mainlab.model.message.AppMessageType;
import com.mainlab.model.message.AppSimpleMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;

@Service
public class MessageService {

    @Value("${service.app.coordinator.queue}")
    private String coordinatorQueue;
    @Autowired
    private SqsClient sqsClient;
    @Autowired
    private ObjectConvertService objectConvertService;
    @Autowired
    private AppLogService appLogService;

    public boolean sendMessageTest(String message) {
        try {
            AppSimpleMessage appSimpleMessage = new AppSimpleMessage(AppMessageType.TEST);
            appSimpleMessage.setMessage(message);

            SendMessageRequest messageRequest = SendMessageRequest.builder()
                    .queueUrl(coordinatorQueue)
                    .messageBody(objectConvertService.objToString(appSimpleMessage))
                    .build();
            SendMessageResponse sendMessageResponse = sqsClient.sendMessage(messageRequest);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    // FIXME: To think how to track its whole response.
    // TODO: Maybe make separate coordinator platform rather than Messaging
//    public void sendMessage(AppTransactionMessage appTransactionMessage) {
//        SendMessageRequest messageRequest = SendMessageRequest.builder()
//                .queueUrl(coordinatorQueue)
//                .messageBody(objectConvertService.objToString(appTransactionMessage))
//                .build();
//        SendMessageResponse sendMessageResponse = sqsClient.sendMessage(messageRequest);
//        appLogService.test("Send Sqs Message - " + sendMessageResponse.messageId());
//    }
}
