package com.shoppingcenter.domain.user.usecase;

import com.shoppingcenter.domain.UploadFile;

public interface UploadUserImageUseCase {

    void apply(String userId, UploadFile file);

}
