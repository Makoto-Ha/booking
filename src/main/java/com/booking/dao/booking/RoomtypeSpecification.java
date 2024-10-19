package com.booking.dao.booking;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.booking.bean.pojo.booking.BookingOrderItem;
import com.booking.bean.pojo.booking.Room;
import com.booking.bean.pojo.booking.Roomtype;
import com.booking.bean.pojo.common.Amenity;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

public class RoomtypeSpecification {
	
	// 根據名字進行模糊查詢
	public static Specification<Roomtype> nameContains(String roomtypeName) {
		return (Root<Roomtype> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
			if(roomtypeName == null || roomtypeName.isEmpty()) {
				return builder.conjunction();
			}
			
			return builder.like(root.get("roomtypeName"), "%" + roomtypeName + "%");
		};
	}
	
	// 根據名字進行查詢
	public static Specification<Roomtype> hasNameContains(String roomtypeName) {
		return (Root<Roomtype> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
			if(roomtypeName == null || roomtypeName.isEmpty()) {
				return builder.conjunction();
			}
			
			return builder.equal(root.get("roomtypeName"), roomtypeName);
		};
	} 
	
	// 根據房間人數進行查詢
	public static Specification<Roomtype> capacityContains (Integer roomtypeCapacity) {
		return (Root<Roomtype> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
			if(roomtypeCapacity == null) {
				return builder.conjunction();
			}
			
			return builder.equal(root.get("roomtypeCapacity"), roomtypeCapacity);
		};
	}
	
	// 根據價錢進行查詢
	public static Specification<Roomtype> priceContains (Integer roomtypePrice) {
		return (Root<Roomtype> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
			if(roomtypePrice == null) {
				return builder.conjunction();
			}
			
			return builder.equal(root.get("roomtypePrice"), roomtypePrice);
		};
	}
	
	// 根據數量進行查詢
	public static Specification<Roomtype> quantityContains (Integer roomtypeQuantity) {
		return (Root<Roomtype> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
			if(roomtypeQuantity == null) {
				return builder.conjunction();
			}
			
			return builder.equal(root.get("roomtypeQuantity"), roomtypeQuantity);
		};
	}
	
	// 根據說明進行模糊查詢
	public static Specification<Roomtype> descriptionContains (String roomtypeDescription) {
		return (Root<Roomtype> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
			if(roomtypeDescription == null || roomtypeDescription.isEmpty()) {
				return builder.conjunction();
			}
			
			return builder.like(root.get("roomtypeDescription"), "%" + roomtypeDescription + "%");
		};
	}
	
	// 根據地址進行模糊查詢
	public static Specification<Roomtype> addressContains (String roomtypeAddress) {
		return (Root<Roomtype> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
			if(roomtypeAddress == null || roomtypeAddress.isEmpty()) {
				return builder.conjunction();
			}
			
			return builder.like(root.get("roomtypeAddress"), "%" + roomtypeAddress + "%");
		};
	}
	
	// 根據城市進行查詢
	public static Specification<Roomtype> cityContains (String roomtypeCity) {
		return (Root<Roomtype> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
			if(roomtypeCity == null || roomtypeCity.isEmpty()) {
				return builder.conjunction();
			}
			
			return builder.equal(root.get("roomtypeCity"), roomtypeCity);
		};
	}
	
	// 根據城市進行模糊查詢
	public static Specification<Roomtype> likeCityContains (String roomtypeCity) {
		return (Root<Roomtype> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
			if(roomtypeCity == null || roomtypeCity.isEmpty()) {
				return builder.conjunction();
			}
			
			return builder.like(root.get("roomtypeCity"), '%' + roomtypeCity + '%');
		};
	}
	
	// 根據區域進行模糊查詢
	public static Specification<Roomtype> likeDistrictContains (String roomtypeDistrict) {
		return (Root<Roomtype> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
			if(roomtypeDistrict == null || roomtypeDistrict.isEmpty()) {
				return builder.conjunction();
			}
			
			return builder.like(root.get("roomtypeDistrict"), '%' + roomtypeDistrict + '%');
		};
	}
	
	// 根據區域進行查詢
	public static Specification<Roomtype> districtContains (String roomtypeDistrict) {
		return (Root<Roomtype> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
			if(roomtypeDistrict == null || roomtypeDistrict.isEmpty()) {
				return builder.conjunction();
			}
			
			return builder.equal(root.get("roomtypeDistrict"), roomtypeDistrict);
		};
	}
	
