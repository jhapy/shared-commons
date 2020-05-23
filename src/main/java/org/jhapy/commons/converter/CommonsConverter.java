package org.jhapy.commons.converter;

import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Component;
import org.jhapy.commons.utils.OrikaBeanMapper;
import org.jhapy.dto.utils.Page;

/**
 * @author jHapy Lead Dev.
 * @version 1.0
 * @since 2019-06-05
 */
@Component
public class CommonsConverter {

  private final OrikaBeanMapper orikaBeanMapper;

  public CommonsConverter(OrikaBeanMapper orikaBeanMapper) {
    this.orikaBeanMapper = orikaBeanMapper;
  }

  @Bean
  public void commonsConverters() {
    orikaBeanMapper.addMapper(Pageable.class, org.jhapy.dto.utils.Pageable.class);
    orikaBeanMapper.addMapper(org.jhapy.dto.utils.Pageable.class, Pageable.class);

    orikaBeanMapper.addMapper(Point.class, org.jhapy.dto.utils.Point.class);
    orikaBeanMapper.addMapper(org.jhapy.dto.utils.Point.class, Point.class);

    orikaBeanMapper.addMapper(PageImpl.class, Page.class);
    orikaBeanMapper.addMapper(Page.class, PageImpl.class);
  }
}