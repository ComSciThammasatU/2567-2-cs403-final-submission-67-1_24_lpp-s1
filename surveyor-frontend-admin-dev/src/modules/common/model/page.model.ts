export interface Page <T> {
    pageNo: number;
    pageSize: number;
    totalPages: number;
    totalElements: number;
    first: boolean;
    last: boolean;
    empty: boolean;
    dataLength: number;
    datas: T
};