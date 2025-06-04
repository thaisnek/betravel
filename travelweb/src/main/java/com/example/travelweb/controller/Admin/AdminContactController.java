package com.example.travelweb.controller.Admin;

import com.example.travelweb.dto.request.ReplyRequest;
import com.example.travelweb.dto.response.ContactResponse;
import com.example.travelweb.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/contacts")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminContactController {
    @Autowired
    private ContactService contactService;

    @GetMapping("/all-contacts")
    public ResponseEntity<Page<ContactResponse>> getUnrepliedContacts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size) {
        Page<ContactResponse> contacts = contactService.getUnrepliedContacts(page, size);
        return ResponseEntity.ok(contacts);
    }

    @PostMapping("/reply")
    public ResponseEntity<?> replyContact(@RequestBody ReplyRequest request) {
        boolean result = contactService.replyContact(request);
        if (result) {
            return ResponseEntity.ok("Phản hồi qua email thành công.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy liên hệ.");
        }
    }
}
