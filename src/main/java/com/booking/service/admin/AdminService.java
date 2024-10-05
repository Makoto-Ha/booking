package com.booking.service.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.booking.bean.dto.admin.AdminDTO;
import com.booking.bean.dto.attraction.AttractionDTO;
import com.booking.bean.pojo.admin.Admin;
import com.booking.bean.pojo.attraction.Attraction;
import com.booking.dao.admin.AdminRepository;
import com.booking.dao.admin.AdminSpecification;
import com.booking.dao.attraction.AttractionSpecification;
import com.booking.utils.DaoResult;
import com.booking.utils.MyPageRequest;
import com.booking.utils.Result;

@Service
@Transactional
public class AdminService {

	@Autowired
	private AdminRepository adminRepo;

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
				.and(AdminSpecification.hiredateEquals(adminDTO.getHiredate()))
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
	 * 新增管理員
	 * 
	 * @param
	 * @return
	 */
	@Transactional
	public Result<String> saveAdmin(Admin admin) {
		adminRepo.save(admin);

		return Result.success("新增管理員成功");
	}

	/**
	 * 軟刪除
	 * 
	 * @param adminId
	 * @return
	 */
	public Result<Integer> softRemoveAdmin(Integer adminId) {
		Optional<Admin> optionalAdmin = adminRepo.findById(adminId);
		if (!optionalAdmin.isPresent()) {
			return Result.failure("軟刪除管理員失敗，找不到該管理員");
		}
		Admin admin = optionalAdmin.get();
		admin.setAdminStatus(0);
		adminRepo.save(admin);
		return Result.success(adminId);
	}

	/**
	 * 更新
	 * 
	 * @param admin
	 * @return
	 */
	public Result<String> updateAdmin(Admin admin) {
		DaoResult<Admin> daoResult = adminRepo.getAdminById(admin.getAdminId());

		if (daoResult.isFailure() || daoResult.getData() == null) {
			return Result.failure("更新失敗，找不到該管理員");
		}

		Admin oldAdmin = daoResult.getData();

		if (admin.getAdminAccount() == null || admin.getAdminAccount().isEmpty()) {
			admin.setAdminAccount(oldAdmin.getAdminAccount());
		}

		if (admin.getAdminPassword() == null || admin.getAdminPassword().isEmpty()) {
			admin.setAdminPassword(oldAdmin.getAdminPassword());
		}

		if (admin.getAdminName() == null || admin.getAdminName().isEmpty()) {
			admin.setAdminName(oldAdmin.getAdminName());
		}

		if (admin.getAdminMail() == null || admin.getAdminMail().isEmpty()) {
			admin.setAdminMail(oldAdmin.getAdminMail());
		}

		if (admin.getHiredate() == null) {
			admin.setHiredate(oldAdmin.getHiredate());
		}

		if (admin.getAdminStatus() == null) {
			admin.setAdminStatus(oldAdmin.getAdminStatus());
		}

		DaoResult<?> updateAdminResult = adminRepo.updateAdmin(admin);
		if (updateAdminResult.isFailure()) {
			return Result.failure("更新失敗");
		}
		return Result.success("更新成功");
	}

	//////////////////////////////////////////////////////
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