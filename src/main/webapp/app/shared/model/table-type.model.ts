export interface ITableType {
    id?: number;
    code?: string;
    description?: string;
}

export class TableType implements ITableType {
    constructor(public id?: number, public code?: string, public description?: string) {}
}
