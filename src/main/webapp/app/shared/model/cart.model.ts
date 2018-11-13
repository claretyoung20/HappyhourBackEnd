import { Moment } from 'moment';

export interface ICart {
    id?: number;
    dateCreated?: Moment;
    dateUpdated?: Moment;
    totalItem?: number;
    productId?: number;
}

export class Cart implements ICart {
    constructor(
        public id?: number,
        public dateCreated?: Moment,
        public dateUpdated?: Moment,
        public totalItem?: number,
        public productId?: number
    ) {}
}
