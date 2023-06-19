package htms.service.impl;

import htms.api.domain.FilterCondition;
import htms.common.constants.FilterOperation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class FilterBuilderService {
    private static final int DEFAULT_PAGE_SIZE = 10;

    private static List<String> split(String search, String delimiter) {
        return Stream.of(search.split(delimiter))
                .toList();
    }

    /**
     * Get request pageable based on requested size, page, order
     *
     * @param size  The page size
     * @param page  The requested page
     * @param order The requested order filter (eg. field|ASC)
     * @return {@link PageRequest}
     */
    public PageRequest getPageable(int size, int page, String order) {
        int pageSize = (size <= 0) ? DEFAULT_PAGE_SIZE : size;
        int currentPage = (page <= 0) ? 1 : page;

        try {
            if (order != null && !order.isEmpty()) {
                final String FILTER_CONDITION_DELIMITER = "\\|";
                List<String> values = split(order, FILTER_CONDITION_DELIMITER);
                String column = values.get(0);
                String sortDirection = values.get(1);

                if (sortDirection.equalsIgnoreCase("asc")) {
                    return PageRequest.of(currentPage - 1, pageSize, Sort.by(Sort.Direction.ASC, column));
                } else if (sortDirection.equalsIgnoreCase("desc")) {
                    return PageRequest.of(currentPage - 1, pageSize, Sort.by(Sort.Direction.DESC, column));
                } else {
                    throw new IllegalArgumentException(String.format("Value for param 'order' is not valid : %s , must be 'asc' or 'desc'", sortDirection));
                }
            } else {
                // Not order, using default order
                return PageRequest.of(currentPage - 1, pageSize);
            }
        } catch (Exception exception) {
            // todo: handle exception
            return null;
            //throw new BadRequestException("Cannot create condition filter " + ex.getMessage())
        }
    }


    public List<FilterCondition> createFilterCondition(String criteria) {
        List<FilterCondition> filters = new ArrayList<>();
        try {

            if (criteria != null && !criteria.isEmpty()) {

                final String FILTER_SEARCH_DELIMITER = "&";
                final String FILTER_CONDITION_DELIMITER = "\\|";

                List<String> values = split(criteria, FILTER_SEARCH_DELIMITER);
                if (!values.isEmpty()) {
                    values.forEach(x -> {
                        List<String> filter = split(x, FILTER_CONDITION_DELIMITER);
                        if (FilterOperation.fromValue(filter.get(1)) != null) {
                            filters.add(new FilterCondition(filter.get(0), FilterOperation.fromValue(filter.get(1)), filter.get(2)));
                        }
                    });
                }
            }

            return filters;

        } catch (Exception ex) {
            // todo: handle exception
            return null;
            //throw new BadRequestException("Cannot create condition filter " + ex.getMessage())
        }

    }
}
