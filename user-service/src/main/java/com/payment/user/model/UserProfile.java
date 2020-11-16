package com.payment.user.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
@NoArgsConstructor
public class UserProfile {
    @Id
    private String username;
    private String fullName;
    @Indexed(unique = true)
    private String mobile;
    private String password;

}
