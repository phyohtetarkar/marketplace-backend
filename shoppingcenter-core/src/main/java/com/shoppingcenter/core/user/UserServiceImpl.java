package com.shoppingcenter.core.user;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.shoppingcenter.core.ApplicationException;
import com.shoppingcenter.core.Constants;
import com.shoppingcenter.core.ErrorCodes;
import com.shoppingcenter.core.PageData;
import com.shoppingcenter.core.UploadFile;
import com.shoppingcenter.core.Utils;
import com.shoppingcenter.core.storage.FileStorageService;
import com.shoppingcenter.core.user.model.User;
import com.shoppingcenter.data.BasicSpecification;
import com.shoppingcenter.data.SearchCriteria;
import com.shoppingcenter.data.SearchCriteria.Operator;
import com.shoppingcenter.data.user.UserEntity;
import com.shoppingcenter.data.user.UserEntity.Role;
import com.shoppingcenter.data.user.UserRepo;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo repo;

	@Autowired
	private FileStorageService storageService;

	@Value("${app.image.base-url}")
	private String imageUrl;

	@Value("${app.image.base-path}")
	private String imagePath;

	@Override
	public void create(User user) {
		if (repo.existsById(user.getId())) {
			throw new ApplicationException(ErrorCodes.USER_ALREADY_CREATED);
		}

		UserEntity entity = new UserEntity();
		entity.setId(user.getId());
		entity.setName(user.getName());
		entity.setPhone(user.getPhone());
		entity.setEmail(user.getEmail());
		entity.setRole(Role.USER);

		repo.save(entity);
	}

	@Override
	public void update(User user) {
		if (!repo.existsById(user.getId())) {
			throw new ApplicationException(ErrorCodes.INVALID_ARGUMENT);
		}
		UserEntity entity = repo.getReferenceById(user.getId());
		entity.setName(user.getName());
		entity.setEmail(user.getEmail());
		repo.save(entity);
	}

	@Override
	public void uploadImage(String userId, UploadFile file) {
		try {
			if (file == null || file.getSize() <= 0) {
				throw new RuntimeException("Empty upload content");
			}
			UserEntity entity = repo.getReferenceById(userId);
			String dir = imagePath + File.separator + "user";
			String image = storageService.write(file, dir, "user-" + entity.getId());
			entity.setImage(image);
		} catch (Exception e) {
			throw new ApplicationException(ErrorCodes.EXECUTION_FAILED, "Image upload failed");
		}
	}

	@Override
	public void changePhoneNumber(String userId, String phoneNumber) {
		if (!repo.existsById(userId)) {
			throw new ApplicationException(ErrorCodes.INVALID_ARGUMENT);
		}
		UserEntity entity = repo.getReferenceById(userId);
		entity.setPhone(phoneNumber);
	}

	@Override
	public void delete(String id) {

	}

	@Override
	public void updateRole(String userId, Role role) {
		if (!repo.existsById(userId)) {
			throw new ApplicationException(ErrorCodes.INVALID_ARGUMENT);
		}
		UserEntity entity = repo.getReferenceById(userId);
		entity.setRole(role);
	}

	@Override
	public User findById(String id) {
		return repo.findById(id).map(e -> User.create(e, imageUrl))
				.orElseThrow(() -> new ApplicationException(ErrorCodes.NOT_FOUND));
	}

	@Override
	public PageData<User> findAll(UserQuery query) {
		Specification<UserEntity> spec = null;
		if (StringUtils.hasText(query.getPhone())) {
			Specification<UserEntity> phoneSpec = new BasicSpecification<>(
					new SearchCriteria("phone", Operator.EQUAL, query.getPhone()));
			spec = Specification.where(phoneSpec);
		}

		if (StringUtils.hasText(query.getName())) {
			String name = query.getName().toLowerCase();
			Specification<UserEntity> nameSpec = new BasicSpecification<>(
					new SearchCriteria("name", Operator.LIKE, name));
			spec = spec != null ? spec.and(nameSpec) : Specification.where(nameSpec);
		}

		Sort sort = Sort.by(Order.desc("createdAt"));

		Pageable pageable = PageRequest.of(Utils.normalizePage(query.getPage()), Constants.PAGE_SIZE, sort);

		Page<UserEntity> pageResult = repo.findAll(spec, pageable);

		return PageData.build(pageResult, e -> User.create(e, imageUrl));
	}

}
