package com.swaarm.adtech.service;

import com.swaarm.adtech.data.StatisticsSearchOutput;
import com.swaarm.adtech.data.model.Category;
import com.swaarm.adtech.util.NativeQueryUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@AllArgsConstructor
public class AdvertisementStatisticsSearchService {

    private final EntityManager entityManager;

    public List<StatisticsSearchOutput> findAllByCategories(OffsetDateTime start, OffsetDateTime end, List<Category> fields) {
        Session hibernateSession = entityManager.unwrap(Session.class);
        String query = generateQuery(start, end, fields);
        NativeQuery<Tuple> nativeQuery = hibernateSession.createNativeQuery(query, Tuple.class);
        Stream<Tuple> resultStream = nativeQuery.getResultStream();
        return NativeQueryUtil.convertTupleResult(resultStream.collect(Collectors.toList()), StatisticsSearchOutput.class);
    }

    private String generateQuery(OffsetDateTime start, OffsetDateTime end, List<Category> fields) {
        return " select " +
                generateFields(fields, QueryType.SELECT) +
                " sum(case when type='DELIVERY' then 1 else 0 end) as deliveries, " +
                " sum(case when type='CLICK' then 1 else 0 end) as clicks, " +
                " sum(case when type='INSTALL' then 1 else 0 end) as installs, " +
                " '" + start + "' as start, " +
                " '" + end + "' as end " +
                " from swaarm.advertisement_record ad " +
                "   left join swaarm.advertisement_record_detail ard on ad.id = ard.advertisement_record_id " +
                " where ard.time between '" + start + "' AND '" + end + "' " +
                generateFields(fields, QueryType.GROUP_BY);
    }

    private String generateFields(List<Category> fields, QueryType type) {
        StringBuilder builder = new StringBuilder();
        if (fields == null) {
            return "";
        }
        if (QueryType.GROUP_BY.equals(type)) {
            builder.append(" group by");
        }
        for (Category field : fields) {
            builder.append(" ad.").append(field.getColumnName());
            if (QueryType.SELECT.equals(type)) {
                builder.append(" as ").append(field.getFieldName());
            }
            builder.append(",");
        }
        if (QueryType.GROUP_BY.equals(type)) {
            return builder.deleteCharAt(builder.length() - 1).toString();
        }
        return builder.toString();
    }

    private enum QueryType {
        SELECT,
        GROUP_BY
    }
}
