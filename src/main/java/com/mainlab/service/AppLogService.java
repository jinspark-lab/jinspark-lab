package com.mainlab.service;

import com.mainlab.common.ObjectConvertService;
import com.mainlab.model.log.AppLogRecord;
import com.mainlab.model.log.FirehoseConnector;
import com.mainlab.model.log.LogLevel;
import com.mainlab.model.log.RecordType;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.firehose.model.PutRecordRequest;
import software.amazon.awssdk.services.firehose.model.PutRecordResponse;
import software.amazon.awssdk.services.firehose.model.Record;

@Service
public class AppLogService {

    @Value("${application.log.firehose}")
    private String deliveryStream;

    @Autowired
    private FirehoseConnector firehoseConnector;
    @Autowired
    private ObjectConvertService objectConvertService;
    @Autowired
    private UserService userService;

    private String generateAppLogRecord(LogLevel logLevel, String message) {
        AppLogRecord appLogRecord = AppLogRecord.builder()
//                .userId(userService.getUserContextHolder().getUserId())
                .logLevel(logLevel)
                .recordType(RecordType.LOG)
                .message(message)
                .dateTime(DateTime.now())
                .build();
        return objectConvertService.objToString(appLogRecord);
    }

    private void log(LogLevel logLevel, String message) {
        sendLog(generateAppLogRecord(logLevel, message));
    }

    public void test(String message) {
        log(LogLevel.TEST, message);
    }

    public void debug(String message) {
        log(LogLevel.DEBUG, message);
    }

    public void info(String message) {
        log(LogLevel.INFO, message);
    }

    public void warn(String message) {
        log(LogLevel.WARN, message);
    }

    public void error(String message) {
        log(LogLevel.ERROR, message);
    }

    private void sendLog(String data) {
        data += "\n";
        try {
            PutRecordRequest putRecordRequest = PutRecordRequest.builder()
                    .deliveryStreamName(deliveryStream)
                    .record(Record.builder().data(SdkBytes.fromUtf8String(data)).build())
                    .build();
            PutRecordResponse putRecordResponse = firehoseConnector.getSyncClient().putRecord(putRecordRequest);
            System.out.println("Record ID : " + putRecordResponse.recordId());
        } catch (Exception e) {
            System.out.println(data);
            System.out.println(e.getMessage());
        }
    }
}
