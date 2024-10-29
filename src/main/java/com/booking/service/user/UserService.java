package com.booking.service.user;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.booking.bean.dto.user.UserDTO;
import com.booking.bean.pojo.user.User;
import com.booking.dao.user.UserRepository;
import com.booking.dao.user.UserSpecification;
import com.booking.utils.DaoResult;
import com.booking.utils.MyModelMapper;
import com.booking.utils.MyPageRequest;
import com.booking.utils.Result;
import com.booking.utils.UploadImageFile;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private EmailService emailService;

	@Transactional
	public User registerUser(User user) {
		if (userRepository.existsByUserAccount(user.getUserAccount())) {
			throw new RuntimeException("Error: Username is already taken!");
		}
		if (userRepository.existsByUserMail(user.getUserMail())) {
			throw new RuntimeException("Error: Email is already in use!");
		}

		user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
		user.setEmailVerified(false);
		user.setVerificationToken(UUID.randomUUID().toString());
		User savedUser = userRepository.save(user);
		emailService.sendVerificationEmail(savedUser);
		return savedUser;
	}

	public User findByUserAccount(String userAccount) {
		return userRepository.findByUserAccount(userAccount).orElseThrow(() -> new RuntimeException("User not found"));
	}

	/**
	 * 根據user的account查找UserDTO
	 * 
	 * @param account
	 * @return
	 */
	public Result<UserDTO> findUserDTOByAccount(String account) {
		User user = userRepository.findByUserAccount(account).orElse(null);
		if (user == null) {
			return Result.failure("根據使用者名稱，找不到使用者");
		}
		UserDTO userDTO = new UserDTO();
		BeanUtils.copyProperties(user, userDTO);

		return Result.success(userDTO);
	}

	public User findByUserMail(String userMail) {
		return userRepository.findByUserMail(userMail).orElseThrow(() -> new RuntimeException("User not found"));
	}

	@Transactional
	public void verifyEmail(String token) {
		User user = userRepository.findByVerificationToken(token)
				.orElseThrow(() -> new RuntimeException("Invalid verification token"));
		user.setEmailVerified(true);
		user.setVerificationToken(null);
		userRepository.save(user);
	}

	@Transactional
	public void initiatePasswordReset(String email) {
		User user = findByUserMail(email);
		user.setResetToken(UUID.randomUUID().toString());
		userRepository.save(user);
		emailService.sendPasswordResetEmail(user);
	}

	@Transactional
	public boolean resetPassword(String token, String newPassword) {
		User user = userRepository.findByResetToken(token)
				.orElseThrow(() -> new RuntimeException("Invalid reset token"));
		if (user.isResetTokenValid()) {
			user.setUserPassword(passwordEncoder.encode(newPassword));
			user.clearResetToken();
			userRepository.save(user);
			return true;
		}
		return false;
	}

	@Transactional
	public User processOAuthPostLogin(String email, String provider, String providerId) {
		User existUser = userRepository.findByProviderAndProviderId(provider, providerId).orElse(null);
		if (existUser == null) {
			User newUser = new User();
			newUser.setUserMail(email);
			newUser.setProvider(provider);
			newUser.setProviderId(providerId);
			newUser.setEmailVerified(true);

			// 設置 userAccount
			String userAccount = email.split("@")[0]; // 使用郵箱的用戶名部分作為 userAccount
			newUser.setUserAccount(userAccount);

			// 設置一個隨機的密碼（因為OAuth2用戶不需要密碼登錄）
			newUser.setUserPassword(passwordEncoder.encode(UUID.randomUUID().toString()));

			// 設置其他必要的字段
			newUser.setUserName(email.split("@")[0]); // 使用郵箱的用戶名部分作為 userName
			newUser.setCreatedTime(LocalDateTime.now());

			return userRepository.save(newUser);
		}
		return existUser;
	}

	@Transactional
	public User updateUserProfile(User user) {
		User existingUser = userRepository.findById(user.getUserId())
				.orElseThrow(() -> new RuntimeException("User not found"));

		// 更新基本資料
		existingUser.setUserName(user.getUserName());
		existingUser.setUserPhone(user.getUserPhone());
		existingUser.setUserBirthday(user.getUserBirthday());
		existingUser.setUserAddress(user.getUserAddress());

		// 保存更新時間
		existingUser.setUpdatedTime(LocalDateTime.now());

		// 不更新敏感資料
		// existingUser.setUserAccount(user.getUserAccount());
		// existingUser.setUserPassword(user.getUserPassword());
		// existingUser.setUserMail(user.getUserMail());

		return userRepository.save(existingUser);
	}

