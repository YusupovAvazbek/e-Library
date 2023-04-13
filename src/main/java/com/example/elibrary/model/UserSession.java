package com.example.elibrary.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Setter
@RedisHash(timeToLive = 60 * 60)
@AllArgsConstructor
public class UserSession {
    @Id
    String uuid;
    String userInfo;

}
