package org.jhapy.commons.converter;

import java.util.stream.Collectors;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Component;
import org.jhapy.dto.utils.Pageable;
import org.jhapy.dto.utils.Pageable.Order.Direction;

/**
 * @author jHapy Lead Dev.
 * @version 1.0
 * @since 2019-06-05
 */
@Component
public class PageRequestCustomConverter extends
    BidirectionalConverter<Pageable, PageRequest> {

  @Override
  public PageRequest convertTo(Pageable source,
      Type<PageRequest> destinationType, MappingContext context) {
    Sort s = Sort.by(source.getSort().stream().map(
        order -> order.getDirection().equals(Direction.ASC) ? Order.asc(order.getProperty())
            : Order.desc(order.getProperty())).collect(
        Collectors.toList()));

    return PageRequest.of(source.getPage(), source.getSize(), s);
  }

  @Override
  public Pageable convertFrom(PageRequest source,
      Type<Pageable> destinationType, MappingContext context) {
    return Pageable.of(source.getPageNumber(),
        source.getPageSize(),
        source.getSort().stream().map(
            order -> order.isAscending() ? Pageable.Order.asc(order.getProperty())
                : Pageable.Order.desc(order.getProperty())).collect(Collectors.toList()));
  }
}
