interface Privilege {
    moduleId: string;
    featureId: string;
    programId: string;
    programActions: string[];
};

interface DataAccess {
    dataSource: string;
    dataSourceRefIds: string[];
};

export interface GrantedAuthority {
    privileges: Privilege[];
    dataAccesses: DataAccess[];
};