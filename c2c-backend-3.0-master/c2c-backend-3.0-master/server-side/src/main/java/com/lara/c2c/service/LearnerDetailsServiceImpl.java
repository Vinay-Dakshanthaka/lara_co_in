package com.lara.c2c.service;

import com.lara.c2c.model.Learner;
import com.lara.c2c.repository.LearnerRepository;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LearnerDetailsServiceImpl implements UserDetailsService {

    @Autowired
    LearnerRepository learnerRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email)
            throws EntityNotFoundException {
    	
        Learner learner = learnerRepository.findByEmail(email)
                	.orElseThrow(() -> 
                        new EntityNotFoundException(email + " is not registered with us.")
        );

        return LearnerPrinciple.build(learner);
    }
}