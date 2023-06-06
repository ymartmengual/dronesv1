package com.example.drones.services.impl;

import com.example.drones.entity.Audit;
import com.example.drones.repository.IAuditRepository;
import com.example.drones.services.IAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuditServiceImpl implements IAuditService {

    @Autowired
    private IAuditRepository auditRepository;

    @Override
    public Audit save(Audit audit) {
        return auditRepository.save(audit);
    }
}
