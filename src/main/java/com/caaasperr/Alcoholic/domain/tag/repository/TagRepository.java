package com.caaasperr.Alcoholic.domain.tag.repository;

import com.caaasperr.Alcoholic.domain.tag.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
