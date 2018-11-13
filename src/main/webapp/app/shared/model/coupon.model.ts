import { Moment } from 'moment';

export interface ICoupon {
    id?: number;
    code?: string;
    dateCreated?: Moment;
    dateUpdated?: Moment;
    endDate?: Moment;
    isActive?: boolean;
    noPerUser?: number;
    price?: number;
    startFromDate?: Moment;
    restaurantId?: number;
}

export class Coupon implements ICoupon {
    constructor(
        public id?: number,
        public code?: string,
        public dateCreated?: Moment,
        public dateUpdated?: Moment,
        public endDate?: Moment,
        public isActive?: boolean,
        public noPerUser?: number,
        public price?: number,
        public startFromDate?: Moment,
        public restaurantId?: number
    ) {
        this.isActive = this.isActive || false;
    }
}
