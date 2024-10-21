package com.booking.dao.attraction;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.booking.bean.pojo.attraction.Attraction;

public interface AttractionRepository extends JpaRepository<Attraction, Integer>, JpaSpecificationExecutor<Attraction>, AttractionDao {

    /**
     * 根據條件查詢景點
     */
    @Query("SELECT a FROM Attraction a WHERE " +
           "(:name IS NULL OR LOWER(a.attractionName) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
           "(:type IS NULL OR :type = '' OR a.attractionType = :type) AND " +
           "(:city IS NULL OR :city = '' OR a.attractionCity = :city) " +
           "ORDER BY a.attractionId")
    List<Attraction> findAttractions(@Param("name") String name,
                                     @Param("type") String type,
                                     @Param("city") String city,
                                     Pageable pageable);

    /**
     * 使用偏移量和限制查詢景點的默認方法
     */
    default List<Attraction> findAttractions(String name, String type, String city, int offset, int limit) {
        return findAttractions(name, type, city, PageRequest.of(offset / limit, limit));
    }

    /**
     * 查詢所有不重複的城市
     */
    @Query("SELECT DISTINCT a.attractionCity FROM Attraction a ORDER BY a.attractionCity")
    List<String> findDistinctAttractionCities();
}
