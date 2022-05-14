package com.project.club.api.dto;

import lombok.Data;

@Data
public class LockResponseDto {

    private boolean isLock;

    public LockResponseDto(boolean isLock)
    {
        this.isLock = isLock;
    }

}
