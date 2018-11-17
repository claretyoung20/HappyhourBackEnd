import { Moment } from 'moment';

export interface ICategory {
    id?: number;
    dateCreated?: Moment;
    dateUpdated?: Moment;
    name?: string;
    restaurantId?: number;
    productTypeId?: number;
}

export class Category implements ICategory {
    constructor(
        public id?: number,
        public dateCreated?: Moment,
        public dateUpdated?: Moment,
        public name?: string,
        public restaurantId?: number,
        public productTypeId?: number
    ) {}
}
