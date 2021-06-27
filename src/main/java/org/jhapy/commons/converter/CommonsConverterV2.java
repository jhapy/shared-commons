package org.jhapy.commons.converter;

import java.util.stream.Collectors;
import org.jhapy.dto.utils.Pageable;
import org.jhapy.dto.utils.Pageable.Order.Direction;
import org.mapstruct.Mapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

/**
 * @author Alexandre Clavaud.
 * @version 1.0
 * @since 18/05/2021
 */
@Mapper(componentModel = "spring")
public abstract class CommonsConverterV2 {

  public PageRequest convert(Pageable src) {
    var s = src.getSort() == null ? Sort.unsorted() : Sort.by(src.getSort().stream().map(
        order -> order.getDirection().equals(Direction.ASC) ? Order.asc(order.getProperty())
            : Order.desc(order.getProperty())).collect(
        Collectors.toList()));

    return PageRequest.of(src.getPage(), src.getSize(), s);
  }

  public Pageable convert(PageRequest src) {
    return Pageable.of(src.getPageNumber(),
        src.getPageSize(),
        src.getSort().stream().map(
            order -> order.isAscending() ? Pageable.Order.asc(order.getProperty())
                : Pageable.Order.desc(order.getProperty())).collect(Collectors.toList()));
  }

  public Pageable convert(org.springframework.data.domain.Pageable domain) {
    var result = new Pageable();
    result.setSize(domain.getPageSize());
    result.setOffset((int) domain.getOffset());
    result.setPage(domain.getPageNumber());
    result.setSort(domain.getSort().stream().map(order -> {
      var o = new Pageable.Order();
      o.setProperty(order.getProperty());
      o.setDirection(order.getDirection().isAscending() ? Direction.ASC : Direction.DESC);
      return o;
    }).collect(Collectors.toSet()));
    return result;
  }
}
