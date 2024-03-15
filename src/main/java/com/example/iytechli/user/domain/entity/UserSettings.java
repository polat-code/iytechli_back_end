package com.example.iytechli.user.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSettings {

    private ProfileStatus profileStatus;
    private UniversityRole universityRole;
}
