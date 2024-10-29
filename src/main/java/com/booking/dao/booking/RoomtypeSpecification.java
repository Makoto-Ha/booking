package com.booking.dao.booking;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.Specification;

import com.booking.bean.pojo.booking.BookingOrderItem;
import com.booking.bean.pojo.booking.Room;
import com.booking.bean.pojo.booking.Roomtype;
import com.booking.bean.pojo.common.Amenity;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
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
	
	// 根據分數進行查詢
	public static Specification<Roomtype> scoreContains (Double score) {
		return (Root<Roomtype> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {

			if(score == null || score == 0) {
				return builder.conjunction();
			}
			
			return builder.equal(root.get("score"), score);
		};
	}
	
	// 根據多個分數進行查詢
	public static Specification<Roomtype> hasScores (List<Double> scores) {
		return (Root<Roomtype> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {

			System.out.println(scores);
			
			if(scores == null || scores.isEmpty()) {
				return builder.conjunction();
			}
			
			List<Predicate> predicates = new ArrayList<>();
			
			for(Double score : scores) {
				predicates.add(builder.equal(root.get("score"), score));
			}
			
			return builder.or(predicates.toArray(new Predicate[0]));
		};
	}
	
	// 根據平方面積進行查詢
	public static Specification<Roomtype> areaContains (Double area) {
		return (Root<Roomtype> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
			if(area == null) {
				return builder.conjunction();
			}
			
			return builder.equal(root.get("area"), area);
		};
	}
	
	// 根據價錢區間進行查詢
	public static Specification<Roomtype> moneyContains (Integer minMoney, Integer maxMoney) {
		return (Root<Roomtype> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
			if((minMoney == null && maxMoney == null) || (minMoney == 0 && maxMoney == 0)) {
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
	

	// 根據日期區間，區間內有一天房型的房間能預定，就返回房型，日期區間被訂滿了，就不返回房型
//	public static Specification<Roomtype> availableRoomTypes(LocalDate checkInDate, LocalDate checkOutDate) {
//	    return (root, query, criteriaBuilder) -> {
//	        boolean isSingleDay = checkInDate.equals(checkOutDate);
//	        
//	        // 獲取房型的所有房間數量
//	        Subquery<Long> totalRoomsSubquery = query.subquery(Long.class);
//	        Root<Room> totalRoomsRoot = totalRoomsSubquery.from(Room.class);
//	        totalRoomsSubquery.select(criteriaBuilder.count(totalRoomsRoot))
//	            .where(criteriaBuilder.equal(totalRoomsRoot.get("roomtype"), root));
//	        
//	        if (isSingleDay) {
//	            // 單日查詢：計算指定日期已訂房間數
//	            Subquery<Long> bookedRoomsSubquery = query.subquery(Long.class);
//	            Root<BookingOrderItem> bookingRoot = bookedRoomsSubquery.from(BookingOrderItem.class);
//	            Join<BookingOrderItem, Room> roomJoin = bookingRoot.join("room");
//	            
//	            bookedRoomsSubquery.select(criteriaBuilder.countDistinct(roomJoin))
//	                .where(
//	                    criteriaBuilder.equal(roomJoin.get("roomtype"), root),
//	                    criteriaBuilder.lessThanOrEqualTo(bookingRoot.get("checkInDate"), checkInDate),
//	                    criteriaBuilder.greaterThanOrEqualTo(bookingRoot.get("checkOutDate"), checkInDate)
//	                );
//	            
//	            // 確保有可用房間
//	            return criteriaBuilder.lessThan(bookedRoomsSubquery, totalRoomsSubquery);
//	            
//	        } else {
//	            // 區間查詢：檢查每一天是否都被訂滿
//	            List<LocalDate> dateRange = checkInDate.datesUntil(checkOutDate.plusDays(1))
//	                                                 .collect(Collectors.toList());
//	            
//	            // 為每一天創建子查詢
//	            List<Predicate> dayPredicates = new ArrayList<>();
//	            
//	            for (LocalDate date : dateRange) {
//	                // 檢查特定日期的已訂房間數
//	                Subquery<Long> dayBookedRoomsSubquery = query.subquery(Long.class);
//	                Root<BookingOrderItem> bookingRoot = dayBookedRoomsSubquery.from(BookingOrderItem.class);
//	                Join<BookingOrderItem, Room> roomJoin = bookingRoot.join("room");
//	                
//	                dayBookedRoomsSubquery.select(criteriaBuilder.countDistinct(roomJoin))
//	                    .where(
//	                        criteriaBuilder.equal(roomJoin.get("roomtype"), root),
//	                        criteriaBuilder.lessThanOrEqualTo(bookingRoot.get("checkInDate"), date),
//	                        criteriaBuilder.greaterThanOrEqualTo(bookingRoot.get("checkOutDate"), date)
//	                    );
//	                
//	                // 創建該天的房間可用性判斷
//	                dayPredicates.add(criteriaBuilder.lessThan(dayBookedRoomsSubquery, totalRoomsSubquery));
//	            }
//	            
//	            // 如果任何一天有空房，就返回該房型
//	            return criteriaBuilder.or(dayPredicates.toArray(new Predicate[0]));
//	        }
//	    };
//	}
	
	// 根據日期區間，區間內有一天房間能預定，就返回房型，反之有一天被預訂滿，就不返回房型
	public static Specification<Roomtype> availableRoomTypes(LocalDate checkInDate, LocalDate checkOutDate) {
	    return (root, query, criteriaBuilder) -> {
	        boolean isSingleDay = checkInDate.equals(checkOutDate);


	        // 獲取房型的所有房間數量
	        Subquery<Long> totalRoomsSubquery = query.subquery(Long.class);
	        Root<Room> totalRoomsRoot = totalRoomsSubquery.from(Room.class);
	        totalRoomsSubquery.select(criteriaBuilder.count(totalRoomsRoot))
	            .where(criteriaBuilder.equal(totalRoomsRoot.get("roomtype"), root));

	        if (isSingleDay) {
	            // 單日查詢：計算指定日期已訂房間數
	            Subquery<Long> bookedRoomsSubquery = query.subquery(Long.class);
	            Root<BookingOrderItem> bookingRoot = bookedRoomsSubquery.from(BookingOrderItem.class);
	            Join<BookingOrderItem, Room> roomJoin = bookingRoot.join("room");

	            bookedRoomsSubquery.select(criteriaBuilder.countDistinct(roomJoin))
	                .where(
	                    criteriaBuilder.equal(roomJoin.get("roomtype"), root),
	                    criteriaBuilder.lessThanOrEqualTo(bookingRoot.get("checkInDate"), checkInDate),
	                    criteriaBuilder.greaterThanOrEqualTo(bookingRoot.get("checkOutDate"), checkInDate)
	                );


	            // 如果某一天所有房間都已預定，則不返回該房型
	            return criteriaBuilder.lessThan(bookedRoomsSubquery, totalRoomsSubquery);

	        } else {
	            // 區間查詢：檢查每一天是否所有房間都被預定滿
	            List<LocalDate> dateRange = checkInDate.datesUntil(checkOutDate.plusDays(1))
	                                                 .collect(Collectors.toList());

	            // 為每一天創建子查詢
	            List<Predicate> dayPredicates = new ArrayList<>();

	            for (LocalDate date : dateRange) {
	                // 檢查特定日期的已訂房間數
	                Subquery<Long> dayBookedRoomsSubquery = query.subquery(Long.class);
	                Root<BookingOrderItem> bookingRoot = dayBookedRoomsSubquery.from(BookingOrderItem.class);
	                Join<BookingOrderItem, Room> roomJoin = bookingRoot.join("room");

	                dayBookedRoomsSubquery.select(criteriaBuilder.countDistinct(roomJoin))
	                    .where(
	                        criteriaBuilder.equal(roomJoin.get("roomtype"), root),
	                        criteriaBuilder.lessThanOrEqualTo(bookingRoot.get("checkInDate"), date),
	                        criteriaBuilder.greaterThanOrEqualTo(bookingRoot.get("checkOutDate"), date)
	                    );

	                // 如果某一天所有房間都已預定，則排除該房型
	                Predicate dayFull = criteriaBuilder.greaterThanOrEqualTo(dayBookedRoomsSubquery, totalRoomsSubquery);
	                dayPredicates.add(dayFull);
	            }

	            // 如果任何一天所有房間都被訂滿，則排除該房型
	            return criteriaBuilder.not(criteriaBuilder.or(dayPredicates.toArray(new Predicate[0])));
	        }
	    };
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
            query.multiselect(root);

            // 使用 HAVING 和 COUNT(DISTINCT ra.amenity_id)
            query.groupBy(root); 
            
            query.having(builder.equal(builder.countDistinct(amenityJoin.get("amenityId")), amenityIds.size()));

            return amenityInPredicate;
        };
	}

	// 排序房型
	public static Specification<Roomtype> orderBy(String attrOrderBy, Boolean selectedSort) {
		return (Root<Roomtype> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
			if(attrOrderBy == null || attrOrderBy.isEmpty()) {
				return builder.conjunction();
			}
			
			if(attrOrderBy.equals("amenities")) {
				Join<Roomtype, Amenity> amenityJoin = root.join("amenities");
				Expression<Long> amenitiesCount = builder.count(amenityJoin);
				
				query.multiselect(root, amenitiesCount);
				query.groupBy(root);
				
				query.orderBy(builder.desc(amenitiesCount));
				return builder.conjunction();
			}
			
			if(selectedSort) {
				query.orderBy(builder.desc(root.get(attrOrderBy)));
			}else {
				query.orderBy(builder.asc(root.get(attrOrderBy)));
			}
			
			return builder.conjunction();
		};
	}

	// 根據查找房型人數
	public static Specification<Roomtype> hasPeopleNumber(Integer peopleNumber) {
		return (Root<Roomtype> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
			if(peopleNumber == null) {
				return builder.conjunction();
			}
			
			return builder.equal(root.get("roomtypeCapacity"), peopleNumber);
		};
	}
	
}