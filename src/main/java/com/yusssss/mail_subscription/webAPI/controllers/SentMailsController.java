package com.yusssss.mail_subscription.webAPI.controllers;

import com.yusssss.mail_subscription.business.abstracts.SentMailService;
import com.yusssss.mail_subscription.core.results.DataResult;
import com.yusssss.mail_subscription.core.results.Result;
import com.yusssss.mail_subscription.core.results.SuccessDataResult;
import com.yusssss.mail_subscription.entities.SentMail;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/sentMails")
public class SentMailsController {

    private final SentMailService sentMailService;

    public SentMailsController(SentMailService sentMailService) {
        this.sentMailService = sentMailService;
    }

    @GetMapping("/getAllSentMails")
    public ResponseEntity<DataResult<List<SentMail>>> getAllSentMails(HttpServletRequest request) {
        var result = sentMailService.getAllSentMails();
        return ResponseEntity.ok(new SuccessDataResult<>(result, HttpStatus.OK, request.getRequestURI()));
    }

    @GetMapping("/getSentMailById")
    public ResponseEntity<DataResult<SentMail>> getSentMailById(@RequestParam UUID id, HttpServletRequest request) {
        var result = sentMailService.getSentMailById(id);
        return ResponseEntity.ok(new SuccessDataResult<>(result, HttpStatus.OK, request.getRequestURI()));
    }

    @GetMapping("/getSentMailsByReceiverId")
    public ResponseEntity<DataResult<List<SentMail>>> getSentMailsByReceiverId(@RequestParam UUID userId, HttpServletRequest request) {
        var result = sentMailService.getSentMailsByReceiverId(userId);
        return ResponseEntity.ok(new SuccessDataResult<>(result, HttpStatus.OK, request.getRequestURI()));
    }

    @PostMapping("/saveSentMail")
    public ResponseEntity<DataResult<SentMail>> saveSentMail(@RequestBody SentMail sentMail, HttpServletRequest request) {
        var result = sentMailService.saveSentMail(sentMail);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SuccessDataResult<>(result, HttpStatus.CREATED, request.getRequestURI()));
    }

    @PostMapping(value = "/sendMailToSubscribers", consumes = "multipart/form-data")
    public ResponseEntity<Result> sendMailToSubscribers(
            @RequestPart("subject") String subject,
            @RequestPart(value = "contentFile", required = false) MultipartFile contentFile,
            @RequestPart(value = "content", required = false) String content,
            HttpServletRequest request) throws IOException {

        String mailContent = content;
        if (contentFile != null && !contentFile.isEmpty()) {
            mailContent = new String(contentFile.getBytes(), StandardCharsets.UTF_8);
        }

        SentMail sentMail = new SentMail();
        sentMail.setSubject(subject);
        sentMail.setContent(mailContent);

        sentMailService.sendMailToSubscribers(sentMail, contentFile != null && !contentFile.isEmpty());
        return ResponseEntity.ok(new SuccessDataResult<>("Mail abonelere gönderildi.", HttpStatus.OK, request.getRequestURI()));
    }

    @GetMapping(value = "/unsubscribeFromMails/{sentMailId}")
    public ResponseEntity<Result> unsubscribeFromMails(@PathVariable UUID sentMailId, HttpServletRequest request) {
        sentMailService.unsubscribeFromMails(sentMailId);
        return ResponseEntity.ok(new SuccessDataResult<>("Abonelikten çıkıldı.", HttpStatus.OK, request.getRequestURI()));
    }
}