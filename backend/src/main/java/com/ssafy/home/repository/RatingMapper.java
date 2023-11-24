package com.ssafy.home.repository;

import com.ssafy.home.dto.RatingDto;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;

@Mapper
public interface RatingMapper {
    boolean isEmpty(long aptCode) throws SQLException;

    void saveRating(RatingDto ratingDto) throws SQLException;

    void addRating(RatingDto ratingDto) throws SQLException;

    void updateRating(RatingDto ratingDto) throws SQLException;

    void deleteRating(RatingDto ratingDto) throws SQLException;
}
