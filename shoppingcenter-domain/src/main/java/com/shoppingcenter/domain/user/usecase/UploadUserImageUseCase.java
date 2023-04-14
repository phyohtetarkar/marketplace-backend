package com.shoppingcenter.domain.user.usecase;

import com.shoppingcenter.domain.UploadFile;

public interface UploadUserImageUseCase {

    void apply(long userId, UploadFile file);

}
