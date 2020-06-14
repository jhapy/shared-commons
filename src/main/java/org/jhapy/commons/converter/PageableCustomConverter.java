/*
 * Copyright 2020-2020 the original author or authors from the JHapy project.
 *
 * This file is part of the JHapy project, see https://www.jhapy.org/ for more information.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jhapy.commons.converter;

import java.util.stream.Collectors;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;
import org.jhapy.dto.utils.Pageable;
import org.jhapy.dto.utils.Pageable.Order.Direction;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Component;

/**
 * @author jHapy Lead Dev.
 * @version 1.0
 * @since 2019-06-05
 */
@Component
public class PageableCustomConverter extends
    BidirectionalConverter<Pageable, org.springframework.data.domain.Pageable> {

  @Override
  public org.springframework.data.domain.Pageable convertTo(Pageable source,
      Type<org.springframework.data.domain.Pageable> destinationType, MappingContext context) {
    Sort s;
    if (source.getSort() != null) {
      s = Sort.by(source.getSort().stream().map(
          order -> order.getDirection().equals(Direction.ASC) ? Order.asc(order.getProperty())
              : Order.desc(order.getProperty())).collect(
          Collectors.toList()));
    } else {
      s = Sort.unsorted();
    }
    return PageRequest.of(source.getPage(), source.getSize(), s);
  }

  @Override
  public Pageable convertFrom(org.springframework.data.domain.Pageable source,
      Type<Pageable> destinationType, MappingContext context) {
    Sort sort = source.getSort();
    if (sort == null) {
      sort = Sort.unsorted();
    }
    return Pageable.of(source.getPageNumber(),
        source.getPageSize(),
        sort.stream().map(
            order -> order.isAscending() ? Pageable.Order.asc(order.getProperty())
                : Pageable.Order.desc(order.getProperty())).collect(Collectors.toList()));
  }
}
