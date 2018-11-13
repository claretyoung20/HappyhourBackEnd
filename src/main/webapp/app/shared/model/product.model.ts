import { Moment } from 'moment';

export interface IProduct {
    id?: number;
    createdDate?: Moment;
    description?: string;
    name?: string;
    price?: number;
    updatedDate?: Moment;
    isAvailable?: boolean;
    restaurantId?: number;
    categoryId?: number;
    productTypeId?: number;
}

export class Product implements IProduct {
    constructor(
        public id?: number,
        public createdDate?: Moment,
        public description?: string,
        public name?: string,
        public price?: number,
        public updatedDate?: Moment,
        public isAvailable?: boolean,
        public restaurantId?: number,
        public categoryId?: number,
        public productTypeId?: number
    ) {
        this.isAvailable = this.isAvailable || false;
    }
}
