import { Moment } from 'moment';

export interface ISaleOrder {
    id?: number;
    basePrice?: number;
    discountAmount?: number;
    originalPrice?: number;
    dateCreated?: Moment;
    dateUpdated?: Moment;
    productId?: number;
    happyOrderId?: number;
}

export class SaleOrder implements ISaleOrder {
    constructor(
        public id?: number,
        public basePrice?: number,
        public discountAmount?: number,
        public originalPrice?: number,
        public dateCreated?: Moment,
        public dateUpdated?: Moment,
        public productId?: number,
        public happyOrderId?: number
    ) {}
}
