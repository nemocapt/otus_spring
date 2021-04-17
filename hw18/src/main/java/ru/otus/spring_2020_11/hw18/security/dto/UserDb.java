package ru.otus.spring_2020_11.hw18.security.dto;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Document(collection = "user")
public class UserDb {
    @Id
    private String id;
    @NonNull
    private String login;
    @NonNull
    private String password;
    @NonNull
    private String role;
}
