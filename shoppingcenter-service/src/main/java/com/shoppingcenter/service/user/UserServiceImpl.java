package com.shoppingcenter.service.user;

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

import com.shoppingcenter.data.BasicSpecification;
import com.shoppingcenter.data.SearchCriteria;
import com.shoppingcenter.data.SearchCriteria.Operator;
import com.shoppingcenter.data.user.UserEntity;
import com.shoppingcenter.data.user.UserRepo;
import com.shoppingcenter.service.ApplicationException;
import com.shoppingcenter.service.Constants;
import com.shoppingcenter.service.ErrorCodes;
import com.shoppingcenter.service.PageData;
import com.shoppingcenter.service.UploadFile;
import com.shoppingcenter.service.Utils;
import com.shoppingcenter.service.storage.FileStorageService;
import com.shoppingcenter.service.user.model.User;

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
		entity.setRole(User.Role.USER.name());

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

			// String oldImage = entity.getImage();

			String dir = imagePath + File.separator + "user";
			// long millis = System.currentTimeMillis();
			String name = String.format("user-%d.%s", entity.getId(), file.getExtension());
			String image = storageService.write(file, dir, name);
			entity.setImage(image);

			// if (StringUtils.hasText(oldImage)) {
			// storageService.delete(dir, oldImage);
			// }
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
	public void updateRole(String userId, User.Role role) {
		if (!repo.existsById(userId)) {
			throw new ApplicationException(ErrorCodes.NOT_FOUND, "User not found");
		}
		UserEntity entity = repo.getReferenceById(userId);
		entity.setRole(role.name());
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
