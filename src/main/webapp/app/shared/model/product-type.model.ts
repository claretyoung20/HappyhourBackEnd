import { Moment } from 'moment';

export interface IProductType {
    id?: number;
    dateCreated?: Moment;
    dateUpdated?: Moment;
    type?: string;
}

export class ProductType implements IProductType {
    constructor(public id?: number, public dateCreated?: Moment, public dateUpdated?: Moment, public type?: string) {}
}
