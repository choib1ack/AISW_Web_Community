package com.aisw.community.repository.admin;

import com.aisw.community.model.entity.admin.Faq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface FaqRepository extends JpaRepository<Faq, Long> {
}