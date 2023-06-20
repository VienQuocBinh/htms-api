package htms.api.response;

import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse<T> {
    private int totalPages;
    private long totalItems;
    private int currentPage;
    private boolean isFirstPage;
    private boolean isLastPage;
    private int itemsCurrentPage;
    private int pageSize;

    private List<T> items;

    public void setPageStats(Page<?> pg, List<T> pageItems) {
        isFirstPage = pg.isFirst();
        isLastPage = pg.isLast();
        currentPage = pg.getNumber() + 1;
        pageSize = pg.getSize();
        totalPages = pg.getTotalPages();
        totalItems = pg.getTotalElements();
        itemsCurrentPage = pg.getNumberOfElements();
        items = pageItems;
    }
}