	// 根據價錢區間進行查詢
	public static Specification<Roomtype> moneyContains (Integer minMoney, Integer maxMoney) {
		return (Root<Roomtype> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
			if(minMoney == 0 && maxMoney == 0) {
				return builder.conjunction();
			}
			
			if (minMoney != null) {
				if (maxMoney != null) {
					return builder.between(root.get("roomtypePrice"), minMoney, maxMoney);
				} else {
					return builder.ge(root.get("roomtypePrice"), minMoney);
				}
			}else {
				if (maxMoney != null) {
					return builder.le(root.get("roomtypePrice"), maxMoney);
				}
			}
			
			return builder.conjunction();
		};
	}
	
	// 根據日期區間做查詢
	public static Specification<Roomtype> availableRoomTypes(LocalDate checkInDate, LocalDate checkOutDate) {
        return (root, query, criteriaBuilder) -> {
	
            // 連結Amenity表格
            root.join("amenities", JoinType.LEFT);

          	query.distinct(true);
        
            // 創建Room子查詢
            Subquery<Room> roomSubquery = query.subquery(Room.class);
            Root<Room> roomRoot = roomSubquery.from(Room.class);
            roomSubquery.select(roomRoot)
                    .where(
                            criteriaBuilder.equal(roomRoot.get("roomtype"), root),
                            criteriaBuilder.not(
                                    criteriaBuilder.exists(
                                            createBookingOrderItemSubquery(roomSubquery, roomRoot, checkInDate, checkOutDate, criteriaBuilder)
                                    )
                            )
                    );

            return criteriaBuilder.exists(roomSubquery);
        };
    }

    private static Subquery<BookingOrderItem> createBookingOrderItemSubquery(Subquery<Room> roomSubquery,
                                                                             Root<Room> roomRoot,
                                                                             LocalDate checkInDate,
                                                                             LocalDate checkOutDate,
                                                                             CriteriaBuilder criteriaBuilder) {
        // 創建BookingOrderItem的子查詢
        Subquery<BookingOrderItem> bookingOrderItemSubquery = roomSubquery.subquery(BookingOrderItem.class);
        Root<BookingOrderItem> bookingOrderItemRoot = bookingOrderItemSubquery.from(BookingOrderItem.class);
        bookingOrderItemSubquery.select(bookingOrderItemRoot)
                .where(
                        criteriaBuilder.equal(bookingOrderItemRoot.get("room"), roomRoot),
                        criteriaBuilder.and(
                                criteriaBuilder.lessThanOrEqualTo(bookingOrderItemRoot.get("checkOutDate"), checkInDate),
                                criteriaBuilder.greaterThanOrEqualTo(bookingOrderItemRoot.get("checkInDate"), checkOutDate)
                        )
                );

        return bookingOrderItemSubquery;
    }

    // 根據點擊的服務做查詢
	public static Specification<Roomtype> hasAmenities(List<Amenity> amenities) {
		return (Root<Roomtype> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (amenities == null || amenities.isEmpty()) {
                // 是空的amenities，返回where 1=1
                return builder.conjunction();
            }

            // Join Roomtype 和 Amenity 表
            Join<Roomtype, Amenity> amenityJoin = root.join("amenities");

            // 獲取所有amenityId
            List<Integer> amenityIds = new ArrayList<>();
            for (Amenity amenity : amenities) {
                amenityIds.add(amenity.getAmenityId());
            }

            // 創建 WHERE ra.amenity_id IN (1, 2, 3, ...)
            Predicate amenityInPredicate = amenityJoin.get("amenityId").in(amenityIds);
            
            // 因為GROUP BY之後不能使用*，所以必須每個欄位都寫上
            query.multiselect(root.get("roomtypeId")
            		, root.get("roomtypeName")
            		, root.get("roomtypeCapacity")
            		, root.get("roomtypePrice")
            		, root.get("roomtypeQuantity")
            		, root.get("roomtypeDescription")
            		, root.get("roomtypeAddress")
            		, root.get("roomtypeCity")
            		, root.get("roomtypeDistrict")
            		, root.get("updatedTime")
            		, root.get("createdTime")
            		, root.get("imagePath"));

            // 使用 HAVING 和 COUNT(DISTINCT ra.amenity_id)
            query.groupBy(root.get("roomtypeId")
            		, root.get("roomtypeName")
            		, root.get("roomtypeCapacity")
            		, root.get("roomtypePrice")
            		, root.get("roomtypeQuantity")
            		, root.get("roomtypeDescription")
            		, root.get("roomtypeAddress")
            		, root.get("roomtypeCity")
            		, root.get("roomtypeDistrict")
            		, root.get("updatedTime")
            		, root.get("createdTime")
            		, root.get("imagePath")); 
            
            query.having(builder.equal(builder.countDistinct(amenityJoin.get("amenityId")), amenityIds.size()));

            return amenityInPredicate;
        };
	}
	
}