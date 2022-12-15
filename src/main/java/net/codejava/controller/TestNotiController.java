package net.codejava.controller;

import com.google.firebase.messaging.BatchResponse;
import lombok.RequiredArgsConstructor;
import net.codejava.request.Notice;
import net.codejava.services.FirebaseMessagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class TestNotiController {

    private final FirebaseMessagingService firebaseMessagingService;

    @RequestMapping("/send-notification")
    @ResponseBody
    public BatchResponse sendNotification(@RequestBody Notice notice) {
        return firebaseMessagingService.sendNotification(notice);
    }
}