///////////////////////////////////////////////////////////////////////////////////

	public Result<PageImpl<UserDTO>> findUserAll(UserDTO userDTO) {

		Integer pageNumber = userDTO.getPageNumber();
		String attrOrderBy = userDTO.getAttrOrderBy();
		Boolean selectedSort = userDTO.getSelectedSort();
		Pageable pageable = MyPageRequest.of(pageNumber, 10, selectedSort, attrOrderBy);
		Page<User> page = userRepository.findAll(pageable);
		List<UserDTO> userDTOs = new ArrayList<>();
		List<User> users = page.getContent();

		for (User user : users) {
			UserDTO responseUserDTO = new UserDTO();
			BeanUtils.copyProperties(user, responseUserDTO);
			userDTOs.add(responseUserDTO);
		}

		PageRequest newPageable = PageRequest.of(page.getNumber(), page.getSize(), page.getSort());
		return Result.success(new PageImpl<>(userDTOs, newPageable, page.getTotalElements()));
	}

	/**
	 * 返回所有景點
	 * 
	 * @return
	 */
	public List<UserDTO> findAllUsers() {
		List<User> allUsers = userRepository.findAll();
		return allUsers.stream().map(user -> {
			UserDTO userDTO = new UserDTO();
			BeanUtils.copyProperties(user, userDTO);
			return userDTO;
		}).collect(Collectors.toList());
	}

	/**
	 * 依模糊查詢得到多筆景點
	 * 
	 * @param attractionDTO
	 * @return
	 */
	public Result<PageImpl<UserDTO>> findUsers(UserDTO userDTO) {
		Specification<User> spec = Specification.where(UserSpecification.nameContains(userDTO.getUserName()))
				.and(UserSpecification.accountContains(userDTO.getUserAccount()));

		Pageable pageable = MyPageRequest.of(userDTO.getPageNumber(), 10, userDTO.getSelectedSort(),
				userDTO.getAttrOrderBy());

		Page<User> page = userRepository.findAll(spec, pageable);
		List<User> users = page.getContent();
		List<UserDTO> usersDTOs = new ArrayList<>();

		for (User user : users) {
			UserDTO responseUserDTO = new UserDTO();

			BeanUtils.copyProperties(user, responseUserDTO);
			usersDTOs.add(responseUserDTO);

		}

		PageRequest newPageable = PageRequest.of(page.getNumber(), page.getSize(), page.getSort());
		return Result.success(new PageImpl<>(usersDTOs, newPageable, page.getTotalElements()));

	}

	/**
	 * 依名稱獲取景點
	 * 
	 * @param attractionName
	 * @return
	 */
	public Result<List<UserDTO>> findUserByName(String userName) {
		DaoResult<List<User>> getUserByNameResult = userRepository.getuserByName(userName);

		if (getUserByNameResult.isFailure()) {
			return Result.failure("根據會員名稱獲取會員失敗");
		}

		List<UserDTO> list = new ArrayList<>();
		List<User> users = getUserByNameResult.getData();

		for (User user : users) {
			UserDTO userDTO = new UserDTO();
			BeanUtils.copyProperties(user, userDTO);
			list.add(userDTO);
		}

		return Result.success(list);
	}

	/**
	 * 依id獲取景點
	 * 
	 * @param attractionId
	 * @return
	 */
	public Result<UserDTO> findUserById(Integer userId) {
		Optional<User> optional = userRepository.findById(userId);
		if (optional.isEmpty()) {
			return Result.failure("沒有此會員");
		}
		User user = optional.get();
		UserDTO userDTO = new UserDTO();
		BeanUtils.copyProperties(user, userDTO);
		return Result.success(userDTO);
	}

	/**
	 * 新增景點
	 * 
	 * @param attraction
	 * @return
	 */
	@Transactional
	public Result<String> saveUser(UserDTO userDTO, MultipartFile imageFile) {
		Result<String> uploadResult = UploadImageFile.upload(imageFile);

		if (uploadResult.isSuccess()) {
			String fileName = imageFile.getOriginalFilename();
			userDTO.setImgsFile("uploads" + "/" + fileName);
		} else {
			userDTO.setImgsFile("uploads/default.jpg");
		}
		User user = new User();

		BeanUtils.copyProperties(userDTO, user);

		userRepository.save(user);

		return Result.success("新增會員成功");
	}

	/**
	 * 依id刪除景點
	 * 
	 * @param attractionId
	 */
	@Transactional
	public Result<String> deledeUserById(Integer userId) {
		userRepository.deleteById(userId);

		return Result.success("刪除會員成功");

	}

	/**
	 * 更新景點
	 * 
	 * @param attractionDTO
	 * @return
	 */
	@Transactional
	public Result<String> updateUser(UserDTO userDTO, MultipartFile imageFile) {
		User existingUser = userRepository.findById(userDTO.getUserId()).orElse(null);
		if (existingUser == null) {
			return Result.failure("會員不存在");
		}

		if (imageFile != null && !imageFile.isEmpty()) {
			Result<String> uploadResult = UploadImageFile.upload(imageFile);
			if (uploadResult.isSuccess()) {
				String fileName = imageFile.getOriginalFilename();
				userDTO.setImgsFile("uploads" + "/" + fileName);
			} else {
				userDTO.setImgsFile("uploads/default.jpg");
			}
		} else {
			userDTO.setImgsFile(existingUser.getImgsFile());
		}

		MyModelMapper.map(userDTO, existingUser);
		userRepository.save(existingUser);
		return Result.success("更新成功");
	}

	/**
	 * 根據id上傳圖片
	 * 
	 * @param imageFile
	 * @param attractionId
	 * @return
	 */
	public Result<String> uploadImageById(MultipartFile imageFile, Integer userId) {
		User user = userRepository.findById(userId).orElse(null);
		if (user == null) {
			return Result.failure("沒有此會員");
		}

		Result<String> uploadImageResult = UploadImageFile.upload(imageFile);

		if (uploadImageResult.isFailure()) {
			return Result.failure(uploadImageResult.getMessage());
		}

		Path path = (Path) uploadImageResult.getExtraData("path");
		user.setImgsFile(path.toString());

		return Result.success("上傳圖片成功");
	}

	public Result<UrlResource> findImageById(Integer userId) {
		User user = userRepository.findById(userId).orElse(null);

		if (user == null) {
			return Result.failure("根據ID查找不到會員");
		}

		String imagesFile = user.getImgsFile();

		Path path = Paths.get(imagesFile);
		try {
			UrlResource urlResource = new UrlResource(path.toUri());
			if (urlResource.exists() || urlResource.isReadable()) {
				return Result.success(urlResource).setExtraData("path", path);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		DaoResult<?> updateUserResult = userRepository.updateUser(user);
		if (updateUserResult.isFailure()) {
			return Result.failure("更新失敗");
		}
		return Result.success("更新會員成功");
	}
	
	public Integer getCurrentUserId() {
		String userAccount = SecurityContextHolder.getContext().getAuthentication().getName();
		return userRepository.findByUserAccount(userAccount).get().getUserId();
	}

}
