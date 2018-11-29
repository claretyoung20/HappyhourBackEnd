import { Moment } from 'moment';

export interface IDiscount {
    id?: number;
    amount?: number;
    endDate?: Moment;
    startDate?: Moment;
    percentage?: number;
    productId?: number;
}

export class Discount implements IDiscount {
    constructor(
        public id?: number,
        public amount?: number,
        public endDate?: Moment,
        public startDate?: Moment,
        public percentage?: number,
        public productId?: number
    ) {}
}
