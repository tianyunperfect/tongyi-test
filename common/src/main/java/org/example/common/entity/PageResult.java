package org.example.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 分页模板
 *
 * @author tianyunperfect
 * @date 2020/05/20
 */
@Data
@AllArgsConstructor
public class PageResult<T> {
    /**
     * 第几页
     *
     * @mock 1
     */
    private Integer currentPage;
    /**
     * 每页数量
     *
     * @mock 10
     */
    private Integer pageSize;
    /**
     * 总数
     *
     * @mock @integer(9,99)
     */
    private Long total;
    private List<T> list;
}
