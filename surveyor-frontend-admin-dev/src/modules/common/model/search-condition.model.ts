export interface Paging {
    page: number;
    size: number;
};

export enum SortDirection {
    ASC = "ASC", DESC = "DESC"
};

export interface Sort {
    property: string;
    direction: SortDirection;
    active: boolean;
};

export interface SearchCondition <T> {
    criteria?: T;
    paging?: Paging;
    sortOrders?: Sort[];
};