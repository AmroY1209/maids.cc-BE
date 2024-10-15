package com.maids.cc.backend.library.repositories;

import com.maids.cc.backend.library.entities.Base;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseRepository<T extends Base> extends JpaRepository<T, Long> {
}
