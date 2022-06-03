package org.example.mytest.DAO;

import org.example.mytest.entity.Role;
import org.example.mytest.entity.Student;
import org.example.mytest.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class UserService implements UserDetailsService {
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Student student = studentRepository.findByUsername(username);
        if (student!= null) {
            return student;
        }
        throw new UsernameNotFoundException("not found");
    }

    public boolean saveUser(Student user) {
        Student student = studentRepository.findByUsername(user.getUsername());
        if (student != null) {
            return false;
        }
        user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        studentRepository.save(user);
        return true;
    }
}
