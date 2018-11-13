import { Moment } from 'moment';

export interface ISaleOrder {
    id?: number;
    basePrice?: number;
    dateCreated?: Moment;
    dateUpdated?: Moment;
    discountAmount?: number;
    originalPrice?: number;
    productId?: number;
    happyOrderId?: number;
}

export class SaleOrder implements ISaleOrder {
    constructor(
        public id?: number,
        public basePrice?: number,
        public dateCreated?: Moment,
        public dateUpdated?: Moment,
        public discountAmount?: number,
        public originalPrice?: number,
        public productId?: number,
        public happyOrderId?: number
    ) {}
}
