import { Moment } from 'moment';

export interface IProduct {
    id?: number;
    createdDate?: Moment;
    description?: string;
    name?: string;
    price?: number;
    updatedDate?: Moment;
    isAvailable?: boolean;
    showOnHomepage?: boolean;
    prodct_imageContentType?: string;
    prodct_image?: any;
    restaurantId?: number;
    categoryId?: number;
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
        public showOnHomepage?: boolean,
        public prodct_imageContentType?: string,
        public prodct_image?: any,
        public restaurantId?: number,
        public categoryId?: number
    ) {
        this.isAvailable = this.isAvailable || false;
        this.showOnHomepage = this.showOnHomepage || false;
    }
}
