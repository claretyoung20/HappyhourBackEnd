export interface IBookTable {
    id?: number;
    isAvaliable?: boolean;
    price?: number;
    imageUrl?: string;
    restaurantId?: number;
    tableTypeId?: number;
}

export class BookTable implements IBookTable {
    constructor(
        public id?: number,
        public isAvaliable?: boolean,
        public price?: number,
        public imageUrl?: string,
        public restaurantId?: number,
        public tableTypeId?: number
    ) {
        this.isAvaliable = this.isAvaliable || false;
    }
}
