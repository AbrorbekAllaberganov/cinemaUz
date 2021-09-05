package com.example.cinemauz.repository;


import com.example.cinemauz.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

    Attachment findByHashId(String hashId);

    @Query(value = "select * from file f where f.hash_id=?1", nativeQuery = true)
    Attachment getFileByHashId(String hashId);

    @Query(value = "select a.hash_id from attachment a where a.product_id=?1 limit 1", nativeQuery = true)
    String getByProductId(Long productId);

}
