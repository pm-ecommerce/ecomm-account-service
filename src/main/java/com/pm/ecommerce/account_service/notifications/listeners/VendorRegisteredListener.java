package com.pm.ecommerce.account_service.notifications.listeners;

import com.pm.ecommerce.account_service.notifications.config.QueueNotification;
import com.pm.ecommerce.account_service.notifications.config.TemplateParser;
import com.pm.ecommerce.account_service.notifications.events.VendorRegisteredEvent;
import com.pm.ecommerce.account_service.repositories.NotificationRepository;
import com.pm.ecommerce.entities.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class VendorRegisteredListener implements ApplicationListener<VendorRegisteredEvent> {
    @Autowired
    QueueNotification queueNotification;

    @Autowired
    NotificationRepository repository;

    @Autowired
    TemplateParser parser;

    @Value("${site.owner.email:ioesandeep@gmail.com}")
    String adminEmail;

    @Override
    public void onApplicationEvent(VendorRegisteredEvent event) {
        Notification notification = new Notification();
        notification.setReceiver(adminEmail);
        notification.setSubject("A new vendor has registered");
        String message = parser.parseTemplate("templates/vendor-registered", event.getVendor());
        notification.setMessage(message);
        queueNotification.queue(notification);
    }
}
