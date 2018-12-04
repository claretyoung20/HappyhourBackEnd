import { Moment } from 'moment';

export interface IHappyOrder {
    id?: number;
    baseTotal?: number;
    totalPrice?: number;
    dateUpdated?: Moment;
    dateCreated?: Moment;
    orderStatusId?: number;
    customerId?: number;
    couponId?: number;
    restaurantId?: number;
    staffStaffCode?: string;
    staffId?: number;
}

export class HappyOrder implements IHappyOrder {
    constructor(
        public id?: number,
        public baseTotal?: number,
        public totalPrice?: number,
        public dateUpdated?: Moment,
        public dateCreated?: Moment,
        public orderStatusId?: number,
        public customerId?: number,
        public couponId?: number,
        public restaurantId?: number,
        public staffStaffCode?: string,
        public staffId?: number
    ) {}
}
