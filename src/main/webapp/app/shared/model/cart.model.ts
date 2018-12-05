import { Moment } from 'moment';

export interface ICart {
    id?: number;
    dateCreated?: Moment;
    dateUpdated?: Moment;
    totalItem?: number;
    productName?: string;
    productPrice?: number;
    productId?: number;
    customerId?: number;
}

export class Cart implements ICart {
    constructor(
        public id?: number,
        public dateCreated?: Moment,
        public dateUpdated?: Moment,
        public totalItem?: number,
        public productName?: string,
        public productPrice?: number,
        public productId?: number,
        public customerId?: number
    ) {}
}
