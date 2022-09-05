package com.example.portfolio.repository.customRepository;

import com.example.portfolio.entity.customDto.CustomDtoExample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomDtoExampleRepository extends JpaRepository<CustomDtoExample, String> {
    @Query(value =
            "SELECT m.id as input_id, b.writer as input_writer, b.title as input_title " +
                    "from Member m " +
                    "inner join Board b on m.id = b.writer " +
                    "where m.id = :memberId",
            nativeQuery = true)
    CustomDtoExample findExample(String memberId);
}
