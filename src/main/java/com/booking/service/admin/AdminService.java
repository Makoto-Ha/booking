package com.booking.service.admin;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.booking.bean.dto.admin.AdminDTO;
import com.booking.bean.dto.attraction.AttractionDTO;
import com.booking.bean.dto.booking.RoomtypeDTO;
import com.booking.bean.pojo.admin.Admin;
import com.booking.bean.pojo.attraction.Attraction;
import com.booking.bean.pojo.booking.Roomtype;
import com.booking.dao.admin.AdminRepository;
import com.booking.dao.admin.AdminSpecification;
import com.booking.dao.attraction.AttractionSpecification;
import com.booking.dao.booking.RoomtypeSpecification;
import com.booking.utils.DaoResult;
import com.booking.utils.MyModelMapper;
import com.booking.utils.MyPageRequest;
import com.booking.utils.Result;
import com.booking.utils.UploadImageFile;

@Service
@Transactional
public class AdminService {

	@Autowired
	private AdminRepository adminRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public Admin register(Admin admin) {
		// 加密密码
		admin.setAdminPassword(passwordEncoder.encode(admin.getAdminPassword()));
		return adminRepo.save(admin);
	}

	public Admin login(String adminAccount, String rawPassword) throws Exception {
		Optional<Admin> optional = adminRepo.findByAdminAccount(adminAccount);
		if (optional.isPresent() && passwordEncoder.matches(rawPassword, optional.get().getAdminPassword())) {
			return optional.get();
		}
		throw new Exception("Invalid credentials");
	}

	public void resetPassword(String token, String newPassword) throws Exception {
		Optional<Admin> optional = adminRepo.findByResetPasswordToken(token);
		if (optional.isPresent()) {
			Admin foundAdmin = optional.get();
			foundAdmin.setAdminPassword(passwordEncoder.encode(newPassword));
			foundAdmin.setResetPasswordToken(null);
			adminRepo.save(foundAdmin);
		} else {
			throw new Exception("Invalid token");
		}
	}

	public String createResetToken(String email) throws Exception {
		Optional<Admin> optional = adminRepo.findByAdminMail(email);
		if (optional.isEmpty()) {
			throw new Exception("Email not found");
		}
		Admin foundAdmin = optional.get();
		String token = UUID.randomUUID().toString();
		foundAdmin.setResetPasswordToken(token);
		adminRepo.save(foundAdmin);
		return token;
	}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 獲得所有管理員
	 * 
	 * @param adminDTO
	 * @return
	 */
	public Result<PageImpl<AdminDTO>> findAdminAll(AdminDTO adminDTO) {

		Integer pageNumber = adminDTO.getPageNumber();
		String attrOrderBy = adminDTO.getAttrOrderBy();
		Boolean selectedSort = adminDTO.getSelectedSort();
		Pageable pageable = MyPageRequest.of(pageNumber, 10, selectedSort, attrOrderBy);
		Page<Admin> page = adminRepo.findAll(pageable);
		List<AdminDTO> adminDTOs = new ArrayList<>();
		List<Admin> admins = page.getContent();

		for (Admin admin : admins) {
			AdminDTO responseAdminDTO = new AdminDTO();
			BeanUtils.copyProperties(admin, responseAdminDTO);
			adminDTOs.add(responseAdminDTO);
		}

		PageRequest newPageable = PageRequest.of(page.getNumber(), page.getSize(), page.getSort());
		return Result.success(new PageImpl<>(adminDTOs, newPageable, page.getTotalElements()));
	}

