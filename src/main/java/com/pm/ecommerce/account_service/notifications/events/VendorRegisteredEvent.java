package com.pm.ecommerce.account_service.notifications.events;

import com.pm.ecommerce.entities.Vendor;
import org.springframework.context.ApplicationEvent;

public class VendorRegisteredEvent extends ApplicationEvent {
    private final Vendor vendor;

    public VendorRegisteredEvent(Object source, Vendor vendor) {
        super(source);
        this.vendor = vendor;
    }
    public Vendor getVendor(){
        return this.vendor;
    }
}
