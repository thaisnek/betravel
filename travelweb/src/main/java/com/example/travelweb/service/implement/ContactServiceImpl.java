package com.example.travelweb.service.implement;

import com.example.travelweb.conventer.ContactMapper;
import com.example.travelweb.dto.request.ContactRequest;
import com.example.travelweb.dto.request.ReplyRequest;
import com.example.travelweb.dto.response.ContactResponse;
import com.example.travelweb.entity.Admin;
import com.example.travelweb.entity.Contact;
import com.example.travelweb.entity.User;
import com.example.travelweb.repository.AdminRepository;
import com.example.travelweb.repository.ContactRepository;
import com.example.travelweb.repository.UserRepository;
import com.example.travelweb.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ContactServiceImpl implements ContactService {
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private ContactMapper contactMapper;
    @Autowired
    private JavaMailSender mailSender;

    public ContactResponse createContact(ContactRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Contact contact = contactMapper.toEntity(request);
        contact.setUser(user);
        contact.setIsReply(false);
        contact.setAdmin(null);

        Contact saved = contactRepository.save(contact);
        return contactMapper.toDto(saved);
    }

    public Page<ContactResponse> getUnrepliedContacts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Contact> contactsPage = contactRepository.findByIsReplyFalse(pageable);
        return contactsPage.map(contactMapper::toDto);
    }

    public boolean replyContact(ReplyRequest request) {
        Optional<Contact> optContact = contactRepository.findById(request.getChatID());
        if (optContact.isEmpty()) return false;
        Contact contact = optContact.get();

        Admin admin = adminRepository.findById(request.getAdminId())
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(contact.getEmail());
        message.setSubject("Phản hồi liên hệ");
        message.setText(request.getReplyMessage());
        mailSender.send(message);

        contact.setIsReply(true);
        contact.setAdmin(admin);
        contactRepository.save(contact);
        return true;
    }
}