	/**
	 * 模糊查詢
	 * 
	 * @param
	 * @return
	 */
	public Result<PageImpl<AdminDTO>> findAdmins(AdminDTO adminDTO) {
		Specification<Admin> spec = Specification.where(AdminSpecification.accountContains(adminDTO.getAdminAccount()))
				.and(AdminSpecification.nameContains(adminDTO.getAdminName()))
				.and(AdminSpecification.mailContains(adminDTO.getAdminMail()))
				.and(AdminSpecification.statusEquals(adminDTO.getAdminStatus()));

		Pageable pageable = MyPageRequest.of(adminDTO.getPageNumber(), 10, adminDTO.getSelectedSort(),
				adminDTO.getAttrOrderBy());

		Page<Admin> page = adminRepo.findAll(spec, pageable);
		List<Admin> admins = page.getContent();
		List<AdminDTO> adminsDTOs = new ArrayList<>();

		for (Admin admin : admins) {
			AdminDTO responseAdminDTO = new AdminDTO();

			BeanUtils.copyProperties(admin, responseAdminDTO);
			adminsDTOs.add(responseAdminDTO);

		}
		PageRequest newPageable = PageRequest.of(page.getNumber(), page.getSize(), page.getSort());
		return Result.success(new PageImpl<>(adminsDTOs, newPageable, page.getTotalElements()));
	}
	
	
	/**
	 * 依名稱獲取景點
	 * 
	 * @param attractionName
	 * @return
	 */
	public Result<List<AdminDTO>> findAdminByName(String adminName) {
		DaoResult<List<Admin>> getAdminsByNameResult = adminRepo.getAdminsByName(adminName);

		if (getAdminsByNameResult.isFailure()) {
			return Result.failure("根據管理員名稱獲取景點失敗");
		}

		List<AdminDTO> list = new ArrayList<>();
		List<Admin> admins = getAdminsByNameResult.getData();

		for (Admin admin : admins) {
			AdminDTO adminDTO = new AdminDTO();
			BeanUtils.copyProperties(admin, adminDTO);
			list.add(adminDTO);
		}

		return Result.success(list);
	}

	/**
	 * 依id獲得管理員
	 * 
	 * @param adminId
	 * @return
	 */
	public Result<AdminDTO> findAdminById(Integer adminId) {
		Optional<Admin> optional = adminRepo.findById(adminId);
		if (optional.isEmpty()) {
			return Result.failure("沒有此管理員");
		}
		Admin admin = optional.get();
		AdminDTO adminDTO = new AdminDTO();
		BeanUtils.copyProperties(admin, adminDTO);
		return Result.success(adminDTO);
	}

	/**
	 *
	 * 
	 * @param 
	 * @return
	 */
	@Transactional
	public Result<String> saveAdmin(AdminDTO adminDTO, MultipartFile imageFile) {
		Result<String> uploadResult = UploadImageFile.upload(imageFile);
		
		if(uploadResult.isSuccess()) {
			String fileName = imageFile.getOriginalFilename();
			adminDTO.setImgFile("uploads" + "/" + fileName);
		}else {
			adminDTO.setImgFile("uploads/default.jpg");
		}
		Admin admin = new Admin();
		
		BeanUtils.copyProperties(adminDTO, admin);
		
		adminRepo.save(admin);

		return Result.success("新增管理員成功");
	}

	/**
	 * 軟刪除
	 * 
	 * @param adminId
	 * @return
	 */
	@Transactional
	public Result<String> softRemoveAdmin(Integer adminId) {
		Optional<Admin> optionalAdmin = adminRepo.findById(adminId);
		if (!optionalAdmin.isPresent()) {
			return Result.failure("管理員狀態更新失敗");
		}
		Admin admin = optionalAdmin.get();
		admin.setAdminStatus(0);
		adminRepo.save(admin);
		return Result.success("管理員狀態更新成功");
	}


	/**
	 * 更新管理員
	 * @param attractionDTO
	 * @return
	 */
	public Result<String> updateAdmin(AdminDTO adminDTO, MultipartFile imageFile) {
		Result<String> uploadResult = UploadImageFile.upload(imageFile);
		
		if(uploadResult.isSuccess()) {
			String fileName = imageFile.getOriginalFilename();
			adminDTO.setImgFile("uploads" + "/" + fileName);
		}else {
			adminDTO.setImgFile("uploads/default.jpg");
		}
		
		Admin updateAdmin = adminRepo.findById(adminDTO.getAdminId()).orElse(null);
		MyModelMapper.map(adminDTO, updateAdmin);
		adminRepo.save(updateAdmin);
		return Result.success("更新管理員成功");
	}
	
