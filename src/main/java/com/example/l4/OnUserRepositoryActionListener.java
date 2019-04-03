package com.example.l4;

import java.util.List;

public interface OnUserRepositoryActionListener {
    void actionSuccess();
    void actionSuccess(List<User> result);
    void actionFailed();
}

