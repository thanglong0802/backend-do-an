package com.api.base.service.impl;

import com.api.base.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author BacDV
 *
 */
@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public User loadUserByUsername(String mId) {
//        Member member = memberRepository.findByMId(mId).orElseThrow(() -> new UsernameNotFoundException(MessageCode.ERR_401.name()));
        User user = new User();
//        user.setUsername(member.getmId());
//        user.setPassword(member.getmPass());
        return user;
    }
}