	/**
	 * 根據id上傳圖片
	 * @param imageFile
	 * @param attractionId
	 * @return
	 */
	public Result<String> uploadImageById(MultipartFile imageFile, Integer adminId){
		Admin admin = adminRepo.findById(adminId).orElse(null);
		if(admin == null) {
			return Result.failure("沒有此管理員");
		}
		
		Result<String> uploadImageResult = UploadImageFile.upload(imageFile);
		
		if(uploadImageResult.isFailure()) {
			return Result.failure(uploadImageResult.getMessage());
		}
		
		Path path = (Path) uploadImageResult.getExtraData("path");
		admin.setImgFile(path.toString());
		
		return Result.success("上傳圖片成功");
	}
		
	
	public Result<UrlResource> findImageById(Integer adminId) {
	    try {
	        Admin admin = adminRepo.findById(adminId).orElse(null);
	        if (admin == null) {
	            return Result.failure("找不到此管理員");
	        }

	        String imagePath = admin.getImgFile();
	        if (imagePath == null) {
	            imagePath = "uploads/default.jpg";
	        }

	        Path path = Paths.get(imagePath);
	        try {
	            UrlResource resource = new UrlResource(path.toUri());
	            if (resource.exists() || resource.isReadable()) {
	                return Result.success(resource).setExtraData("path", path);
	            }
	        } catch (MalformedURLException e) {
	            return Result.failure("圖片資源無效");
	        }

	        return Result.failure("無法讀取圖片");
	    } catch (Exception e) {
	        return Result.failure("獲取圖片失敗：" + e.getMessage());
	    }
	}

	/////////////////////////////////////////////////////////////
	public Result<AdminDTO> loginAdmin(String adminAccount, String adminPassword) {
		return adminRepo
				.findOne(AdminSpecification.accountContains(adminAccount)
						.and((root, query, cb) -> cb.equal(root.get("adminPassword"), adminPassword)))
				.map(admin -> Result.success(convertToDTO(admin))).orElse(Result.failure("登入失敗，請檢查帳號密碼"));
	}

	public Result<Void> registerAdmin(String adminAccount, String adminPassword) {
		if (adminRepo.existsByAccount(adminAccount) != null) {
			return Result.failure("該帳號已存在");
		}
		Admin admin = new Admin();
		admin.setAdminAccount(adminAccount);
		admin.setAdminPassword(adminPassword);
		// Set other necessary fields
		adminRepo.save(admin);
		return Result.success(null);
	}

	public Result<Boolean> checkAccountExists(String adminAccount) {
		boolean exists = adminRepo.existsByAccount(adminAccount) != null;
		return exists ? Result.failure("此帳號已被註冊") : Result.success(false);
	}

	private AdminDTO convertToDTO(Admin admin) {
		AdminDTO dto = new AdminDTO();
		BeanUtils.copyProperties(admin, dto);
		return dto;
	}

	private Admin convertToEntity(AdminDTO dto) {
		Admin admin = new Admin();
		BeanUtils.copyProperties(dto, admin);
		return admin;
	}

	private void updateAdminFields(Admin admin, AdminDTO dto) {
		if (dto.getAdminAccount() != null)
			admin.setAdminAccount(dto.getAdminAccount());
		if (dto.getAdminName() != null)
			admin.setAdminName(dto.getAdminName());
		if (dto.getAdminMail() != null)
			admin.setAdminMail(dto.getAdminMail());
		if (dto.getHiredate() != null)
			admin.setHiredate(dto.getHiredate());
		if (dto.getAdminStatus() != null)
			admin.setAdminStatus(dto.getAdminStatus());
		// Don't update password here for security reasons
	}
}
