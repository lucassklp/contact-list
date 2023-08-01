export type Page<T> = {
    content: T[]
    pageable: Pageable,
    totalPages: number,
    totalElements: number,
    last: boolean,
    size: number,
    number: number,
    sort: Sort,
    numberOfElements: number,
    first: boolean,
    empty: boolean
}

type Pageable = {
    sort: Sort,
    offset: number,
    pageNumber: number,
    pageSize: number,
    paged: boolean,
    unpaged: boolean
}

type Sort = {
    empty: boolean,
    unsorted: boolean,
    sorted: boolean
}